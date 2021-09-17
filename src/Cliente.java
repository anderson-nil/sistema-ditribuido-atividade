import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Utils.Request;
import Utils.Response;
import Utils.Request.Header;

public class Cliente {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
		try {
			Socket cliente = new Socket("localhost", 8888);

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            
            Object resposta = entrada.readObject();
            System.out.println("Servidor: " + (String) resposta);

            int statusAuth = 0;
            int token = 0;

            do {
                Map<Object, Object> requestBody = new HashMap<>();
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

    private static Request construirRequest(Header header, Map<Object, Object> body) {
        return Request
            .builder()
            .header(header)
            .body(body)
            .build();
    }

    private static void enviarMensagemServidor(ObjectOutputStream saida, Request request) throws IOException {
        saida.writeObject(request);
        saida.flush();
    }
}
