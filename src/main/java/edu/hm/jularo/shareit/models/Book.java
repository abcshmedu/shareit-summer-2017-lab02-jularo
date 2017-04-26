package edu.hm.jularo.shareit.models;

/**
 * Model für Bücher.
 *
 * @author Juliane Seidl
 * @author Carolin Direnberger
 */
public class Book extends Medium {

    private final String author;
    private final String isbn;

    /**
     * Kontruktor.
     */
    public Book() {
        super("");
        author = "";
        isbn = "";
    }

    /**
     * Konstruktor.
     *
     * @param author Der Autor des Buches
     * @param isbn   Die ISBN des Buches
     * @param title  Der Titel des Buches
     */
    public Book(String author, String isbn, String title) {
        super(title);
        if (author == null || isbn == null) {
            throw new IllegalArgumentException("isbn and author must not be null");
        }
        this.author = author;
        this.isbn = isbn;
    }

    /**
     * Einfacher Getter für den Autor.
     *
     * @return author
     */
    private String getAuthor() {
        return author;
    }

    /**
     * Einfacher Getter für die ISBN.
     *
     * @return isbn
     */
    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append(" \"title\":\"" + getTitle() + "\",\n");
        builder.append(" \"isbn\":\"" + isbn + "\",\n");
        builder.append(" \"author\":\"" + author + "\"\n");
        builder.append("}\n");

        return builder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        Book book = (Book) object;
        return author.equals(book.getAuthor())
                && isbn.equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + isbn.hashCode();
        return result;
    }

    /**
     * Prüft, ob der Titel und der Autor des Buches gefüllt sind.
     *
     * @return true wenn valid
     */
    public boolean isValid() {
        return !author.isEmpty() && !getTitle().isEmpty();
    }
}
