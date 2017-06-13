package edu.hm.shareit.models;

import javax.persistence.Entity;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */

@Entity
public class Book extends Medium {

    private final String author;
    private final String isbn;

    /**
     * private default constructor is needed for reflection (Jackson)
     */
    private Book() {
        super("");
        author = "";
        isbn = "";
    }

    public Book(String title, String author, String isbn) {
        super(title);
        this.author = author!=null?author:"";
        this.isbn = isbn!=null?isbn:"";
    }

    @Override
    public String toString() {
        return "Book{" +
                super.toString() +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        return author.equals(book.author) && isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getIsbn() != null ? getIsbn().hashCode() : 0);
        return result;
    }
}
