package com.example.connectserver.global.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

public class CorsFilter implements CorsConfigurationSource {
    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.setMaxAge(3600L);

        config.setExposedHeaders(Collections.singletonList("Set-Cookie"));
        config.setExposedHeaders(Collections.singletonList("Authorization"));

        return config;
    }
}
