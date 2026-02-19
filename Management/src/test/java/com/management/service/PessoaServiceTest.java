package com.management.service;

import com.management.DTOs.*;
import com.management.enums.TipoEndereco;
import com.management.exception.ResourceNotFoundException;
import com.management.model.TipoContato;
import com.management.repository.TipoContatoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
@Commit
class PessoaServiceTest {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private TipoContatoRepository tipoContatoRepository;

    @Test
    @DisplayName("Cadastrar outra pessoa testando campo Complemento")
    void CadastrarPessoa_ComComplemento() {

        // Busca tipo de contato EMAIL
        TipoContato tipoEmail = tipoContatoRepository
                .findByDescricaoIgnoreCase("EMAIL")
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de contato 'EMAIL' não encontrado."));

        // Monta o objeto de requisição
        PessoaRequest request = new PessoaRequest();
        request.setNome("Darda Fernandes");

        // Contatos
        ContatoRequest email = new ContatoRequest();
        email.setTipo(tipoEmail.getDescricao());
        email.setValor("Eduarda.teste@gmail.com");

        request.setContatos(Arrays.asList(email));

        // Endereço residencial com complemento
        EnderecoDTO residencial = new EnderecoDTO();
        residencial.setRua("Rua das Acácias");
        residencial.setNumero("250");
        residencial.setComplemento("Bloco B, Apto 32"); // ← TESTANDO COMPLEMENTO
        residencial.setBairro("Jardim Central");
        residencial.setCidade("Campinas");
        residencial.setEstado("SP");
        residencial.setCep("13045-789");
        residencial.setTipo(TipoEndereco.RESIDENCIAL);

        request.setEnderecos(Arrays.asList(residencial));

        // Documento
        DocumentosDTO documento = new DocumentosDTO();

        documento.setTipoDocumento("CPF");
        documento.setNumeroDocumento("123.456.789-55");

        request.setDocumentos(Arrays.asList(documento));

        // Executa o cadastro
        pessoaService.cadastrar(request);
    }
}
