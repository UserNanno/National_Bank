package com.nationalbank.nationalbankperu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Especifica los orígenes permitidos en lugar de "*"
        config.setAllowedOrigins(List.of("http://localhost:3000", "https://tudominio.com")); // Cambia esto según tu frontend
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true); // Permite credenciales, pero necesitas orígenes específicos

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
