package com.eliasjunnior.curriculoapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "formacoes_academicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormacaoAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instituicao;
    private String curso;
    private String nivel; // Ex: Graduação, Pós, Técnico
    private LocalDate dataInicio;
    private LocalDate dataFim; // Pode ser null (em andamento)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id", nullable = false)
    @JsonBackReference
    private Curriculo curriculo;
}