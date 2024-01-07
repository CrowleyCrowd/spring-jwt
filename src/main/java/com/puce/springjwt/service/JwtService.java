package com.puce.springjwt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Variable que contiene la clave secreta
    private static final String SECRET_KEY = "d1M2YWlZLVBwd2Y4TUJIMHROb3dCQVhVN2VtLWNHdVozRkFvb3JMUTl5dw==";

    // Funcion que permite obtener el token
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user); // Clase de colecciones que se utiliza para almacenar datos en forma de
                                                // pares clave/valor
    }

    // Funcion que permite obtener el token con los claims
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // Establece la duracción del token a 10 min.
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Funcion que permite obtener la clave secreta
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Funcion que permite obtener el username del token
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);// Perite obtener el token y el username alojado en el subject.
    }

    // Funcion que permite validar si el token es valido
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Funcion que permite obtener todos los claims del token
    private Claims getAllClaims(String token) {
        return Jwts// Lireria que permite capturar los claims del token
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Funcion generica que permite obtener todos los claims del token
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Funcion que permite obtener la fecha de expiracion del token
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Funcion que permite validar si el token esta expirado
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

}
