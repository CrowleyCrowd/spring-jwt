package com.puce.springjwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.puce.springjwt.auth.AuthResponse;
import com.puce.springjwt.auth.LoginRequest;
import com.puce.springjwt.auth.RegisterRequest;
import com.puce.springjwt.model.Role;
import com.puce.springjwt.model.User;
import com.puce.springjwt.repository.UserRepository;

import lombok.RequiredArgsConstructor; // Genera un constructor con los atributos finales

@Service
@RequiredArgsConstructor // Genera un constructor con los atributos finales
public class AuthService {

        // Variable para el repositorio de usuarios
        private final UserRepository userRepository;
        // Variable para el servicio de JWT
        private final JwtService jwtService;
        // Variable para encriptar la contraseña
        private final PasswordEncoder passwordEncoder;
        // Variable para el administrador de autenticación
        private final AuthenticationManager authenticationManager;

        // Método para iniciar sesión
        public AuthResponse login(LoginRequest request) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                                                request.getPassword()));
                UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
                String token = jwtService.getToken(user);
                return AuthResponse.builder()
                                .token(token)
                                .build();
        }

        // Método para registrar un usuario
        public AuthResponse register(RegisterRequest request) {
                User user = User.builder()
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .firstName(request.getFirstName())
                                .lastName(request.getLastName())
                                .role(Role.USER)
                                .build();

                userRepository.save(user);

                return AuthResponse.builder()
                                .token(jwtService.getToken(user))
                                .build();
        }

}
