package com.turkcell.library_cqrs.library_cqrs.core.mediator.cqrs;

/**
 * Generic Command Handler interface
 * Handles commands of type C and returns a response of type R
 *
 * @param <C> The command type
 * @param <R> The response type
 */
public interface CommandHandler<C extends Command<R>, R> {
    R handle(C command);
}
