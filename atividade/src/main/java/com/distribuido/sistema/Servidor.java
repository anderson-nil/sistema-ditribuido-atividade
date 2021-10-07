package com.distribuido.sistema;

import java.net.ServerSocket;
import java.net.Socket;

import com.distribuido.sistema.handler.ClienteHandler;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(8888);
            System.out.println("Servidor ouvindo a porta 8888");

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

                ClienteHandler clienteHandler = new ClienteHandler(cliente);
                clienteHandler.start();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
