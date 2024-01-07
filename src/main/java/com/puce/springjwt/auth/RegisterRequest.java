package com.puce.springjwt.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera los getters y setters
@Builder // Permite crear objetos de la clase sin necesidad de usar el constructor
@AllArgsConstructor // Genera un constructor con todos los atributos de la clase
@NoArgsConstructor // Genera un constructor vacío
public class RegisterRequest {
    String username;
    String password;
    String firstName;
    String lastName;
}
