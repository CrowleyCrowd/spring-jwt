package com.puce.springjwt.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.puce.springjwt.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component // Indica que la clase es un componente de Spring
@RequiredArgsConstructor // Genera un constructor con los atributos marcados como final
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Servicio de JWT
    private final UserDetailsService userDetailsService;  // Servicio de detalles de usuario

    // Método para filtrar las peticiones
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);  // Obtiene el token de la petición
        final String username; // Variable para almacenar el username

        // Si el token es nulo, se continua con la petición
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtenemos el username del token
        username = jwtService.getUsernameFromToken(token);

        // Si el username no es nulo y el contexto de seguridad no contiene una autenticación
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // Establece los detalles de la autenticación

                SecurityContextHolder.getContext().setAuthentication(authentication); // Establece la autenticación en el contexto de seguridad
            }

        }

        filterChain.doFilter(request, response); // Continua con la petición
    }
    // Método para obtener el token de la petición
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // Obtiene el header de autorización

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Devuelve el token
        }
        return null;
    }
}
