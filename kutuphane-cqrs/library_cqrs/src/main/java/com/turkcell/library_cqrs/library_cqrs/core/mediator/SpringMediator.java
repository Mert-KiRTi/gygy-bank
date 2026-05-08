package com.turkcell.library_cqrs.library_cqrs.core.mediator;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Command;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Query;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.pipeline.RequestHandlerDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring-based Mediator implementation
 * Resolves and executes appropriate handlers for commands and queries
 * using Spring's ApplicationContext and bean discovery mechanism.
 * 
 * Integrates with PipelineBehavior implementations to process requests through
 * a chain of behaviors before reaching the actual handler (e.g., logging, performance monitoring).
 */
@Component
public class SpringMediator implements Mediator {

    private final ApplicationContext applicationContext;
    private final List<PipelineBehavior> pipelineBehaviors;

    public SpringMediator(ApplicationContext applicationContext, List<PipelineBehavior> pipelineBehaviors) {
        this.applicationContext = applicationContext;
        this.pipelineBehaviors = pipelineBehaviors != null ? pipelineBehaviors : new ArrayList<>();
    }

    @Override
    public <R> R send(Command<R> command) {
        // Get the handler bean name pattern: commandClassName -> handlerClassName
        String commandClassName = command.getClass().getSimpleName();
        String handlerBeanName = generateHandlerBeanName(commandClassName, "Handler");

        // Find the CommandHandler bean for this command
        CommandHandler<Command<R>, R> handler = findCommandHandler(command, handlerBeanName);

        if (handler == null) {
            throw new IllegalStateException(
                    "No CommandHandler found for command: " + commandClassName
            );
        }

        // Execute the command through the pipeline chain
        return executeThroughPipeline(command, () -> handler.handle(command));
    }

    @Override
    public <R> R send(Query<R> query) {
        // Get the handler bean name pattern: queryClassName -> handlerClassName
        String queryClassName = query.getClass().getSimpleName();
        String handlerBeanName = generateHandlerBeanName(queryClassName, "Handler");

        // Find the QueryHandler bean for this query
        QueryHandler<Query<R>, R> handler = findQueryHandler(query, handlerBeanName);

        if (handler == null) {
            throw new IllegalStateException(
                    "No QueryHandler found for query: " + queryClassName
            );
        }

        // Execute the query through the pipeline chain
        return executeThroughPipeline(query, () -> handler.handle(query));
    }

    /**
     * Finds a CommandHandler for the given command
     * Uses reflection to discover the handler bean
     */
    @SuppressWarnings("unchecked")
    private <R> CommandHandler<Command<R>, R> findCommandHandler(
            Command<R> command,
            String preferredBeanName
    ) {
        // First, try to find by the preferred bean name (lowercase first letter)
        if (applicationContext.containsBean(preferredBeanName)) {
            return (CommandHandler<Command<R>, R>) applicationContext.getBean(preferredBeanName);
        }

        // Otherwise, search for beans of type CommandHandler
        Map<String, CommandHandler> handlers = applicationContext.getBeansOfType(CommandHandler.class);

        for (CommandHandler handler : handlers.values()) {
            if (isHandlerForCommand(handler, command)) {
                return (CommandHandler<Command<R>, R>) handler;
            }
        }

        return null;
    }

    /**
     * Finds a QueryHandler for the given query
     * Uses reflection to discover the handler bean
     */
    @SuppressWarnings("unchecked")
    private <R> QueryHandler<Query<R>, R> findQueryHandler(
            Query<R> query,
            String preferredBeanName
    ) {
        // First, try to find by the preferred bean name (lowercase first letter)
        if (applicationContext.containsBean(preferredBeanName)) {
            return (QueryHandler<Query<R>, R>) applicationContext.getBean(preferredBeanName);
        }

        // Otherwise, search for beans of type QueryHandler
        Map<String, QueryHandler> handlers = applicationContext.getBeansOfType(QueryHandler.class);

        for (QueryHandler handler : handlers.values()) {
            if (isHandlerForQuery(handler, query)) {
                return (QueryHandler<Query<R>, R>) handler;
            }
        }

        return null;
    }

    /**
     * Checks if the given handler can handle the given command
     */
    private <R> boolean isHandlerForCommand(CommandHandler handler, Command<R> command) {
        // Get generic type parameters from the handler class
        return handler.getClass().getGenericInterfaces()[0].getTypeName()
                .contains(command.getClass().getSimpleName());
    }

    /**
     * Checks if the given handler can handle the given query
     */
    private <R> boolean isHandlerForQuery(QueryHandler handler, Query<R> query) {
        // Get generic type parameters from the handler class
        return handler.getClass().getGenericInterfaces()[0].getTypeName()
                .contains(query.getClass().getSimpleName());
    }

    /**
     * Executes a request through the pipeline chain of behaviors.
     * Each behavior wraps the next one, allowing for cross-cutting concerns
     * to be applied in order (logging, performance monitoring, etc.).
     *
     * @param request The request object being processed
     * @param handler The final handler to be called after all behaviors
     * @return The result from the handler execution
     */
    private <R> R executeThroughPipeline(Object request, RequestHandlerDelegate<R> handler) {
        // If no behaviors are configured, execute the handler directly
        if (pipelineBehaviors.isEmpty()) {
            return handler.handle();
        }

        // Build the pipeline chain starting from the final handler
        // and wrapping it with each behavior in reverse order
        RequestHandlerDelegate<R> pipeline = handler;

        // Wrap the pipeline with behaviors in reverse order
        // This ensures they execute in the order they were registered
        for (int i = pipelineBehaviors.size() - 1; i >= 0; i--) {
            PipelineBehavior behavior = pipelineBehaviors.get(i);
            RequestHandlerDelegate<R> finalPipeline = pipeline;
            pipeline = () -> behavior.handle(request, finalPipeline);
        }

        return pipeline.handle();
    }

    /**
     * Generates a bean name from a class name and suffix
     * e.g., CreateKategoriCommand -> createKategoriHandler
     */
    private String generateHandlerBeanName(String className, String suffix) {
        // Remove the suffix if present (e.g., Command, Query)
        String baseName = className.replaceAll("(Command|Query)$", "");

        // Add the new suffix
        String withSuffix = baseName + suffix;

        // Convert first letter to lowercase
        return withSuffix.substring(0, 1).toLowerCase() + withSuffix.substring(1);
    }
}
