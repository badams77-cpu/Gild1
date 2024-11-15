package com.gildedrose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityCustomizer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityCustomizer.class);

    @Bean
    public DefaultSecurityFilterChain filerChain(HttpSecurity http) {
        try {
            http.cors().disable()
                    .csrf().disable();
            return http.build();
        } catch (Exception e){

            return null;
        }


    }
}