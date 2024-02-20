package com.micrometer.config;

import com.micrometer.base.command.executor.CommandExecutor;
import com.micrometer.base.command.executor.DefaultCommandExecutor;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiniAppConfig {

    @Bean
    public CommandExecutor commandExecutor(Validator validator) {
        DefaultCommandExecutor commandExecutor = new DefaultCommandExecutor();
        commandExecutor.setValidator(validator);
        return commandExecutor;
    }

}
