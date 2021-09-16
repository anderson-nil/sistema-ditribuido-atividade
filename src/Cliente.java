import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Utils.Request;
import Utils.Response;

public class Cliente {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
		try {
			Socket cliente = new Socket("localhost", 8888);

            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            String resposta = (String) entrada.readObject();

            System.out.println("Servidor: " + resposta);

			ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());

            int statusAuth = 0;

            do {
                Map<Object, Object> requestBody = new HashMap<>();

                System.out.println("Matrícula: ");
                String matricula = input.nextLine();

                System.out.println("Senha: ");
                String senha = input.nextLine();

                requestBody.put("matricula", matricula);
                requestBody.put("senha", senha);

                saida.writeObject(
                    Request
                    .builder()
                    .body(requestBody)
                    .build());
                saida.flush();

                Response response = (Response) entrada.readObject();
                resposta = (String) response.getBody().get("response");
                statusAuth = response.getStatus();

                System.out.println(resposta);
            } while (statusAuth != 200);

			cliente.close();
			System.out.println("Conexão encerrada");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
