package com.turkcell.library_cqrs.library_cqrs.core.mediator;

import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Command;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.Query;
import com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Spring-based Mediator implementation
 * Resolves and executes appropriate handlers for commands and queries
 * using Spring's ApplicationContext and bean discovery mechanism
 */
@Component
public class SpringMediator implements Mediator {

    private final ApplicationContext applicationContext;

    public SpringMediator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
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

        return handler.handle(command);
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

        return handler.handle(query);
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
