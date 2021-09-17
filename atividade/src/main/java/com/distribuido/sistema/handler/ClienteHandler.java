package com.distribuido.sistema.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.distribuido.sistema.Utils.Request;
import com.distribuido.sistema.Utils.Response;
import com.distribuido.sistema.Utils.TokenGenerator;
import com.distribuido.sistema.Utils.Usuario;
import com.distribuido.sistema.db.AlunosDTO;

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

            do {
                Map<Object, Object> body = new HashMap<>();

                Request request = (Request) entrada.readObject();

                Optional<Usuario> usuario = loginValido(request);

                if (usuario.isPresent()) {
                    usuarioAutenticado = usuario.get();
                    body.put("response", "Login realizado com sucesso!");
                    body.put("token", TokenGenerator.generate());

                    Response response = construirResponse(body, 200);
                    saida.writeObject(response);
                    saida.flush();
                } else {
                    body.put("response", "Matrícula e/ou senha inválidos digite novamente");
                    
                    Response response = construirResponse(body, 400);
                    saida.writeObject(response);
                    saida.flush();
                }
            } while (Objects.isNull(usuarioAutenticado));

            System.out.println("Aluno: " + usuarioAutenticado.obterNomeCompleto() +  " autenticou no sistema\n");






            cliente.close();
        } catch (IOException|ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        } finally {
            if (Objects.nonNull(usuarioAutenticado)) {
                System.out.println("Aluno: " + usuarioAutenticado.obterNomeCompleto() +  " saiu do sistema\n");
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
}
