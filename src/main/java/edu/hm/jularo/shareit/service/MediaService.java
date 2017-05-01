package edu.hm.jularo.shareit.service;

import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;

import java.util.List;

/**
 * Interface für MediaServiceImpl.
 *
 * @author Juliane Seidl
 * @author Carolin Direnberger
 */
public interface MediaService {

    /**
     * Fügt ein Buch zur Bücherliste hinzu.
     *
     * @param book Das Buch, welches hinzugefügt werden soll.
     * @return Responsestatus
     */
    MediaServiceResult addBook(Book book);

    /**
     * Fügt eine Disc zur Discliste hinzu.
     *
     * @param disc Die Disc, welche hinzugefügt werden soll.
     * @return Responsestatus
     */
    MediaServiceResult addDisc(Disc disc);

    /**
     * Sucht ein Buch mit Hilfe der ISBN.
     *
     * @param isbn Die ISBN des Buches
     * @return Responsestatus
     */
    Book getBookByISBN(String isbn);

    /**
     * Gibt die Bücherliste aus.
     *
     * @return Responsestatus
     */
    List<Book> getBooks();

    /**
     * Sucht ein Buch mit Hilfe des Barcodes.
     *
     * @param barcode Der Barcode des Buches
     * @return Responsestatus
     */
    Disc getDiscByBarcode(String barcode);

    /**
     * Gibt die Discliste aus.
     *
     * @return Responsestatus
     */
    List<Disc> getDiscs();

    /**
     * Aktualisiert ein Buch.
     *
     * @param book Das zu ändernde Buch
     * @return Responsestatus
     */
    MediaServiceResult updateBook(Book book);

    /**
     * Aktualisiert eine Disc.
     *
     * @param disc Die zu ändernde Disc
     * @return Responsestatus
     */
    MediaServiceResult updateDisc(Disc disc);

    /**
     * Leert die Medienlisten.
     */
    void clearLists();
}
