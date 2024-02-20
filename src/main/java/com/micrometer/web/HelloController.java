package com.micrometer.web;

import com.micrometer.base.command.executor.CommandExecutor;
import com.micrometer.command.HelloCommand;
import com.micrometer.model.helper.ResponseHelper;
import com.micrometer.model.response.Response;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.otel.bridge.OtelTracer;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@Log
public class HelloController {

    private static final String KEY_BAGGAGE = "name";

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OtelTracer tracer;

    @GetMapping(path = "/v1/hello/{name}")
    public Mono<Response<String>> helloV1(@PathVariable String name) {
        return Mono.just(buildBaggage(name))
                .then(commandExecutor.execute(HelloCommand.class, KEY_BAGGAGE))
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(path = "/v2/hello/{name}")
    public Mono<Response<String>> helloV2(@PathVariable String name) {
        return Mono.just(buildBaggage(name))
                .then(commandExecutor.executeV2(HelloCommand.class, KEY_BAGGAGE))
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(path = "/v3/hello/{name}")
    public Mono<Response<String>> helloV3(@PathVariable String name) {
        return Mono.just(buildBaggage(name))
                .then(commandExecutor.executeV3(HelloCommand.class, KEY_BAGGAGE))
                .map(ResponseHelper::ok)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Tracer buildBaggage(String name) {
        Span span = tracer.currentSpan();

        tracer.createBaggageInScope(span.context(), KEY_BAGGAGE, name);
        log.info("Set baggage " + KEY_BAGGAGE + " to " + name);
        return tracer;
    }

}
