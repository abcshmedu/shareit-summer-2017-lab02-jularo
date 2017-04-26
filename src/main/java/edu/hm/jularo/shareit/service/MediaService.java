package edu.hm.jularo.shareit.service;

import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;

import java.util.List;

public interface MediaService {

    MediaServiceResult addBook(Book book);

    MediaServiceResult addDisc(Disc disc);

    Book getBookByISBN(String isbn);

    List<Book> getBooks();

    Disc getDiscByBarcode(String barcode);

    List<Disc> getDiscs();

    MediaServiceResult updateBook(Book book);

    MediaServiceResult updateDisc(Disc disc);

}
