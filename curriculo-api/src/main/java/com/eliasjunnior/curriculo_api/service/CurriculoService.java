package com.eliasjunnior.curriculoapi.service;

import com.eliasjunnior.curriculoapi.exception.ResourceNotFoundException;
import com.eliasjunnior.curriculoapi.model.*; // Importa todas as entidades do pacote model
import com.eliasjunnior.curriculoapi.repository.CurriculoRepository;
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.stereotype.Service; // Marca como um componente de serviço do Spring
import org.springframework.transaction.annotation.Transactional; // Para gerenciar transações com o banco

import java.util.List;

@Service // Indica ao Spring que esta classe contém lógica de negócio
public class CurriculoService {

    // Injeta automaticamente uma instância de CurriculoRepository gerenciada pelo Spring
    @Autowired
    private CurriculoRepository curriculoRepository;

    // Busca todos os currículos.
    // @Transactional(readOnly = true) otimiza a transação para operações de leitura.
    @Transactional(readOnly = true)
    public List<Curriculo> findAllCurriculos() {
        return curriculoRepository.findAll(); // Delega para o repositório
    }

    // Busca um currículo pelo ID.
    @Transactional(readOnly = true)
    public Curriculo findCurriculoById(Long id) {
        // findById retorna um Optional. orElseThrow lança a exceção se o Optional estiver vazio.
        return curriculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currículo não encontrado com id: " + id));
    }

    // Cria um novo currículo.
    // @Transactional garante que todas as operações com o banco dentro do método
    // ocorram em uma única transação (ou tudo funciona, ou nada é salvo - atomicidade).
    @Transactional
    public Curriculo createCurriculo(Curriculo curriculo) {
        // ** Importante: Garante que as entidades filhas (Experiencia, Formacao, etc.)
        // ** tenham a referência correta para este novo Curriculo ANTES de salvar.
        // ** Isso é necessário por causa do CascadeType.ALL na entidade Curriculo.
        setReferenciasFilhas(curriculo);
        return curriculoRepository.save(curriculo); // Salva o curriculo e suas entidades filhas (devido ao Cascade)
    }

    // Atualiza um currículo existente.
    @Transactional
    public Curriculo updateCurriculo(Long id, Curriculo curriculoDetails) {
        // 1. Busca o currículo existente no banco (ou lança exceção se não encontrar)
        Curriculo existingCurriculo = findCurriculoById(id);

        // 2. Atualiza os campos simples do currículo existente com os dados recebidos
        existingCurriculo.setNomeCompleto(curriculoDetails.getNomeCompleto());
        existingCurriculo.setEmail(curriculoDetails.getEmail());
        existingCurriculo.setTelefone(curriculoDetails.getTelefone());
        existingCurriculo.setLinkedinUrl(curriculoDetails.getLinkedinUrl());
        existingCurriculo.setGithubUrl(curriculoDetails.getGithubUrl());
        existingCurriculo.setResumoProfissional(curriculoDetails.getResumoProfissional());

        // 3. Atualiza as listas (Experiencias, Formacoes, etc.)
        // Estratégia simples: limpar a lista existente e adicionar todos os itens da lista recebida.
        // Cuidado: Isso causa DELETEs e INSERTs nas tabelas filhas.
        existingCurriculo.getExperiencias().clear(); // Remove todas as experiências antigas associadas
        if (curriculoDetails.getExperiencias() != null) {
            // Adiciona as novas experiências recebidas na requisição
            existingCurriculo.getExperiencias().addAll(curriculoDetails.getExperiencias());
        }

        // Repete o processo para as outras listas...
        existingCurriculo.getFormacoes().clear();
        if (curriculoDetails.getFormacoes() != null) {
            existingCurriculo.getFormacoes().addAll(curriculoDetails.getFormacoes());
        }

        existingCurriculo.getHabilidades().clear();
        if (curriculoDetails.getHabilidades() != null) {
            existingCurriculo.getHabilidades().addAll(curriculoDetails.getHabilidades());
        }

        existingCurriculo.getIdiomas().clear();
        if (curriculoDetails.getIdiomas() != null) {
            existingCurriculo.getIdiomas().addAll(curriculoDetails.getIdiomas());
        }

        // 4. ** Re-seta as referências filhas para garantir a consistência bidirecional **
        //    depois de modificar as listas.
        setReferenciasFilhas(existingCurriculo);

        // 5. Salva o currículo atualizado (JPA/Hibernate gerencia as atualizações e inserções/deleções nas filhas)
        return curriculoRepository.save(existingCurriculo);
    }

    // Deleta um currículo pelo ID.
    @Transactional
    public void deleteCurriculo(Long id) {
        // Verifica se o currículo existe antes de tentar deletar (lança exceção se não achar)
        Curriculo curriculo = findCurriculoById(id);
        curriculoRepository.delete(curriculo); // Deleta o currículo e suas entidades filhas (devido ao Cascade)
    }

    // Método auxiliar privado para garantir que as referências ManyToOne nas entidades filhas
    // apontem corretamente para a entidade Pai (Curriculo). Essencial para o Cascade funcionar.
    private void setReferenciasFilhas(Curriculo curriculo) {
        if (curriculo.getExperiencias() != null) {
            // Para cada Experiencia na lista, define o campo 'curriculo' dela para ser este Curriculo
            curriculo.getExperiencias().forEach(exp -> exp.setCurriculo(curriculo));
        }
        if (curriculo.getFormacoes() != null) {
            curriculo.getFormacoes().forEach(form -> form.setCurriculo(curriculo));
        }
        if (curriculo.getHabilidades() != null) {
            curriculo.getHabilidades().forEach(hab -> hab.setCurriculo(curriculo));
        }
        if (curriculo.getIdiomas() != null) {
            curriculo.getIdiomas().forEach(idi -> idi.setCurriculo(curriculo));
        }
    }
}