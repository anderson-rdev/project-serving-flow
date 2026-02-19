package com.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.management.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * Classe de endereços dos clientes cadastrados
 **/
@Entity
@Table(name = "Enderecos")
@Schema(description = "Representa um endereço vinculado a uma pessoa")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do endereço", example = "1")
    private Long id;

    @NotBlank(message = "A rua é obrigatória")
    @Schema(description = "Nome da rua ou avenida", example = "Rua das Flores")
    private String rua;

    @NotBlank(message = "O número é obrigatório")
    @Schema(description = "Número do endereço", example = "608")
    private String numero;

    @NotBlank(message = "O bairro é obrigatório")
    @Schema(description = "Bairro do endereço", example = "Centro")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória")
    @Schema(description = "Cidade do endereço", example = "São Paulo")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Schema(description = "Estado (UF) do endereço", example = "SP")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Schema(description = "CEP do endereço", example = "01000-000")
    private String cep;

    @Schema(description = "Complemento do endereço", example = "Apartamento 12, Bloco B")
    private String complemento;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo do endereço é obrigatório")
    @Schema(
            description = "Tipo do endereço",
            example = "RESIDENCIAL",
            allowableValues = {"RESIDENCIAL", "COMERCIAL", "COBRANCA", "ENTREGA", "OUTRO"}
    )
    private TipoEndereco tipo;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonIgnore
    private Pessoa pessoa; // vínculo com a pessoa

    // CONSTRUTOR
    public Endereco() {
    }

    public Endereco(String rua, String numero, String bairro, String cidade,
                    String estado, String cep, TipoEndereco tipo, String complemento) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.tipo = tipo;
        this.complemento = complemento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public TipoEndereco getTipo() {
        return tipo;
    }

    public void setTipo(TipoEndereco tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    // MÉTODOS AUXILIAR
    public Long getPessoaId() {
        return pessoa != null ? pessoa.getIdPessoa() : null;
    }

    @Override
    public String toString() {
        return tipo + ": " + rua + ", " + numero +
                (complemento != null && !complemento.isBlank() ? " (" + complemento + ")" : "") +
                " - " + bairro + ", " + cidade + "/" + estado +
                " (CEP: " + cep + ")";
    }

}
