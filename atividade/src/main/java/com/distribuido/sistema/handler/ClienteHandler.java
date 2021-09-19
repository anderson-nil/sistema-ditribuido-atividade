package com.distribuido.sistema.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.distribuido.sistema.Utils.Request;
import com.distribuido.sistema.Utils.Response;
import com.distribuido.sistema.Utils.TokenGenerator;
import com.distribuido.sistema.db.AlunosDTO;
import com.distribuido.sistema.db.QuestoesDTO;
import com.distribuido.sistema.model.Questao;
import com.distribuido.sistema.model.Usuario;

public class ClienteHandler extends Thread {
    
    private final Socket cliente;

    public ClienteHandler(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        Usuario usuarioAutenticado = null;

        try {
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            saida.writeObject(new String("Bem vindo ao servidor!!!\nDigite a matrícula e a senha"));
            saida.flush();

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

            // Autenticação
            do {
                Map<Object, Object> body = new HashMap<>();

                Request request = (Request) entrada.readObject();

                Optional<Usuario> usuario = loginValido(request);

                if (usuario.isPresent()) {
                    usuarioAutenticado = usuario.get();
                    body.put("response", "Login realizado com sucesso!");
                    body.put("token", TokenGenerator.generate());

                    Response response = construirResponse(body, 200);
                    enviarMensagemServidor(saida, response);
                } else {
                    body.put("response", "Matrícula e/ou senha inválidos digite novamente");
                    
                    Response response = construirResponse(body, 400);
                    enviarMensagemServidor(saida, response);
                }
            } while (Objects.isNull(usuarioAutenticado));

            System.out.println("Aluno: " + usuarioAutenticado.obterNomeCompleto() +  " autenticou no sistema\n");

            // Enviar as questões para o aluno
            List<Questao> questoes = QuestoesDTO.obterQuestoes();
            int quantidadeRespostasCertas = 0;

            Map<Object, Object> body = new HashMap<>();
            body.put("questionsQuantity", questoes.size());
            Response response = construirResponse(body, 200);
            enviarMensagemServidor(saida, response);

            int questaoIndex = 0;
            for(Questao questao: questoes) {
                body = new HashMap<>();
                body.put("response", questao);
                    
                System.out.println("\nEnviando questão " + (questaoIndex+1) + " para: " + usuarioAutenticado.obterNomeCompleto());

                response = construirResponse(body, 200);
                enviarMensagemServidor(saida, response);
                
                Request request = (Request) entrada.readObject();
                int resposta = (int) request.getBody().get("alternativa");

                System.out.println(usuarioAutenticado.obterNomeCompleto() + " respondeu a questão questão " + (questaoIndex+1));

                if (resposta == questao.getRespostaCerta()) {
                    quantidadeRespostasCertas++;
                }
                
                questaoIndex++;
            }

            body = new HashMap<>();
            String resultado = "\nO aluno: " + usuarioAutenticado.obterNomeCompleto() + " respondeu " + questoes.size() + " questões e acertou " + quantidadeRespostasCertas;
            body.put("response", resultado);

            response = construirResponse(body, 200);
            enviarMensagemServidor(saida, response);

            cliente.close();
        } catch (IOException|ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (Objects.nonNull(usuarioAutenticado)) {
                System.out.println("\nAluno: " + usuarioAutenticado.obterNomeCompleto() +  " saiu do sistema\n");
            }
        }
    }

    private static Response construirResponse(Map<Object, Object> body, int statusCode) {
        return new Response(body, statusCode);
    }

    private static Optional<Usuario> loginValido(Request request) {
		String matricula = (String) request.getBody().get("matricula");
		String senha = (String) request.getBody().get("senha");

		return AlunosDTO.obterAlunosMatriculados()
			.stream()
			.filter(u -> u.getMatricula().equals(matricula) && u.getSenha().equals(senha))
			.findAny();
	}

    private static void enviarMensagemServidor(ObjectOutputStream saida, Response response) throws IOException {
        saida.writeObject(response);
        saida.flush();
    }
}
