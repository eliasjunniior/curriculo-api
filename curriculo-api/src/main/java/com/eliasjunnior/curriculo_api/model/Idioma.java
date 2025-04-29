package com.eliasjunnior.curriculoapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "idiomas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String nivel; // Ex: Básico, Intermediário, Avançado, Fluente, Nativo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id", nullable = false)
    @JsonBackReference
    private Curriculo curriculo;
}