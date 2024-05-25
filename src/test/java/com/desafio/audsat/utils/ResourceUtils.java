package com.desafio.audsat.utils;


import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class ResourceUtils {

    public static String loadResourceAsString(String resourcePath) throws IOException {
        try (InputStream inputStream = new ClassPathResource(resourcePath).getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }
}