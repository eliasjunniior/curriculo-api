package com.eliasjunnior.curriculoapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // Importante!
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate; // Para datas

@Entity
@Table(name = "experiencias_profissionais")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaProfissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cargo;
    private String empresa;
    private LocalDate dataInicio;
    private LocalDate dataFim; // Pode ser nulo (emprego atual)

    @Column(columnDefinition = "TEXT")
    private String descricao;

    // Relacionamento ManyToOne com Curriculo
    @ManyToOne(fetch = FetchType.LAZY) // Carrega o Curriculo só quando necessário
    @JoinColumn(name = "curriculo_id", nullable = false) // Nome da coluna de chave estrangeira no banco
    @JsonBackReference // Essencial para evitar loop infinito ao converter para JSON
    private Curriculo curriculo;
}