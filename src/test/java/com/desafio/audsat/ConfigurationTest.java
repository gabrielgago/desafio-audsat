package com.desafio.audsat;

import com.desafio.audsat.config.ParserConfig;
import com.desafio.audsat.config.SecurityConfiguration;
import com.desafio.audsat.service.JWTService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.jwt.JwtEncoder;

@Profile("test")
@TestConfiguration
@Import({SecurityConfiguration.class, ParserConfig.class})
public class ConfigurationTest {

    @Bean
    public JWTService jwtService(JwtEncoder encoder) {
        return new JWTService(encoder);
    }

}
