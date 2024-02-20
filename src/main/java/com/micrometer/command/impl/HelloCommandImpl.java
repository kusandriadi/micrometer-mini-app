package com.micrometer.command.impl;

import com.micrometer.command.HelloCommand;
import io.micrometer.tracing.otel.bridge.OtelTracer;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Log
public class HelloCommandImpl implements HelloCommand {

    @Autowired
    private OtelTracer tracer;

    @Override
    public Mono<String> execute(String request) {
        String baggageName = tracer.getBaggage(request).get();
        log.info("Get baggage " + request + " with value " + baggageName);
        return Mono.just("Your name is " + baggageName);
    }
}
