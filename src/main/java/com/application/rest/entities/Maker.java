package com.application.rest.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// Etiquetas de lombook
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

//Etiqueta de JPA
@Entity
@Table(name = "fabricante")
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String name;

    // Relacion de uno a muchos
    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Product> productList = new ArrayList<>();
}
