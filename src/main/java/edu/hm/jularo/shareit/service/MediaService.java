package edu.hm.jularo.shareit.service;

import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;

public interface MediaService {

    MediaServiceResult addBook(Book book);
    MediaServiceResult addDisc(Disc disc);
    Book getBook(String isbn);
    Book[] getBooks();
    Disc getDisc(String barcode);
    Disc[] getDiscs();
    MediaServiceResult updateBook(Book book);
    MediaServiceResult updateDisc(Disc disc);

}
