package com.masprogtech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Permitir origem específica
        config.addAllowedOrigin("http://localhost:3000");

        // Permitir métodos HTTP
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("OPTIONS"); // Inclui OPTIONS para pré-requisições

        // Permitir cabeçalhos
        config.addAllowedHeader("*");

        // Permitir credenciais (necessário para Authorization)
        config.setAllowCredentials(true);



        // Aplicar a configuração a todos os endpoints
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
