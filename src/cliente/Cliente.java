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

                input = formarComando(input);
                out.println(input);

                String response;
                StringBuilder fullResponse = new StringBuilder();
                while ((response = in.readLine()) != null) {
                    fullResponse.append(response).append("\n");
                    if (!in.ready()) { // Verifica se há mais linhas para ler
                        break;
                    }
                }
                System.out.println("Resposta do servidor: " + fullResponse.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formarComando(String input) {
        String[] parts = input.split(";");
        if (parts.length > 1) {
            parts[1] = capitalizeWords(parts[1]);
        }
        return String.join(";", parts);
    }

    private static String capitalizeWords(String str) {
        String[] words = str.toLowerCase().split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return capitalized.toString().trim();
    }
}
