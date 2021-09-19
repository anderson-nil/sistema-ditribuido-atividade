package com.distribuido.sistema.model;

import java.io.Serializable;

public class Questao implements Serializable {
    
    private String pergunta;
    private int quantidadeAlternativas;
    private String[] alternativas;
    private int respostaCerta;

    public Questao(String pergunta, int quantidadeAlternativas, String[] alternativas, int respostaCerta) {
        this.pergunta = pergunta;
        this.quantidadeAlternativas = quantidadeAlternativas;
        this.alternativas = alternativas;
        this.respostaCerta = respostaCerta;
    }

    public String getPergunta() {
        return this.pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public int getQuantidadeAlternativas() {
        return this.quantidadeAlternativas;
    }

    public void setQuantidadeAlternativas(int quantidadeAlternativas) {
        this.quantidadeAlternativas = quantidadeAlternativas;
    }

    public String[] getAlternativas() {
        return this.alternativas;
    }

    public void setAlternativas(String[] alternativas) {
        this.alternativas = alternativas;
    }

    public int getRespostaCerta() {
        return this.respostaCerta;
    }

    public void setRespostaCerta(int respostaCerta) {
        this.respostaCerta = respostaCerta;
    }

}
