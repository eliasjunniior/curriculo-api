package com.eliasjunnior.curriculoapi.controller;

import com.eliasjunnior.curriculoapi.model.Curriculo; // Nossa entidade principal
import com.eliasjunnior.curriculoapi.service.CurriculoService; // Nosso serviço
import jakarta.validation.Valid; // Para ativar validações (@NotBlank, @Email, etc.) no corpo da requisição
import org.springframework.beans.factory.annotation.Autowired; // Injeção de dependência
import org.springframework.http.ResponseEntity; // Para construir respostas HTTP customizadas (status, headers, body)
import org.springframework.web.bind.annotation.*; // Anotações do Spring Web (@RestController, @GetMapping, etc.)
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Para construir a URI do novo recurso criado

import java.net.URI; // Representa uma URI (Uniform Resource Identifier)
import java.util.List; // Para listas

@RestController // Combinação de @Controller e @ResponseBody, indica que os retornos dos métodos serão o corpo da resposta HTTP
@RequestMapping("/api/v1/curriculos") // Define o caminho (path) base para todos os endpoints neste controlador
public class CurriculoController {

    @Autowired // Injeta a instância do CurriculoService
    private CurriculoService curriculoService;

    // Endpoint para Listar Todos os Currículos
    // Mapeado para GET /api/v1/curriculos
    @GetMapping
    public ResponseEntity<List<Curriculo>> getAllCurriculos() {
        List<Curriculo> curriculos = curriculoService.findAllCurriculos();
        // Retorna HTTP 200 OK com a lista de currículos no corpo da resposta
        return ResponseEntity.ok(curriculos);
    }

    // Endpoint para Buscar um Currículo por ID
    // Mapeado para GET /api/v1/curriculos/{id}
    // {id} é uma variável de path
    @GetMapping("/{id}")
    public ResponseEntity<Curriculo> getCurriculoById(@PathVariable Long id) {
        // @PathVariable pega o valor de {id} da URL e passa para o parâmetro Long id
        Curriculo curriculo = curriculoService.findCurriculoById(id);
        // Se findCurriculoById não encontrar, ele lança ResourceNotFoundException,
        // que (por causa do @ResponseStatus na exceção) já retorna HTTP 404.
        // Se encontrar, retorna HTTP 200 OK com o currículo encontrado.
        return ResponseEntity.ok(curriculo);
    }

    // Endpoint para Criar um Novo Currículo
    // Mapeado para POST /api/v1/curriculos
    @PostMapping
    public ResponseEntity<Curriculo> createCurriculo(@Valid @RequestBody Curriculo curriculo) {
        // @RequestBody indica que o corpo da requisição HTTP (em JSON) deve ser convertido para um objeto Curriculo.
        // @Valid ativa as validações definidas na classe Curriculo (@NotBlank, @Email). Se falhar, retorna HTTP 400 Bad Request.
        Curriculo novoCurriculo = curriculoService.createCurriculo(curriculo);

        // Boa prática REST: Retornar a URI do recurso recém-criado no header "Location".
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Pega a URI da requisição atual (/api/v1/curriculos)
                .path("/{id}") // Adiciona o path do ID
                .buildAndExpand(novoCurriculo.getId()) // Substitui {id} pelo ID do novo currículo
                .toUri(); // Constrói a URI final

        // Retorna HTTP 201 Created, com a URI no header Location e o novo currículo no corpo
        return ResponseEntity.created(location).body(novoCurriculo);
    }

    // Endpoint para Atualizar um Currículo Existente
    // Mapeado para PUT /api/v1/curriculos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Curriculo> updateCurriculo(@PathVariable Long id, @Valid @RequestBody Curriculo curriculoDetails) {
        Curriculo curriculoAtualizado = curriculoService.updateCurriculo(id, curriculoDetails);
        // Retorna HTTP 200 OK com o currículo atualizado no corpo
        return ResponseEntity.ok(curriculoAtualizado);
    }

    // Endpoint para Deletar um Currículo
    // Mapeado para DELETE /api/v1/curriculos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurriculo(@PathVariable Long id) {
        curriculoService.deleteCurriculo(id);
        // Retorna HTTP 204 No Content (sem corpo na resposta), indicando sucesso na remoção.
        return ResponseEntity.noContent().build();
    }
}