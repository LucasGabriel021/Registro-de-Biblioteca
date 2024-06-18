import java.io.*;
import java.net.*;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Servidor {
    private static final int PORT = 12345;
    public static List<Livro> livros = new ArrayList<>();
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

    public static void loadLivros() {
        try (InputStream inputStream = new FileInputStream("src/servidor/livros.json")) {
            livros = mapper.readValue(inputStream, new TypeReference<List<Livro>>() {});
            System.out.println(livros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveLivros() {
        try {
            mapper.writeValue(new File("src/servidor/livros.json"), livros);
            System.out.println("Livros salvos com sucesso no arquivo ");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar os livros: " + e.getMessage());
        }
    }
}
