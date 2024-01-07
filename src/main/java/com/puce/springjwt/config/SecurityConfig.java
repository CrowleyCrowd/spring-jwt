package com.puce.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.puce.springjwt.jwt.JwtAuthFilter;

import lombok.RequiredArgsConstructor;

@Configuration // Indica que la clase es una clase de configuración
@EnableWebSecurity // Habilita la seguridad web
@RequiredArgsConstructor // Genera un constructor con los atributos marcados como final
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter; // Filtro de autenticación
    private final AuthenticationProvider authProvider; // Proveedor de autenticación

    @Bean // Indica que el método es un bean de Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desahabilita la autenticación por csrf value
                .authorizeHttpRequests(authRequest -> authRequest // Configura las rutas que requieren autenticación
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sessionManager -> sessionManager // Deshabilita la creación de sesiones
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider) // Configura el proveedor de autenticación
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Agrega el filtro de autenticación
                .build(); // Construye la configuración
    }
}
