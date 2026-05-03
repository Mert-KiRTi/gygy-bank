package com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs;

/**
 * Generic Query Handler interface
 * Handles queries of type Q and returns a response of type R
 *
 * @param <Q> The query type
 * @param <R> The response type
 */
public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}
