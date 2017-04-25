package edu.hm.jularo.shareit.service;

import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;

public class MediaServiceImpl implements MediaService {
    @Override
    public MediaServiceResult addBook(Book book) {
        return null;
    }

    @Override
    public MediaServiceResult addDisc(Disc disc) {
        return null;
    }

    @Override
    public Book getBook(String isbn) {
        return null;
    }

    @Override
    public Book[] getBooks() {
        return new Book[0];
    }

    @Override
    public Disc getDisc(String barcode) {
        return null;
    }

    @Override
    public Disc[] getDiscs() {
        return new Disc[0];
    }

    @Override
    public MediaServiceResult updateBook(Book book) {
        return null;
    }

    @Override
    public MediaServiceResult updateDisc(Disc disc) {
        return null;
    }
}
