package com.puce.springjwt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "product", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) }) // Evita que se repitan los nombres de los productos
public class Product {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private float price;

}
