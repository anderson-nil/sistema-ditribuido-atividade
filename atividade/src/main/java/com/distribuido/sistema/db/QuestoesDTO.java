package com.distribuido.sistema.db;

import java.util.List;

import com.distribuido.sistema.model.Questao;

public class QuestoesDTO {
 
    private static List<Questao> questoes = List.of(
         new Questao("1) Qual dos pontos abaixo não é uma característica de sistemas distribuídos?", 5, new String[]{
            " Interoperabilidade",
            " Portabilidade",
            " Escalabilidade",
            " Segurança",
            " Responsividade"
        }, 5),
        new Questao("2) Soquete está presente em qual paradigma de comunicação?", 4, new String[]{
            " Comunicação indireta",
            " Entre processos",
            " Invocação Remota ",
            " Orientado à objetos"
        }, 2),
        new Questao("3) Qual arquitetura mais comum para comunicação de sistemas?", 5, new String[]{
            " Cliente-Servidor",
            " Peer to Peer",
            " Hibrida (Cliente-Servidor, Peer to Peer)",
            " Paralela",
            " Multi-Thread"
        }, 1),
        new Questao("4) Dois meios de comunicação entre maquínas.", 4, new String[]{
            " Cliente-Servidor",
            " UDP e TCP",
            " API e middlewares",
            " DNS e UDP"
        }, 2),
        new Questao("5) Caracteristica de Segurança da informação:", 4, new String[]{
            " Confiabilidade",
            " Integridade",
            " Autenticidade",
            " Disponibilidade",
            " Todas estão corretas"
        }, 5));

    public static List<Questao> obterQuestoes() {
        return questoes;
    }

}
