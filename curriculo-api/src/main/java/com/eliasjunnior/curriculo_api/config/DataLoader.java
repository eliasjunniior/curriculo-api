package com.eliasjunnior.curriculoapi.config;

import com.eliasjunnior.curriculoapi.model.*;
import com.eliasjunnior.curriculoapi.repository.CurriculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CurriculoRepository curriculoRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (curriculoRepository.count() == 0) {
            loadInitialData();
            System.out.println("### Dados iniciais do currículo carregados com sucesso! ###");
        } else {
            System.out.println("### Banco de dados já contém dados. DataLoader ignorado. ###");
        }
    }

    private void loadInitialData() {
        Curriculo curriculo = new Curriculo();
        curriculo.setNomeCompleto("Elias Pereira da Silva Junior");
        curriculo.setEmail("elias.pereira.adm@outlook.com");
        curriculo.setTelefone("(81) 9 9697-3442");
        curriculo.setLinkedinUrl("https://www.linkedin.com/in/elias-pereira-350519184");
        curriculo.setGithubUrl("https://github.com/eliasjunniior");
        curriculo.setResumoProfissional(
            "Profissional com mais de 4 anos de experiência em marketing digital, tecnologia e gestão. " +
            "Focado em inovação, criatividade e resultados. Experiência com sistemas ERP, CRM, campanhas digitais, " +
            "eventos, BI, e automações. Busca constante por aprendizado em tecnologia, desenvolvimento e marketing estratégico."
        );

        // EXPERIÊNCIAS
        ExperienciaProfissional atual = new ExperienciaProfissional();
        atual.setCargo("Analista de Marketing Jr");
        atual.setEmpresa("Bioxxi Esterilização");
        atual.setDataInicio(LocalDate.of(2024, 9, 1));
        atual.setDataFim(null); // atual
        atual.setDescricao(
            "Gestão de campanhas de marketing, inbound marketing com RD Station, eventos, CRM, indicadores e suporte ao comercial."
        );
        atual.setCurriculo(curriculo);

        ExperienciaProfissional anterior = new ExperienciaProfissional();
        anterior.setCargo("Assistente de Marketing Pleno");
        anterior.setEmpresa("Bioxxi Esterilização");
        anterior.setDataInicio(LocalDate.of(2022, 1, 1));
        anterior.setDataFim(LocalDate.of(2023, 12, 31));
        anterior.setDescricao("Comunicação interna, marketing estratégico, eventos, brindes e suporte comercial.");
        anterior.setCurriculo(curriculo);

        ExperienciaProfissional rh = new ExperienciaProfissional();
        rh.setCargo("Estagiário de Recursos Humanos");
        rh.setEmpresa("Bioxxi Esterilização");
        rh.setDataInicio(LocalDate.of(2022, 1, 1));
        rh.setDataFim(LocalDate.of(2023, 1, 1));
        rh.setDescricao("Gestão de benefícios, ponto, admissões, integrações e sistemas (TOTVS).");
        rh.setCurriculo(curriculo);

        ExperienciaProfissional ciee = new ExperienciaProfissional();
        ciee.setCargo("Estagiário Financeiro");
        ciee.setEmpresa("CIEE - Pernambuco");
        ciee.setDataInicio(LocalDate.of(2020, 1, 1));
        ciee.setDataFim(LocalDate.of(2021, 1, 1));
        ciee.setDescricao("Contas a receber, cobranças, emissão de boletos e documentos bancários.");
        ciee.setCurriculo(curriculo);

        ExperienciaProfissional vidrossul = new ExperienciaProfissional();
        vidrossul.setCargo("Auxiliar Administrativo");
        vidrossul.setEmpresa("Vidrossul LTDA");
        vidrossul.setDataInicio(LocalDate.of(2019, 1, 1));
        vidrossul.setDataFim(LocalDate.of(2020, 4, 1));
        vidrossul.setDescricao("Emissão de notas fiscais, controle de estoque e orçamentos.");
        vidrossul.setCurriculo(curriculo);

        curriculo.setExperiencias(Arrays.asList(atual, anterior, rh, ciee, vidrossul));

        // FORMAÇÃO
        FormacaoAcademica graduacao = new FormacaoAcademica();
        graduacao.setInstituicao("Centro Universitário Maurício de Nassau (UNINASSAU)");
        graduacao.setCurso("Administração");
        graduacao.setNivel("Graduação");
        graduacao.setDataInicio(LocalDate.of(2019, 1, 1));
        graduacao.setDataFim(LocalDate.of(2023, 12, 1));
        graduacao.setCurriculo(curriculo);

        FormacaoAcademica sistemas = new FormacaoAcademica();
        sistemas.setInstituicao("Universidade Católica de Pernambuco (UNICAP)");
        sistemas.setCurso("Sistemas para Internet");
        sistemas.setNivel("Graduação");
        sistemas.setDataInicio(LocalDate.of(2023, 1, 1));
        sistemas.setDataFim(null); // Em andamento
        sistemas.setCurriculo(curriculo);

        FormacaoAcademica tecnico = new FormacaoAcademica();
        tecnico.setInstituicao("ETE Lucilo Ávila Pessoa");
        tecnico.setCurso("Técnico em Desenvolvimento de Sistemas");
        tecnico.setNivel("Técnico");
        tecnico.setDataInicio(LocalDate.of(2017, 1, 1));
        tecnico.setDataFim(LocalDate.of(2018, 12, 1));
        tecnico.setCurriculo(curriculo);

        curriculo.setFormacoes(Arrays.asList(graduacao, sistemas, tecnico));

        // IDIOMAS
        Idioma pt = new Idioma("Português", "Nativo");
        Idioma es = new Idioma("Espanhol", "Leitura e compreensão");

        pt.setCurriculo(curriculo);
        es.setCurriculo(curriculo);

        curriculo.setIdiomas(Arrays.asList(pt, es));

        // HABILIDADES
        Habilidade h1 = new Habilidade("Marketing Digital", "Avançado");
        Habilidade h2 = new Habilidade("RD Station", "Avançado");
        Habilidade h3 = new Habilidade("Kommo CRM", "Avançado");
        Habilidade h4 = new Habilidade("TOTVS RM/Protheus", "Intermediário");
        Habilidade h5 = new Habilidade("ERP Bling / Tiny", "Avançado");
        Habilidade h6 = new Habilidade("Figma / Canva", "Intermediário");
        Habilidade h7 = new Habilidade("Organização de eventos", "Avançado");
        Habilidade h8 = new Habilidade("Inovação e criatividade", "Avançado");
        Habilidade h9 = new Habilidade("Python / IA (iniciante)", "Básico");

        for (Habilidade h : Arrays.asList(h1, h2, h3, h4, h5, h6, h7, h8, h9)) {
            h.setCurriculo(curriculo);
        }

        curriculo.setHabilidades(Arrays.asList(h1, h2, h3, h4, h5, h6, h7, h8, h9));

        curriculoRepository.save(curriculo);
    }
}
