import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;
import edu.hm.jularo.shareit.service.MediaResource;
import edu.hm.jularo.shareit.service.MediaServiceResult;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testklasse für Mediaservice.
 *
 * @author Juliane Seidl
 * @author Carolin Direnberger
 */

public class ServiceTest {

    private final static MediaResource mediaResource = new MediaResource();

    // -----------------------------------------
    // ----------------- Books -----------------
    // -----------------------------------------

    /**
     * Medienisten leeren.
     */
    @Before
    public void clearLists() {
        mediaResource.clearLists();
    }

    /**
     * Test zum Hinzufügen eines validen Buchs.
     */
    @Test
    public void addingValidBook() {
        Book newBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        Response result = mediaResource.createBook(newBook);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());
    }

    /**
     * Test zum Hinzufügen eines Buches mit bereits vorhandener ISBN.
     */
    @Test
    public void addingBookWithAlreadyExistingISBN() {
        Book firstNewBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        Book secondNewBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");

        Response result = mediaResource.createBook(firstNewBook);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());

        result = mediaResource.createBook(secondNewBook);
        assertEquals(MediaServiceResult.ALREADY_IN_LIST.getCode(), result.getStatus());
    }

    /**
     * Test, ob ein invalides Buch (leeres Feld) erkannt wird.
     */
    @Test
    public void noEmptyFieldsInBookAccepted() {
        Book firstBook = new Book("Tanenbaum", "", "Moderne Betriebssysteme");
        Response result = mediaResource.createBook(firstBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Book secondBook = new Book("", "978-3-8273-7", "Moderne Betriebssysteme");
        result = mediaResource.createBook(secondBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Book thirdBook = new Book("Tanenbaum", "978-3-8273-7", "");
        result = mediaResource.createBook(thirdBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob das richtige Buch geladen wird, wenn mit ISBN gesucht wird.
     */
    @Test
    public void getBookByExistingISBN() {
        Book newBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        mediaResource.createBook(newBook);
        Book book = mediaResource.getBookByISBN("978-3-8273-7");
        assertEquals(book, newBook);
    }

    /**
     * Test, ob null, wenn ein Buch mit der gesuchten ISBN nicht in der Liste ist.
     */
    @Test
    public void getBookByUnexistingISBN() {
        Book book = mediaResource.getBookByISBN("978-3-8273-7");
        assertEquals(book, null);
    }

    /**
     * Test, ob alle Bücher in Liste.
     */
    @Test
    public void getBooks() {
        Book bookOne = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        mediaResource.createBook(bookOne);

        Book bookTwo = new Book("Tanenbaum", "3-8273-7151-1", "Computerarchitektur");
        mediaResource.createBook(bookTwo);

        Book bookThree = new Book("Tanenbaum", "978-3-8273-7097-6", "Compiler");
        mediaResource.createBook(bookThree);

        List<Book> books = mediaResource.getBooks();
        boolean bookOneFound = false;
        boolean bookTwoFound = false;
        boolean bookThreeFound = false;
        for (Book book : books) {
            if (book.equals(bookOne)) {
                bookOneFound = true;
            } else if (book.equals(bookTwo)) {
                bookTwoFound = true;
            } else if (book.equals(bookThree)) {
                bookThreeFound = true;
            }
        }

        assertTrue(bookOneFound && bookTwoFound && bookThreeFound);
    }

    /**
     * Test, ob Updaten eines Buches funktioniert.
     */
    @Test
    public void updateBook() {
        Book oldBook = new Book("Tanenbaum", "978-3-8273-7", "Betriebssysteme");
        mediaResource.createBook(oldBook);
        assertEquals(oldBook, mediaResource.getBookByISBN("978-3-8273-7"));

        Book newBook = new Book("A. Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        mediaResource.updateBook(newBook);
        assertEquals(newBook, mediaResource.getBookByISBN("978-3-8273-7"));
    }

    /**
     * Test, ob Updaten eines Buches mit nicht vorhander ISBN zu NOT_VALID führt.
     */
    @Test
    public void updateBookISBNNotInList() {
        Book newBook = new Book("A. Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        Response result = mediaResource.updateBook(newBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob Updaten eines Buches mit nicht vorhander ISBN zu NOT_VALID führt.
     */
    @Test
    public void updateBookWithInvalidAttribute() {
        Book oldBook = new Book("Tanenbaum", "978-3-8273-7", "Betriebssysteme");
        mediaResource.createBook(oldBook);
        assertEquals(oldBook, mediaResource.getBookByISBN("978-3-8273-7"));

        Book newBook = new Book("", "978-3-8273-7", "Moderne Betriebssysteme");
        Response result = mediaResource.updateBook(newBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Book newBook2 = new Book("Andrew", "978-3-8273-7", "");
        result = mediaResource.updateBook(newBook2);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    // -----------------------------------------
    // ----------------- Discs -----------------
    // -----------------------------------------

    /**
     * Test zum Hinzufügen einer validen Disc.
     */
    @Test
    public void addingValidDisc() {
        Disc newDisk = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        Response result = mediaResource.createDisc(newDisk);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());
    }

    /**
     * Test zum Hinzufügen einer Disk mit bereits vorhandenem Barcode.
     */
    @Test
    public void addingDiscWithAlreadyExistingBarcode() {
        Disc firstDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        Disc secondDisc = new Disc("5050582810837", "R. Michell", 0, "Notting Hill");

        Response result = mediaResource.createDisc(firstDisc);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());

        result = mediaResource.createDisc(secondDisc);
        assertEquals(MediaServiceResult.ALREADY_IN_LIST.getCode(), result.getStatus());
    }

    /**
     * Test, ob eine invalide Disc (leeres Feld) erkannt wird.
     */
    @Test
    public void noEmptyFieldsInDiscAccepted() {
        Disc firstDisc = new Disc("", "Roger Michell", 0, "Notting Hill");
        Response result = mediaResource.createDisc(firstDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Disc secondDisc = new Disc("5050582810837", "", 0, "Notting Hill");
        result = mediaResource.createDisc(secondDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Disc thirdDisc = new Disc("5050582810837", "Roger Michell", 0, "");
        result = mediaResource.createDisc(thirdDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob die richtige Disc geladen wird, wenn mit Barcode gesucht wird.
     */
    @Test
    public void getDiscByExistingBarcode() {
        Disc newDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        mediaResource.createDisc(newDisc);
        Disc disc = mediaResource.getDiscByBarcode("5050582810837");
        assertEquals(disc, newDisc);
    }

    /**
     * Test, ob null, wenn eine Disc mit der gesuchten Barcode nicht in der Liste ist.
     */
    @Test
    public void getDiscByUnexistingBarcode() {
        Disc disc = mediaResource.getDiscByBarcode("5050582810837");
        assertEquals(disc, null);
    }

    /**
     * Test, ob alle Discs in Liste.
     */
    @Test
    public void getDiscs() {
        Disc discOne = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        mediaResource.createDisc(discOne);

        Disc discTwo = new Disc("4011976318088", "Paul McGuigan", 16, "Lucky Number Slevin");
        mediaResource.createDisc(discTwo);

        Disc discThree = new Disc("4010232067777", "Ridley Scott", 12, "The Martian");
        mediaResource.createDisc(discThree);

        List<Disc> discs = mediaResource.getDiscs();
        boolean discOneFound = false;
        boolean discTwoFound = false;
        boolean discThreeFound = false;
        for (Disc disc : discs) {
            if (disc.equals(discOne)) {
                discOneFound = true;
            } else if (disc.equals(discTwo)) {
                discTwoFound = true;
            } else if (disc.equals(discThree)) {
                discThreeFound = true;
            }
        }

        assertTrue(discOneFound && discTwoFound && discThreeFound);
    }

    /**
     * Test, ob Updaten einer Disk funktioniert.
     */
    @Test
    public void updateDisc() {
        Disc oldDisc = new Disc("5050582810837", "R. Michell", 0, "Hill");
        mediaResource.createDisc(oldDisc);
        assertEquals(oldDisc, mediaResource.getDiscByBarcode("5050582810837"));

        Disc newDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        mediaResource.updateDisc(newDisc);
        assertEquals(newDisc, mediaResource.getDiscByBarcode("5050582810837"));
    }

    /**
     * Test, ob Updaten einer Disk mit nicht vorhander ISBN zu NOT_VALID führt.
     */
    @Test
    public void updateDiscISBNNotInList() {
        Disc newDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        Response result = mediaResource.updateDisc(newDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob Updaten einer Disk mit fehlenden Attributen zu NOT_VALID führt.
     */
    @Test
    public void updateDiscWithInvalidAttribute() {
        Disc oldDisc = new Disc("5050582810837", "R. Michell", 0, "Notting Hill");
        mediaResource.createDisc(oldDisc);
        assertEquals(oldDisc, mediaResource.getDiscByBarcode("5050582810837"));

        Disc newDisc = new Disc("5050582810837", "", 0, "Notting Hill");
        Response result = mediaResource.updateDisc(newDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Disc newDisc2 = new Disc("5050582810837", "Michell", 0, "");
        result = mediaResource.updateDisc(newDisc2);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }
}