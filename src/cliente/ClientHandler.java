import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler extends Thread {

    Servidor servidor = new Servidor();
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                String[] parts = request.split(";");
                System.out.println(Arrays.toString(parts));
                if (parts.length < 1) {
                    out.println("Comando inválido!");
                    continue;
                }
                String command = parts[0];

                switch (command) {
                    case "LISTA":
                        listAllBooks(out);
                        break;

                    case "ALUGUEL":
                        if (parts.length < 2) {
                            out.println("Comando inválido!");
                            break;
                        }
                        String tituloAlugar = parts[1];
                        rentBook(tituloAlugar, out);
                        break;

                    case "DEVOLUCAO":
                        if (parts.length < 2) {
                            out.println("Comando inválido!");
                            break;
                        }
                        String tituloDevolver = parts[1];
                        returnBook(tituloDevolver, out);
                        break;

                    case "ADICIONA":
                        if (parts.length < 5) {
                            out.println("Comando inválido!");
                            break;
                        }
                        String tituloAdd = parts[1];
                        String autor = parts[2];
                        String generoAdd = parts[3];
                        int exemplares = Integer.parseInt(parts[4]);
                        addBook(new Livro(tituloAdd, autor, generoAdd, exemplares), out);
                        break;

                    case "GENERO":
                        if (parts.length < 2) {
                            out.println("Comando inválido!");
                            break;
                        }

                        String generoSearch = parts[1];
                        listBooksByGenre(generoSearch, out);
                        break;

                    default:
                        out.println("Comando não reconhecido.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listAllBooks(PrintWriter out) {
        StringBuilder response = new StringBuilder();
        for (Livro livro : servidor.livros) {
            response.append(livro).append("\n\n"); // Adiciona duas quebras de linha entre os livros
        }
        out.println(response.toString());
    }

    private void listBooksByGenre(String genero, PrintWriter out) {
        StringBuilder response = new StringBuilder();
        boolean found = false;

        for (Livro livro : servidor.livros) {
            if (livro.getGenero().equalsIgnoreCase(genero)) {
                response.append(livro).append("\n\n");
                found = true;
            }
        }
        if (!found) {
            out.println("Nenhum livro encontrado para o gênero: " + genero);
        } else {
            out.println(response.toString());
        }
    }

    private void rentBook(String titulo, PrintWriter out) {
        for (Livro livro : servidor.livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getExemplares() > 0) {
                livro.setExemplares(livro.getExemplares() - 1);
                servidor.saveLivros();
                out.println("Alugado: " + livro);
                return;
            }
        }
        out.println("Livro não disponível.");
    }

    private void returnBook(String titulo, PrintWriter out) {
        for (Livro livro : servidor.livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livro.setExemplares(livro.getExemplares() + 1);
                servidor.saveLivros();
                out.println("Devolvido: " + livro);
                return;
            }
        }
        out.println("Livro não encontrado.");
    }

    private void addBook(Livro livro, PrintWriter out) {
        System.out.println("Adicionando livro: " + livro);
        servidor.livros.add(livro);
        servidor.saveLivros();
        out.println("Adicionado: " + livro);
        System.out.println("Lista de livros após adição: " + servidor.livros);
    }

}
