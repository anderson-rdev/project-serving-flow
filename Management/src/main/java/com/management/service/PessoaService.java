package com.management.service;

import com.management.DTOs.*;
import com.management.exception.ResourceNotFoundException;
import com.management.logging.AppLogger;
import com.management.model.*;
import com.management.repository.DocumentosRepository;
import com.management.repository.PessoaRepository;
import com.management.repository.TipoContatoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

// Serviço responsável por gerenciar operações de Pessoa

@Service
@Transactional
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private DocumentosRepository documentosRepository;

    @Autowired
    private TipoContatoRepository tipoContatoRepository;

    @Autowired
    private static final AppLogger log = AppLogger.getInstance();

    public PessoaResponse cadastrar(PessoaRequest request) {

        // Validação de nome nulo ou vazio
        if (request.getNome() == null || request.getNome().isEmpty()) {
            log.warn(getClass(), "Tentativa de cadastro com nome nulo ou vazio.");
            throw new IllegalArgumentException("O nome da pessoa não pode ser nulo ou vazio.");
        }

        // Verifica se já existe um registro com o mesmo nome
        if (pessoaRepository.existsByNomeIgnoreCase(request.getNome().trim())) {
            log.warn(getClass(), "Tentativa de cadastro com nome já existente: " + request.getNome());
            throw new IllegalArgumentException("Já existe uma pessoa cadastrada com esse nome.");
        }

        Pessoa pessoa = converterRequestParaEntidade(request);
        log.info(getClass(), "Iniciando cadastro da pessoa.");

        try {
            // Garante vínculos bidirecionais
            if (pessoa.getEnderecos() != null) {
                pessoa.getEnderecos().forEach(e -> e.setPessoa(pessoa));
            }

            if (pessoa.getContatos() != null) {
                pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
            }

// Validação de documentos únicos
            if (pessoa.getDocumentos() != null) {
                for (Documentos doc : pessoa.getDocumentos()) {
                    if (documentosRepository.existsByNumeroDocumentoAndTipoDocumento(
                            doc.getNumeroDocumento(), doc.getTipoDocumento())) {
                        log.warn(getClass(), "Tentativa de cadastro com documento já existente: "
                                + doc.getNumeroDocumento() + " / " + doc.getTipoDocumento());
                        throw new IllegalArgumentException(
                                "Já existe um documento cadastrado com este número e tipo: "
                                        + doc.getNumeroDocumento() + " / " + doc.getTipoDocumento()
                        );
                    }
                    doc.setPessoa(pessoa); // garante o relacionamento bidirecional
                }
            }

            Pessoa salva = pessoaRepository.save(pessoa);
            return converterParaResponse(salva);

        } catch (Exception ex) {
            log.error(getClass(), "Erro ao cadastrar pessoa", ex);
            throw new RuntimeException("Erro ao cadastrar pessoa", ex);
        }
    }


    // Consultar (GET)
    public PessoaResponse buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa com ID " + id + " não encontrada"));
        return converterParaResponse(pessoa);
    }

    // Alterar (PUT)
    public PessoaResponse alterar(Long id, PessoaRequest request) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa com ID " + id + " não encontrada"));

        // Contatos
        if (request.getContatos() != null && !request.getContatos().isEmpty()) {
            List<Contato> novosContatos = converterContatos(request.getContatos());
            novosContatos.forEach(c -> c.setPessoa(pessoa));

            // Substitui contatos existentes
            pessoa.getContatos().clear();
            pessoa.getContatos().addAll(novosContatos);
        }

        // Endereços
        if (request.getEnderecos() != null) {
            List<Endereco> novosEnderecos = converterEnderecos(request.getEnderecos());
            novosEnderecos.forEach(e -> e.setPessoa(pessoa));
            // corrigido: não usar clone(); limpar e adicionar
            pessoa.getEnderecos().clear();
            pessoa.getEnderecos().addAll(novosEnderecos);
        }

        Pessoa atualizada = pessoaRepository.save(pessoa);
        return converterParaResponse(atualizada);
    }

    // Excluir (DELETE)
    public void excluir(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pessoa com ID " + id + " não encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    // Conversores auxiliares (DTO ↔ Entidade)
    private Pessoa converterRequestParaEntidade(PessoaRequest request) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());

        // Endereços
        if (request.getEnderecos() != null) {
            List<Endereco> enderecos = converterEnderecos(request.getEnderecos());
            pessoa.setEnderecos(enderecos);
        } else {
            pessoa.setEnderecos(new ArrayList<>());
        }

        // Contatos
        if (request.getContatos() != null) {
            List<Contato> contatos = converterContatos(request.getContatos());
            pessoa.setContatos(contatos);
        } else {
            pessoa.setContatos(new ArrayList<>());
        }

        // Documentos
        if (request.getDocumentos() != null) {
            List<Documentos> documentos = converterDocumentos(request.getDocumentos(), pessoa);
            pessoa.setDocumentos(documentos);
        } else {
            pessoa.setDocumentos(new ArrayList<>());
        }

        return pessoa;
    }

    private List<Contato> converterContatos(List<ContatoRequest> dtos) {
        if (dtos == null) return new ArrayList<>();

        return dtos.stream().map(dto -> {
            if (dto.getTipo() == null || dto.getTipo().trim().isEmpty()) {
                throw new ResourceNotFoundException("Tipo de contato não informado");
            }

            // Buscar o tipo de contato existente
            TipoContato tipoContato = tipoContatoRepository
                    .findByDescricaoIgnoreCase(dto.getTipo().trim())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("TipoContato não encontrado: " + dto.getTipo()));

            Contato contato = new Contato();
            contato.setContato(dto.getValor());
            contato.setTipoContato(tipoContato);
            return contato;
        }).collect(Collectors.toList());
    }

    private List<Endereco> converterEnderecos(List<EnderecoDTO> dtos) {
        if (dtos == null) return new ArrayList<>();
        return dtos.stream().map(dto -> {
            Endereco e = new Endereco();
            e.setRua(dto.getRua());
            e.setNumero(dto.getNumero());
            e.setBairro(dto.getBairro());
            e.setCidade(dto.getCidade());
            e.setEstado(dto.getEstado());
            e.setCep(dto.getCep());
            e.setComplemento(dto.getComplemento());
            e.setTipo(dto.getTipo());
            return e;
        }).collect(Collectors.toList());
    }

    private List<Documentos> converterDocumentos(@NotNull List<DocumentosDTO> documentosDTOs, Pessoa pessoa) {
        if (documentosDTOs == null) return new ArrayList<>();

        return documentosDTOs.stream()
                .filter(dto ->
                        dto.getNumeroDocumento() != null &&
                                !dto.getNumeroDocumento().trim().isEmpty() &&
                                dto.getTipoDocumento() != null)
                .map(dto -> {
                    Documentos doc = new Documentos();
                    doc.setNumeroDocumento(dto.getNumeroDocumento());
                    doc.setTipoDocumento(dto.getTipoDocumento());
                    doc.setPessoa(pessoa);
                    return doc;
                })
                .collect(Collectors.toList());
    }

    private PessoaResponse converterParaResponse(Pessoa pessoa) {
        if (pessoa == null) return null;

        PessoaResponse response = new PessoaResponse();
        response.setId(pessoa.getIdPessoa());
        response.setNome(pessoa.getNome());

        // Caso mantenha compatibilidade com DTOs antigos, retornamos o primeiro contato
        if (pessoa.getContatos() != null && !pessoa.getContatos().isEmpty()) {
            Contato contato = pessoa.getContatos().get(0);
            ContatoDTO contatoDTO = new ContatoDTO();
            if (contato.getTipoContato() != null) {
                contatoDTO.setTipo(contato.getTipoContato().getDescricao());
            }
            contatoDTO.setValor(contato.getContato());
            // response.setContatos(contatoDTO);
        }

        // Endereços
        List<EnderecoDTO> enderecosDTO = new ArrayList<>();
        if (pessoa.getEnderecos() != null) {
            for (Endereco e : pessoa.getEnderecos()) {
                EnderecoDTO dto = new EnderecoDTO();
                dto.setRua(e.getRua());
                dto.setNumero(e.getNumero());
                dto.setBairro(e.getBairro());
                dto.setCidade(e.getCidade());
                dto.setEstado(e.getEstado());
                dto.setCep(e.getCep());
                dto.setComplemento(e.getComplemento());
                dto.setTipo(e.getTipo());
                enderecosDTO.add(dto);
            }
        }
        response.setEnderecos(enderecosDTO);

        return response;
    }
}
