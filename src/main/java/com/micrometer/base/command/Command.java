package com.micrometer.base.command;

import reactor.core.publisher.Mono;

public interface Command<R, T> extends CommandCacheable<R, T> {

    Mono<T> execute(R request);

    default Mono<T> fallback(Throwable throwable, R request) {
        return Mono.error(throwable);
    }

    default boolean validateRequest() {
        return true;
    }

}
