import java.io.PrintStream;
import java.util.Scanner;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class MenuCliente {
    private Scanner teclado;
    private Scanner entradaServidor;   // corresponde ao "e" no Cliente.java
    private PrintStream saidaServidor; // corresponde ao "ESCREVE_NO_SOCKET"

    public MenuCliente(Scanner teclado, Scanner entradaServidor, PrintStream saidaServidor) {
        this.teclado = teclado;
        this.entradaServidor = entradaServidor;
        this.saidaServidor = saidaServidor;
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
        byte[] bytes = Files.readAllBytes(Paths.get("imagem.png"));
        String base64 = Base64.getEncoder().encodeToString(bytes);
        saidaServidor.println(base64);
    }

    private void receberImg() throws IOException {
        String respostaBase64 = entradaServidor.nextLine();
        byte[] imgBytes = Base64.getDecoder().decode(respostaBase64);
        Files.write(Paths.get("imagem_recebida.png"), imgBytes);
        System.out.println("IMG RECEBIDA COM SUCESSO!!!");
    }

    private void realizarOperacao(int operacao){
        System.out.print("Digite o primeiro número:");
        double n1 = Double.parseDouble(teclado.nextLine());
        System.out.print("Digite o segundo número:");
        double n2 = Double.parseDouble(teclado.nextLine());
        saidaServidor.println(operacao + ";" + n1 + ";" + n2);

        String resposta = entradaServidor.nextLine();
        System.out.println("Resultado: " + resposta);
    }

    private void enviarMensagem(){
        System.out.print("Digite a mensagem:");
        String msg = teclado.nextLine();

        saidaServidor.println(msg);
        String resposta = entradaServidor.nextLine();
        System.out.println("Servidor:" + resposta);
    }
}
