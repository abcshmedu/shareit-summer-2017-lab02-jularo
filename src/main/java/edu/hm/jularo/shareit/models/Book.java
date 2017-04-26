package edu.hm.jularo.shareit.models;

public class Book extends Medium {

    private final String author;
    private final String isbn;

    public Book() {
        super("");
        author = "";
        isbn = "";
    }

    public Book(String author, String isbn, String title) {
        super(title);
        if (author == null || isbn == null) {
            throw new IllegalArgumentException("isbn and author must not be null");
        }
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        //return "Book{ Titel: "+ getTitle() + " - Author='" + author + '\'' + ", isbn='" + isbn + '\'' + '}';

        StringBuilder builder = new StringBuilder();
            builder.append("{\n");
            builder.append(" \"title\":\""+getTitle()+"\",\n");
            builder.append(" \"isbn\":\""+isbn+"\",\n");
            builder.append(" \"author\":\""+author+ "\"\n");
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
}
