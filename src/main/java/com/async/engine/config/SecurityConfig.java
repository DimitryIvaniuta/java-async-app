package com.async.engine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF if you're not using it (for APIs it's common)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Allow unauthenticated access
                        .requestMatchers("/tasks/**").permitAll()
                        // Require authentication for any other request
                        .anyRequest().authenticated()
                )
                // Optionally configure HTTP Basic auth if needed
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
