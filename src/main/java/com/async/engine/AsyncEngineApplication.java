package com.async.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.async.engine")
@EnableTransactionManagement
public class AsyncEngineApplication {

    /**
     * AsyncEngineApplication Application.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(com.async.engine.AsyncEngineApplication.class, args);
    }

}
