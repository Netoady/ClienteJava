package src.clientes;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import src.menu.MenuCliente;

public class Cliente1 {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket clienteSocket = new Socket("?.?.?.?",12345);
        System.out.println("[CLIENTE1] conectado");
        Scanner teclado = new Scanner(System.in);
        Scanner e = new Scanner(clienteSocket.getInputStream());
        PrintStream ESCREVE_NO_SOCKET = new PrintStream(clienteSocket.getOutputStream());

        MenuCliente menu = new MenuCliente(teclado, e, ESCREVE_NO_SOCKET);
        menu.iniciar();

        ESCREVE_NO_SOCKET.close();
        teclado.close();
        e.close();
        clienteSocket.close();
    }
}