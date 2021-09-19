package com.distribuido.sistema.model;

public class Usuario {

    public Usuario() {
    }

    public Usuario(String matricula, String senha, String nome, String sobrenome) {
        this.matricula = matricula;
        this.senha = senha;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }
    
    private String matricula;

    private String senha;

    private String nome;

    private String sobrenome;

    public String obterNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
}
