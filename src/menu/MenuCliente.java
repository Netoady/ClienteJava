package src.menu;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import src.img.ImagemService;

public class MenuCliente {
    private Scanner teclado;
    private Scanner entradaServidor;   // corresponde ao "e" no Cliente.java
    private PrintStream saidaServidor; // corresponde ao "ESCREVE_NO_SOCKET"
    private ImagemService imagemService;

    public MenuCliente(Scanner teclado, Scanner entradaServidor, PrintStream saidaServidor) {
        this.teclado = teclado;
        this.entradaServidor = entradaServidor;
        this.saidaServidor = saidaServidor;
        this.imagemService = new ImagemService();
    }

    public void iniciar() throws IOException {
        int opcao;
        do {
            mostrarMenu();
            opcao = Integer.parseInt(teclado.nextLine());

            switch (opcao) {
                case 1:
                    realizarOperacao(1);
                    break;
                case 2:
                    realizarOperacao(2);
                    break;
                case 3:
                    realizarOperacao(3);
                    break;
                case 4:
                    enviarImg();
                    receberImg();
                    break;
                case 5:
                    enviarMensagem();
                    break;
                case 0:
                    saidaServidor.println("0"); // Avisa o servidor sobre o encerramento
                    System.out.println("SAINDO DO PROGRAMA!!!!!!");
                    break;
                default:
                    System.out.println("!!!!!OPÇÃO INVÁLIDA, TENTE NOVAMENTE!!!!!");
            }
        } while (opcao != 0);
    }

    private void mostrarMenu() {
        System.out.println("!!MENU!!");
        System.out.println("1 - OPÇÃO 1-(Somar)");
        System.out.println("2 - OPÇÃO 2-(Subtrair)");
        System.out.println("3 - OPÇÃO 3-(Multiplicar)");
        System.out.println("4 - OPÇÃO 4-(Imagem Base64)");
        System.out.println("5 - OPÇÃO 5-(Mensagem)");
        System.out.println("0 - SAIR");
    }

    private void enviarImg() throws IOException {
        saidaServidor.println("4"); // Notifica o servidor que o envio de imagem será iniciado
        String base64 = imagemService.converterParaBase64("imagem.png");
        saidaServidor.println(base64);
    }

    private void receberImg() throws IOException {
        String respostaBase64 = entradaServidor.nextLine();
        imagemService.salvarBase64(respostaBase64, "imagem_recebida.png");
        System.out.println("IMG RECEBIDA COM SUCESSO!!!");
    }

    private void realizarOperacao(int operacao) {
        System.out.print("Digite o primeiro número:");
        double n1 = Double.parseDouble(teclado.nextLine());
        System.out.print("Digite o segundo número:");
        double n2 = Double.parseDouble(teclado.nextLine());
        saidaServidor.println(operacao + ";" + n1 + ";" + n2);

        String resposta = entradaServidor.nextLine();
        System.out.println("Resultado: " + resposta);
    }

    private void enviarMensagem() {
        System.out.print("Digite a mensagem:");
        String msg = teclado.nextLine();

        saidaServidor.println("5;" + msg); 
        String resposta = entradaServidor.nextLine();
        System.out.println("Servidor:" + resposta);
    }
}