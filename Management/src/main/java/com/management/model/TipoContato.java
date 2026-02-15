package com.management.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/** Classe referente ao tipo de contato **/
@Entity
@Table(name = "TiposContato")
public class TipoContato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipo;

    @Column(nullable = false, unique = true, length = 50)
    private String descricao;

    public TipoContato() {}

    public TipoContato(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TipoContato)) return false;
        TipoContato that = (TipoContato) o;
        return Objects.equals(idTipo, that.idTipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTipo);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
