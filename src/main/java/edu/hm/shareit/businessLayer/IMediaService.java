package edu.hm.shareit.businessLayer;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

import java.util.List;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public interface IMediaService {

    MediaServiceResult addBook(Book book);

    MediaServiceResult addDisc(Disc disc);

    List<Book> getBooks();

    List<Disc> getDiscs();

    Medium getBook(String isbn);

    Medium getDisc(String barcode);

    MediaServiceResult updateBook(Book book, String isbn);

    MediaServiceResult updateDisc(Disc disc, String barcode);

//    void clearMap();
}
