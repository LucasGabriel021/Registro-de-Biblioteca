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
                System.out.println("\n----- MENU DE OPÇÕES -----");
                System.out.println("LISTA");
                System.out.println("ALUGUEL;TITULO");
                System.out.println("DEVOLUCAO;TITULO");
                System.out.println("ADICIONA;TITULO;AUTOR;GENERO;EXEMPLARES");
                System.out.println("GENERO;GENEROLIVRO");
                System.out.println("SAIDA");
                System.out.println("\nDigite um comando:");

                String input = scanner.nextLine().toUpperCase();

                if (input.equalsIgnoreCase("SAIDA")) {
                    break;
                }

                out.println(input);
                String response = in.readLine();
                System.out.println("Resposta do servidor: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

