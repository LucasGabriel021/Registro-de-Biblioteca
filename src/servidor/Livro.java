import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Livro {
    private String autor;
    private String titulo;
    private String genero;
    private int exemplares;

    // Construtor padrão
    public Livro() {}

    // Construtor com parâmetros
    public Livro(String titulo, String autor, String genero, int exemplares) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.exemplares = exemplares;
    }

    // Getters e Setters
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getExemplares() {
        return exemplares;
    }

    public void setExemplares(int exemplares) {
        this.exemplares = exemplares;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Gênero: " + genero + "\n" +
                "Exemplares: " + exemplares;
    }

}
