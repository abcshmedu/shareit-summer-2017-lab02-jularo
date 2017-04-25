package edu.hm.jularo.shareit.models;

public class Book extends Medium{

    private final String author;
    private final String isbn;

    public Book(){
        super(null);
        author = null;
        isbn = null;
    }

    public Book(String author, String isbn, String title){
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

    public String getAuthor(){
        return author;
    }

    public String getIsbn(){
        return isbn;
    }

    @Override
    public String toString() {
        return "Book{" +
                super.toString() +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (getAuthor() != null ? !getAuthor().equals(book.getAuthor()) : book.getAuthor() != null) return false;
        return getIsbn() != null ? getIsbn().equals(book.getIsbn()) : book.getIsbn() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getIsbn() != null ? getIsbn().hashCode() : 0);
        return result;
    }
}
