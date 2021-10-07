package com.distribuido.sistema;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.distribuido.sistema.Utils.Request;
import com.distribuido.sistema.Utils.Response;
import com.distribuido.sistema.Utils.Request.Header;
import com.distribuido.sistema.model.Questao;

public class Cliente {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Map<Object, Object> requestBody = new HashMap<>();
        try {
            Socket cliente = new Socket("localhost", 8888);

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            
            Object resposta = entrada.readObject();
            System.out.println("Servidor: " + (String) resposta);

            int statusAuth = 0;
            int token = 0;

            do {
                requestBody = new HashMap<>();
                obterInformacoesAutenticacaoUsuario(requestBody);

                Request request = construirRequest(null, requestBody);

                enviarMensagemServidor(saida, request);

                Response response = (Response) entrada.readObject();
                resposta = response.getBody().get("response");
                statusAuth = response.getStatus();

                if (statusAuth == 200) {
                    token = (int) response.getBody().get("token");
                }

                System.out.println("\n" + (String) resposta);
                System.out.println("Status code: " + statusAuth);
                System.out.println("Token: " + token);
            } while (statusAuth != 200);

            System.out.println("\nRespondendo questões");

            Response response = (Response) entrada.readObject();
            int quantidadeQuestoes = (int) response.getBody().get("questionsQuantity");
            int questaoNumero = 1;

            while (quantidadeQuestoes > 0) {
                requestBody = new HashMap<>();
                response = (Response) entrada.readObject();
                Questao questao = (Questao) response.getBody().get("response");

                System.out.println("\nPergunta " + questaoNumero++);

                System.out.println(questao.getPergunta());
                
                for(int i = 0; i < questao.getAlternativas().length; i++) {
                    System.out.println((i+1) + ") " + questao.getAlternativas()[i]);
                }
                obterAlternativa(requestBody, questao.getAlternativas().length);

                Request request = construirRequest(null, requestBody);
                enviarMensagemServidor(saida, request);

                quantidadeQuestoes--;
            }

            response = (Response) entrada.readObject();
            String resultado = (String) response.getBody().get("response");

            System.out.println("\n" + resultado);

            cliente.close();
            System.out.println("Conexão encerrada");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
	}

    private static void obterInformacoesAutenticacaoUsuario(Map<Object, Object> requestBody) {
        System.out.print("\nMatrícula: ");
        String matricula = input.nextLine();

        System.out.print("Senha: ");
        String senha = input.nextLine();

        requestBody.put("matricula", matricula);
        requestBody.put("senha", senha);
    }

    private static void obterAlternativa(Map<Object, Object> requestBody, int alternativas) {
        int alternativa = 0;
        do {
            System.out.print("\n>>>: ");
            alternativa = input.nextInt();
        } while (alternativa < 1 || alternativa > alternativas);

        requestBody.put("alternativa", alternativa);
    }


    private static Request construirRequest(Header header, Map<Object, Object> body) {
        return new Request(header, body);
    }

    private static void enviarMensagemServidor(ObjectOutputStream saida, Request request) throws IOException {
        saida.writeObject(request);
        saida.flush();
    }
}
