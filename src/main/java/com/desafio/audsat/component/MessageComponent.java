package com.desafio.audsat.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class MessageComponent {

    private final MessageSource messageSource;
    private final Locale locale;

    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }
}
