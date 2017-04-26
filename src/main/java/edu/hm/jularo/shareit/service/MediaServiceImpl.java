package edu.hm.jularo.shareit.service;

import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;

import java.util.ArrayList;
import java.util.List;

public class MediaServiceImpl implements MediaService {

    private static final List<Book> bookList = new ArrayList<>();
    private static final List<Disc> discList = new ArrayList<>();

    @Override
    public MediaServiceResult addBook(Book book) {
        if (bookList.contains(book)) {
            return MediaServiceResult.ALREADY_IN_LIST;
        }

        if (book.isValid()) {
            bookList.add(book);
            return MediaServiceResult.CREATED;
        }
        return MediaServiceResult.NOT_VALID;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        if (discList.contains(disc)) {
            return MediaServiceResult.ALREADY_IN_LIST;
        }

        if (disc.isValid()) {
            discList.add(disc);
            return MediaServiceResult.CREATED;
        }
        return MediaServiceResult.NOT_VALID;
    }

    @Override
    public Book getBookByISBN(String isbn) {
        for (Book book : bookList) {
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }

    @Override
    public List<Book> getBooks() {
        return bookList;
    }

    @Override
    public Disc getDiscByBarcode(String barcode) {
        for (Disc disc : discList) {
            if (barcode.equals(disc.getBarcode())) {
                return disc;
            }
        }
        return null;
    }

    @Override
    public List<Disc> getDiscs() {
        return discList;
    }

    @Override
    public MediaServiceResult updateBook(Book updatedBook) {
        if (updatedBook != null) {
            Book bookToUpdate = getBookByISBN(updatedBook.getIsbn());
            if (bookToUpdate != null && bookToUpdate.isValid()) {
                bookList.remove(bookToUpdate);
                bookList.add(updatedBook);
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
                discList.remove(discToUpdate);
                discList.add(updatedDisc);
                return MediaServiceResult.UPDATED;
            }
        }

        return MediaServiceResult.NOT_ACCEPTABLE;
    }
}
