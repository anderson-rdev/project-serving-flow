package com.management.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;

// Classe Pessoa que vai dar origem aos usuários (Funcionário/Gerente) e também cadastro de clientes

@Entity
@Table(name = "Pessoas")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoa;

    @Column(nullable = false, length = 120)
    private String nome;


    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Contato> contatos = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Documentos> documentos = new ArrayList<>();


    public Pessoa() {
    }

    public Pessoa(String nome) {
        this.nome = nome;
    }


    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos == null ? new ArrayList<>() : contatos;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos == null ? new ArrayList<>() : enderecos;
    }

    public List<Documentos> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documentos> documentos) {
        this.documentos = documentos == null ? new ArrayList<>() : documentos;
    }

    /* -------------------- Auxiliares bidirecionais -------------------- */

    public void adicionarContato(Contato contato) {
        if (contato == null) return;
        contato.setPessoa(this);
        this.contatos.add(contato);
    }

    public void removerContato(Contato contato) {
        if (contato == null) return;
        contato.setPessoa(null);
        this.contatos.remove(contato);
    }

    public void adicionarEndereco(Endereco endereco) {
        if (endereco == null) return;
        endereco.setPessoa(this);
        this.enderecos.add(endereco);
    }

    public void removerEndereco(Endereco endereco) {
        if (endereco == null) return;
        endereco.setPessoa(null);
        this.enderecos.remove(endereco);
    }

    public void adicionarDocumento(Documentos doc) {
        if (doc == null) return;
        doc.setPessoa(this);
        this.documentos.add(doc);
    }

    /* -------------------- equals / hashCode / toString -------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(idPessoa, pessoa.idPessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPessoa);
    }

    @Override
    public String toString() {
        return nome;
    }
}
