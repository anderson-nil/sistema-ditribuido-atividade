package com.distribuido.sistema.db;

import java.util.List;

import com.distribuido.sistema.model.Questao;

public class QuestoesDTO {
 
    private static List<Questao> questoes = List.of(
        new Questao("Questão 1", 4, new String[]{
            "Alternativa 1",
            "Alternativa 2",
            "Alternativa 3",
            "Alternativa 4"
        }, 2),
        new Questao("Questão 2", 4, new String[]{
            "Alternativa 1",
            "Alternativa 2",
            "Alternativa 3",
            "Alternativa 4"
        }, 1));

    public static List<Questao> obterQuestoes() {
        return questoes;
    }

}
