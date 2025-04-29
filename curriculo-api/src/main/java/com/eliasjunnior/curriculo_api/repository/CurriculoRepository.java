package com.eliasjunnior.curriculoapi.repository;

import com.eliasjunnior.curriculoapi.model.Curriculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca como um componente de repositório do Spring
// JpaRepository<TipoDaEntidade, TipoDaChavePrimaria>
public interface CurriculoRepository extends JpaRepository<Curriculo, Long> {
    // O Spring Data JPA implementa automaticamente os métodos básicos:
    // save(), findById(), findAll(), deleteById(), etc.
    // Não precisamos escrever nada aqui por enquanto.
}