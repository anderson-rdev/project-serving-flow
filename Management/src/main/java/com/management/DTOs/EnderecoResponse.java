
package com.management.DTOs;

import com.management.enums.TipoEndereco;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representa os dados de um endereço na resposta da API")
public class EnderecoResponse {

    @Schema(description = "Identificador único do endereço", example = "1")
    private Long id;

    @Schema(description = "Rua, avenida ou logradouro", example = "Rua das Flores")
    private String rua;

    @Schema(description = "Número da residência", example = "123")
    private String numero;

    @Schema(description = "Bairro", example = "Centro")
    private String bairro;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @Schema(description = "Estado (UF)", example = "SP")
    private String estado;

    @Schema(description = "CEP do endereço", example = "01000-000")
    private String cep;

    @Schema(description = "Complemento do endereço", example = "Casa 1")
    private String complemento;

    @Schema(description = "Tipo do endereço (RESIDENCIAL, COMERCIAL, etc)", example = "RESIDENCIAL")
    private TipoEndereco tipo;

    // Construtor padrão
    public EnderecoResponse() {
    }

    // Construtor completo (útil para conversão da entidade)
    public EnderecoResponse(Long id, String rua, String numero, String bairro,
                            String cidade, String estado, String cep, TipoEndereco tipo,
                            String complemento) {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.tipo = tipo;
        this.complemento = complemento;
    }

    // Getters e Setters
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
        complemento = complemento;
    }

    public TipoEndereco getTipo() {
        return tipo;
    }

    public void setTipo(TipoEndereco tipo) {
        this.tipo = tipo;
    }
}
