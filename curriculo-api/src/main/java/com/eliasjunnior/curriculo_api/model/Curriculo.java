package com.eliasjunnior.curriculoapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data; // Lombok para getters, setters, toString, etc.
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity // Marca como entidade JPA
@Table(name = "curriculos") // Nome da tabela no banco
@Data // Lombok: gera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Lombok: gera construtor sem argumentos
@AllArgsConstructor // Lombok: gera construtor com todos os argumentos
public class Curriculo {

    @Id // Chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremento no banco
    private Long id;

    @NotBlank(message = "Nome completo é obrigatório") // Validação: não pode ser nulo ou vazio
    @Column(nullable = false) // Mapeamento da coluna: não pode ser nula no banco
    private String nomeCompleto;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido") // Validação: deve ser um email válido
    @Column(unique = true, nullable = false) // Mapeamento: valor único e não nulo no banco
    private String email;

    private String telefone;
    private String linkedinUrl;
    private String githubUrl;

    @Column(columnDefinition = "TEXT") // Para campos de texto mais longos
    private String resumoProfissional;

    // Relacionamento OneToMany com ExperienciaProfissional
    // cascade = CascadeType.ALL: Operações (salvar, deletar) em Curriculo se propagam para Experiencias
    // orphanRemoval = true: Se uma Experiencia for removida da lista, ela é deletada do banco
    // fetch = FetchType.LAZY: Carrega as experiências só quando necessário (melhor performance)
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ExperienciaProfissional> experiencias = new ArrayList<>();

    // Relacionamentos similares para as outras entidades filhas
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FormacaoAcademica> formacoes = new ArrayList<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Habilidade> habilidades = new ArrayList<>();

    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Idioma> idiomas = new ArrayList<>();
}