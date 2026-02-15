package com.management.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/** Classe referente aos contatos **/

@Entity
@Table(
        name = "Contatos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"contato", "id_tipo_contato"})
)
public class Contato implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContato;

    @Column(nullable = false, length = 100)
    private String contato; // Ex: e-mail, telefone, etc.

    @ManyToOne
    @JoinColumn(name = "id_tipo_contato", nullable = false)
    private TipoContato tipoContato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Contato() {}

    public Contato(String contato, TipoContato tipoContato) {
        this.contato = contato;
        this.tipoContato = tipoContato;
    }

    public Long getIdContato() {
        return idContato;
    }

    public void setIdContato(Long idContato) {
        this.idContato = idContato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public TipoContato getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(TipoContato tipoContato) {
        this.tipoContato = tipoContato;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contato)) return false;
        Contato contato1 = (Contato) o;
        return Objects.equals(idContato, contato1.idContato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idContato);
    }

    @Override
    public String toString() {
        return contato + " (" + tipoContato + ")";
    }
}
