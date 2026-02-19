package com.management.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Representa os dados retornados ao consultar ou cadastrar uma pessoa")
public class PessoaResponse {

    @Schema(description = "Identificador único da pessoa", example = "1")
    private Long id;

    @Schema(description = "Nome completo da pessoa", example = "João da Silva")
    private String nome;

    @Schema(description = "Lista de contatos da pessoa")
    @JsonProperty("contatos")
    private List<ContatoDTO> contatos = new ArrayList<>();

    @Schema(description = "Lista de endereços associados à pessoa")
    @JsonProperty("enderecos")
    private List<EnderecoDTO> enderecos = new ArrayList<>();

    @Schema(description = "Lista de documentos da pessoa (CPF, RG, passaporte etc.)")
    @Valid
    @JsonProperty("documentos")
    private List<DocumentosDTO> documentos = new ArrayList<>();

    public PessoaResponse() {
    }

    public PessoaResponse(Long id, String nome,
                          List<ContatoDTO> contatos, List<EnderecoDTO> enderecos) {
        this.id = id;
        this.nome = nome;
        this.contatos = contatos != null ? contatos : new ArrayList<>();
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
        this.documentos = documentos != null ? documentos : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ContatoDTO> getContatos() {
        return contatos;
    }

    public void setContatos(List<ContatoDTO> contatos) {
        this.contatos = contatos != null ? contatos : new ArrayList<>();
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
    }

    public List<DocumentosDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentosDTO> documentos) {
        this.documentos = documentos != null ? documentos : new ArrayList<>();
    }

}
