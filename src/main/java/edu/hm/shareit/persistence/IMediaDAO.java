package edu.hm.shareit.persistence;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

import java.util.List;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public interface IMediaDAO {
    void addBook(Book book);

    Book getBook(String isbn);

    List<Book> getBooks();

    void updateBook(Book book);


    void addDisc(Disc disc);

    Disc getDisc(String barcode);

    List<Disc> getDiscs();

    void updateDisc(Disc disc);
}
