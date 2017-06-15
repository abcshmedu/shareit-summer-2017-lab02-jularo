package edu.hm.shareit.businessLayer;

import edu.hm.shareit.models.Book;

import java.util.List;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public interface IMediaDAO {
    void createBook(Book book);

    void deleteBook(Book book);

    Book getBook(String isbn);

    List<Book> getBooks();

    void updateBook(Book book);
}
