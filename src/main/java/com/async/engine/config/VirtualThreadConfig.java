package com.async.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadConfig {

    @Bean
    public Executor virtualThreadExecutor() {
        // Each submitted task will run on its own lightweight virtual thread.
        return Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual().name("vt-%d", 0).factory()
        );
    }

}
