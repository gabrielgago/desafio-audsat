package com.desafio.audsat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class MessageConfig {

    @Bean
    public Locale locale() {
        return Locale.ENGLISH;
    }

}
