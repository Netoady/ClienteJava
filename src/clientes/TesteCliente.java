package src.clientes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TesteCliente {

    public static void main(String[] args) {
        int qtdClientes = 15;

        System.out.println("=== INICIANDO TESTE COM " + qtdClientes + " THREADS SIMULTÂNEAS ===");

        for (int i = 1; i <= qtdClientes; i++) {
            int id = i;
            new Thread(() -> {
                try {
                    Socket clienteSocket = new Socket("?.?.?.?", 12345);
                    System.out.println("[TESTE CLIENTE " + id + "] CONECTADO!");

                    Scanner e = new Scanner(clienteSocket.getInputStream());
                    PrintStream ESCREVE_NO_SOCKET = new PrintStream(clienteSocket.getOutputStream());

                    // 1. Operação de Soma (1;10;20)
                    ESCREVE_NO_SOCKET.println("1;10;20");
                    String respostaSoma = e.nextLine();
                    System.out.println("[TESTE CLIENTE " + id + "] Resposta Soma: " + respostaSoma);

                    // Pausa de 1 segundo simulando o tempo de resposta do usuário
                    Thread.sleep(1000);

                    // 2. Envio de Mensagem (5;...)
                    ESCREVE_NO_SOCKET.println("5;Ola Servidor do Cliente " + id);
                    String respostaMsg = e.nextLine();
                    System.out.println("[TESTE CLIENTE " + id + "] Resposta Mensagem: " + respostaMsg);

                    // Pausa de 1 segundo antes de desconectar
                    Thread.sleep(1000);

                    // Encerramento limpo (0)
                    ESCREVE_NO_SOCKET.println("0");

                    ESCREVE_NO_SOCKET.close();
                    e.close();
                    clienteSocket.close();
                    System.out.println("[TESTE CLIENTE " + id + "] Finalizado com sucesso.");

                } catch (IOException | InterruptedException ex) {
                    System.err.println("[TESTE CLIENTE " + id + "] Erro: " + ex.getMessage());
                }
            }).start();

            // Pequeno delay (50ms) entre os disparos para evitar thundering herd no accept
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}