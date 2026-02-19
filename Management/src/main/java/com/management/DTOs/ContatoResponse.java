package com.management.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "Representa um contato retornado pela API (email, telefone, etc.)")
public class ContatoResponse {

    @Schema(description = "Identificador do contato", example = "1")
    private Long id;

    @Schema(description = "Tipo do contato (descrição do TipoContato)", example = "EMAIL")
    private String tipo;

    @Schema(description = "Valor do contato (e-mail, número de telefone, etc.)", example = "joao@exemplo.com")
    private String valor;

    // Construtor vazio
    public ContatoResponse() {
    }

    // Construtor de conveniência
    public ContatoResponse(Long id, String tipo, String valor) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }

    // Getters / Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    @JsonProperty("tipo")
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    @JsonProperty("valor")
    public void setValor(String valor) {
        this.valor = valor;
    }

    // equals / hashCode (baseados no id quando existente)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContatoResponse)) return false;
        ContatoResponse that = (ContatoResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return tipo + ": " + valor;
    }
}
