package com.puce.springjwt.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority; // Interfaz que representa una autoridad (rol) concedida a un usuario
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Implementación básica de GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails; // Interfaz que representa un usuario autenticado

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera los getters y setters
@Builder // Permite crear objetos de la clase sin necesidad de usar el constructor
@AllArgsConstructor // Genera un constructor con todos los atributos de la clase
@NoArgsConstructor // Genera un constructor vacío
@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) }) // Evita que se repitan los nombres de los usuarios
public class User implements UserDetails {
    @Id
    @GeneratedValue // Genera un valor automáticamente
    Integer id;
    @Basic // Permite que el atributo no sea nulo
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String firstName;
    String lastName;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    // Métodos de la interfaz UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
