package handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import Utils.Request;
import Utils.Response;
import Utils.Usuario;
import db.AlunosDTO;

public class ClienteHandler extends Thread {
    
    private final Socket cliente;

    public ClienteHandler(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            saida.writeObject(new String("Bem vindo ao servidor!!!\nDigite a matrícula e a senha"));
            saida.flush();

            Usuario usuarioAutenticado = null;

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

            do {
                Request request = (Request) entrada.readObject();

                Optional<Usuario> usuario = loginValido(request);

                if (usuario.isPresent()) {
                    usuarioAutenticado = usuario.get();
                } else {
                    Map<Object, Object> response = new HashMap<>();
                    response.put("response", "Matrícula e/ou senha inválidos digite novamente");

                    saida.writeObject(
                        Response
                            .builder()
                            .body(response)
                            .status(400)
                            .build()
                    );
                    saida.flush();
                }
            } while (Objects.isNull(usuarioAutenticado));

            cliente.close();
        } catch (IOException|ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
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
