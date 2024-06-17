import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Cliente conectado.");

            while (true) {
                exibirMenu();
                String input = scanner.nextLine().toUpperCase();

                if (input.equals("SAIDA")) {
                    out.println(input);
                    break;
                }

                if (validarComando(input)) {
                    out.println(input);
                    if (input.equals("POPULARES")) {
                        String response = in.readLine();
                        System.out.println("Autores mais populares:\n" + response);
                    } else {
                        String response = in.readLine();
                        System.out.println("Resposta do servidor: " + response);
                    }
                } else {
                    System.out.println("Comando inválido.");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro de comunicação com o servidor: " + e.getMessage());
        }
    }

    private static void exibirMenu() {
        System.out.println("\n----- MENU DE OPÇÕES -----");
        System.out.println("LISTA");
        System.out.println("ALUGUEL;TITULO");
        System.out.println("DEVOLUCAO;TITULO");
        System.out.println("ADICIONA;TITULO;AUTOR;GENERO;EXEMPLARES");
        System.out.println("DISPONIVEL;TITULO_DO_LIVRO");
        System.out.println("POPULARES");
        System.out.println("SAIDA");
        System.out.println("--------------------------");
    }

    private static boolean validarComando(String input) {
        return input.matches("LISTA|ALUGUEL;.+|DEVOLUCAO;.+|ADICIONA;.+;.+;.+;\\d+|DISPONIVEL;.+|POPULARES|SAIDA");
    }
}

