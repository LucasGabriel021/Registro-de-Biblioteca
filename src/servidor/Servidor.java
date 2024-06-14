import java.io.*;
import java.net.*;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Servidor {
    private static final int PORT = 12345;
    private static List<Livro> livros = new ArrayList<>();
    // private static final String FILE_PATH = "livros.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        loadLivros();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadLivros() {
        try (InputStream inputStream = Servidor.class.getResourceAsStream(("/livros.json"))){
            livros = mapper.readValue(inputStream, new TypeReference<List<Livro>>() {});
            System.out.println(livros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveLivros() {
        try {
            mapper.writeValue(new File("/src/servidor/livros.json"), livros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
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
                    System.out.println(parts);
                    if(parts.length < 1) {
                        out.println("Comando inválido!");
                        continue;
                    }
                    String command = parts[0];

                    switch (command) {
                        case "LISTA":
                            out.println(livros);
                            break;

                        case "ALUGUEL":
                            if(parts.length < 2) {
                                out.println("Comando inválido!");
                                break;
                            }
                            String tituloAlugar = parts[1];
                            rentBook(tituloAlugar, out);
                            break;

                        case "DEVOLUCAO":
                            if(parts.length < 2) {
                                out.println("Comando inválido!");
                                break;
                            }
                            String tituloDevolver = parts[1];
                            returnBook(tituloDevolver, out);
                            break;

                        case "ADICIONA":
                            if(parts.length < 5) {
                                out.println("Comando inválido!");
                                break;
                            }
                            String titulo = parts[1];
                            String autor = parts[2];
                            String genero = parts[3];
                            int exemplares = Integer.parseInt(parts[4]);
                            addBook(new Livro(titulo, autor, genero, exemplares), out);
                            break;

                        default:
                            out.println("Comando não reconhecido.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void rentBook(String titulo, PrintWriter out) {
            for (Livro livro : livros) {
                if (livro.getTitulo().equalsIgnoreCase(titulo) && livro.getExemplares() > 0) {
                    livro.setExemplares(livro.getExemplares() - 1);
                    saveLivros();
                    out.println("Livro alugado: " + livro);
                    return;
                }
            }
            out.println("Livro não disponível .");
        }

        private void returnBook(String titulo, PrintWriter out) {
            for (Livro livro : livros) {
                if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                    livro.setExemplares(livro.getExemplares() + 1);
                    saveLivros();
                    out.println("Livro devolvido: " + livro);
                    return;
                }
            }
            out.println("Livro não encontrado.");
        }

        private void addBook(Livro livro, PrintWriter out) {
            livros.add(livro);
            saveLivros();
            out.println("Livro adicionado: " + livro);
        }
    }
}

