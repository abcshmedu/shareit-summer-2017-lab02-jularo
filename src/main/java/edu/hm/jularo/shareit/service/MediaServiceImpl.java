package edu.hm.jularo.shareit.service;

import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse enthält die Methoden zum Bearbeiten der Medienlisten.
 *
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
public class MediaServiceImpl implements MediaService {

    private static final List<Book> BOOK_LIST = new ArrayList<>();
    private static final List<Disc> DISC_LIST = new ArrayList<>();

    @Override
    public MediaServiceResult addBook(Book book) {
        if (BOOK_LIST.contains(book)) {
            return MediaServiceResult.ALREADY_IN_LIST;
        }

        if (book.isValid()) {
            BOOK_LIST.add(book);
            return MediaServiceResult.CREATED;
        }
        return MediaServiceResult.NOT_VALID;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        if (DISC_LIST.contains(disc)) {
            return MediaServiceResult.ALREADY_IN_LIST;
        }

        if (disc.isValid()) {
            DISC_LIST.add(disc);
            return MediaServiceResult.CREATED;
        }
        return MediaServiceResult.NOT_VALID;
    }

    @Override
    public Book getBookByISBN(String isbn) {
        for (Book book : BOOK_LIST) {
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> getBooks() {
        return BOOK_LIST;
    }

    @Override
    public Disc getDiscByBarcode(String barcode) {
        for (Disc disc : DISC_LIST) {
            if (barcode.equals(disc.getBarcode())) {
                return disc;
            }
        }
        return null;
    }

    @Override
    public List<Disc> getDiscs() {
        return DISC_LIST;
    }

    @Override
    public MediaServiceResult updateBook(Book updatedBook) {
        if (updatedBook != null) {
            Book bookToUpdate = getBookByISBN(updatedBook.getIsbn());
            if (bookToUpdate != null && bookToUpdate.isValid()) {
                BOOK_LIST.remove(bookToUpdate);
                BOOK_LIST.add(updatedBook);
                return MediaServiceResult.UPDATED;
            }
        }

        return MediaServiceResult.NOT_ACCEPTABLE;
    }

    @Override
    public MediaServiceResult updateDisc(Disc updatedDisc) {
        if (updatedDisc != null) {
            Disc discToUpdate = getDiscByBarcode(updatedDisc.getBarcode());
            if (discToUpdate != null && discToUpdate.isValid()) {
                DISC_LIST.remove(discToUpdate);
                DISC_LIST.add(updatedDisc);
                return MediaServiceResult.UPDATED;
            }
        }

        return MediaServiceResult.NOT_ACCEPTABLE;
    }
}
