package com.micrometer.base.command.executor;

import com.micrometer.base.command.Command;
import reactor.core.publisher.Mono;

public interface CommandExecutor {

  <R, T> Mono<T> execute(Class<? extends Command<R, T>> commandClass, R request);

  <R, T> Mono<T> executeV2(Class<? extends Command<R, T>> commandClass, R request);
  <R, T> Mono<T> executeV3(Class<? extends Command<R, T>> commandClass, R request);

}
