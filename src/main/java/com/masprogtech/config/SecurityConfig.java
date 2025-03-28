package com.masprogtech.config;

import com.masprogtech.security.jwt.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] DOCUMENTATION_OPENAPI = {
            "/docs/index.html",
            "/docs-seller-api.html", "/docs-seller-api/**",
            "/v3/api-docs/**",
            "/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**",
            "/**.html", "/webjars/**", "/configuration/**", "/swagger-resources/**"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter, CorsFilter corsFilter) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login").permitAll()
                        .requestMatchers(DOCUMENTATION_OPENAPI).permitAll()
                        .requestMatchers("/api/v1/categories/**").hasRole("ADMIN") // Admin Gerenciar todo de cateogria
                        // Apenas Admin para gerenciar produtos
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN") // Criar
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN") // Atualizar
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN") // Deletar
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/all").hasRole("ADMIN") // Listar com paginação
                        // Admin e Cliente podem visualizar produtos
                        .requestMatchers(HttpMethod.GET, "/api/v1/products").permitAll() // Lista pública sem paginação
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/productName/**").permitAll() // Nome público
                        // Pedidos (Cliente)
                        .requestMatchers(HttpMethod.POST, "/api/v1/orders").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/addresses").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/addresses/list").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/{orderId}").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/details/{orderId}").hasRole("CLIENT")

                        // Pedidos (Admin)

                        .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/admin/orders/{orderId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders/admin/details/{orderId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/stats").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/report").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

