package com.turkcell.library_cqrs.library_cqrs.core.mediator.pipeline;

/**
 * Defines a pipeline behavior that can intercept and process requests in the mediator chain.
 * Implementations of this interface can perform cross-cutting concerns such as logging,
 * performance monitoring, caching, validation, etc.
 *
 * Generic type parameter R represents the response/result type.
 *
 * @param <R> The type of the response/result
 */
public interface PipelineBehavior<R> {
    /**
     * Handles the request by executing the next handler in the pipeline chain.
     *
     * @param request The request object to be processed
     * @param next The delegate to execute the next handler in the chain
     * @return The result of the pipeline execution
     */
    R handle(Object request, RequestHandlerDelegate<R> next);
}
