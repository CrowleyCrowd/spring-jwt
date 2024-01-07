package com.puce.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.puce.springjwt.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration // Indica que la clase es una clase de configuración
@RequiredArgsConstructor // Genera un constructor con los atributos marcados como final
public class ApplicationConfig {

    private final UserRepository userRepository; // Repositorio de usuarios

    @Bean // Indica que el método es un bean de Spring
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { // Configura el administrador de autenticación
        return config.getAuthenticationManager(); // Devuelve el administrador de autenticación
    }

    @Bean // Indica que el método es un bean de Spring
    public AuthenticationProvider authenticationProvider() { // Configura el proveedor de autenticación
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService()); // Intanciar el servicio de usuario
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Instanciar el encriptador de contraseña
        return authenticationProvider; // Devuelve el proveedor de autenticación
    }

    @Bean // Indica que el método es un bean de Spring
    public PasswordEncoder passwordEncoder() { // Instancia el encriptador de contraseña
        return new BCryptPasswordEncoder(); // Devuelve el encriptador de contraseña
    }

    @Bean // Indica que el método es un bean de Spring
    public UserDetailsService userDetailService() { // Instancia el servicio de usuario
        return username -> userRepository.findByUsername(username) // Busca el usuario por nombre de usuario
                .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe")); // Lanza una excepción si no existe
    }

}
