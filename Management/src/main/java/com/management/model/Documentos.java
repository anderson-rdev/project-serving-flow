package com.management.model;

import jakarta.persistence.*;

/** Classe dos documentos que não são obrigatórios no cadastro **/

@Entity
@Table(
        name = "Documentos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"numeroDocumento", "tipoDocumento"})
)
public class Documentos extends Dominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroDocumento;
    private String tipoDocumento;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Documentos() {
        super();
    }

    public Documentos(String numeroDocumento, String tipoDocumento, Pessoa pessoa) {
        this.numeroDocumento = numeroDocumento;
        this.tipoDocumento = tipoDocumento;
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
