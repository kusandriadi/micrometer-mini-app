package com.micrometer;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@Log
public class MicrometerMiniApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrometerMiniApplication.class, args);
	}

	@EventListener(ApplicationStartedEvent.class)
	public void onStart() {
		log.info("ApplicationStartedEvent on start....");
		Hooks.enableAutomaticContextPropagation();
	}
}
