package com.turkcell.library_cqrs.library_cqrs.core.mediator.pipeline;

/**
 * Represents a request handler delegate that executes the next handler in the pipeline chain.
 * Generic type parameter R represents the response/result type.
 *
 * @param <R> The type of the response/result
 */
public interface RequestHandlerDelegate<R> {
    /**
     * Executes the next handler in the pipeline chain.
     *
     * @return The result of the handler execution
     */
    R handle();
}
