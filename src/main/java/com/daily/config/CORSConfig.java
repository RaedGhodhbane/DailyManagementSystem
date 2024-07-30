package com.daily.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Majd Selmi
 */
@Configuration
public class CORSConfig {

    @Value("${server.allowed-origin}")
    private String allowedOrigin;

    @Value("${server.max-request-age}")
    private Long maxRequestAge;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        if ("*".equals(allowedOrigin)) {
            configuration.addAllowedOriginPattern("*");
        } else {
            configuration.addAllowedOrigin(allowedOrigin);
        }

        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(maxRequestAge);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
