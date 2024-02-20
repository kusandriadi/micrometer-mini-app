package com.micrometer.web;

import com.micrometer.base.command.executor.CommandExecutor;
import com.micrometer.base.command.executor.DefaultCommandExecutor;
import com.micrometer.command.HelloCommand;
import com.micrometer.command.impl.HelloCommandImpl;
import jakarta.validation.Validator;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Hooks;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HelloControllerTest.Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void helloV1Test() {
        String pathVariable = "Shaq";
        webTestClient.get().uri("/v1/hello/" + pathVariable)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.data").isEqualTo("Your name is " + pathVariable);
    }

    @Test
    public void helloV2Test() {
        String pathVariable = "Kobe";
        webTestClient.get().uri("/v2/hello/" + pathVariable)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.data").isEqualTo("Your name is " + pathVariable);
    }

    @Test
    public void helloV3Test() {
        String pathVariable = "LeBron";
        webTestClient.get().uri("/v3/hello/" + pathVariable)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.data").isEqualTo("Your name is " + pathVariable);
    }

    @SpringBootApplication
    @AutoConfigureObservability
    @Log
    public static class Application {

        @EventListener(ApplicationStartedEvent.class)
        public void onStart() {
            log.info("Start testing....");
            Hooks.enableAutomaticContextPropagation();
        }

        @Bean
        public HelloCommand helloService() {
            return new HelloCommandImpl();
        }

        @Bean
        public CommandExecutor commandExecutor(Validator validator) {
            DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor();
            commandExecutor.setValidator(validator);
            return commandExecutor;
        }

    }
}
