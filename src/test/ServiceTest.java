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

    private static final MediaResource MEDIA_RESOURCE = new MediaResource();

    // -----------------------------------------
    // ----------------- Books -----------------
    // -----------------------------------------

    /**
     * Medienisten leeren.
     */
    @Before
    public void clearLists() {
        MEDIA_RESOURCE.clearLists();
    }

    /**
     * Test zum Hinzufügen eines validen Buchs.
     */
    @Test
    public void addingValidBook() {
        Book newBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        Response result = MEDIA_RESOURCE.createBook(newBook);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());
    }

    /**
     * Test zum Hinzufügen eines Buches mit bereits vorhandener ISBN.
     */
    @Test
    public void addingBookWithAlreadyExistingISBN() {
        Book firstNewBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        Book secondNewBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");

        Response result = MEDIA_RESOURCE.createBook(firstNewBook);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());

        result = MEDIA_RESOURCE.createBook(secondNewBook);
        assertEquals(MediaServiceResult.ALREADY_IN_LIST.getCode(), result.getStatus());
    }

    /**
     * Test, ob ein invalides Buch (leeres Feld) erkannt wird.
     */
    @Test
    public void noEmptyFieldsInBookAccepted() {
        Book firstBook = new Book("Tanenbaum", "", "Moderne Betriebssysteme");
        Response result = MEDIA_RESOURCE.createBook(firstBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Book secondBook = new Book("", "978-3-8273-7", "Moderne Betriebssysteme");
        result = MEDIA_RESOURCE.createBook(secondBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Book thirdBook = new Book("Tanenbaum", "978-3-8273-7", "");
        result = MEDIA_RESOURCE.createBook(thirdBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob das richtige Buch geladen wird, wenn mit ISBN gesucht wird.
     */
    @Test
    public void getBookByExistingISBN() {
        Book newBook = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        MEDIA_RESOURCE.createBook(newBook);
        Book book = MEDIA_RESOURCE.getBookByISBN("978-3-8273-7");
        assertEquals(book, newBook);
    }

    /**
     * Test, ob null, wenn ein Buch mit der gesuchten ISBN nicht in der Liste ist.
     */
    @Test
    public void getBookByUnexistingISBN() {
        Book book = MEDIA_RESOURCE.getBookByISBN("978-3-8273-7");
        assertEquals(book, null);
    }

    /**
     * Test, ob alle Bücher in Liste.
     */
    @Test
    public void getBooks() {
        Book bookOne = new Book("Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        MEDIA_RESOURCE.createBook(bookOne);

        Book bookTwo = new Book("Tanenbaum", "3-8273-7151-1", "Computerarchitektur");
        MEDIA_RESOURCE.createBook(bookTwo);

        Book bookThree = new Book("Tanenbaum", "978-3-8273-7097-6", "Compiler");
        MEDIA_RESOURCE.createBook(bookThree);

        List<Book> books = MEDIA_RESOURCE.getBooks();
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
        MEDIA_RESOURCE.createBook(oldBook);
        assertEquals(oldBook, MEDIA_RESOURCE.getBookByISBN("978-3-8273-7"));

        Book newBook = new Book("A. Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        MEDIA_RESOURCE.updateBook(newBook);
        assertEquals(newBook, MEDIA_RESOURCE.getBookByISBN("978-3-8273-7"));
    }

    /**
     * Test, ob Updaten eines Buches mit nicht vorhander ISBN zu NOT_VALID führt.
     */
    @Test
    public void updateBookISBNNotInList() {
        Book newBook = new Book("A. Tanenbaum", "978-3-8273-7", "Moderne Betriebssysteme");
        Response result = MEDIA_RESOURCE.updateBook(newBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob Updaten eines Buches mit nicht vorhander ISBN zu NOT_VALID führt.
     */
    @Test
    public void updateBookWithInvalidAttribute() {
        Book oldBook = new Book("Tanenbaum", "978-3-8273-7", "Betriebssysteme");
        MEDIA_RESOURCE.createBook(oldBook);
        assertEquals(oldBook, MEDIA_RESOURCE.getBookByISBN("978-3-8273-7"));

        Book newBook = new Book("", "978-3-8273-7", "Moderne Betriebssysteme");
        Response result = MEDIA_RESOURCE.updateBook(newBook);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Book newBook2 = new Book("Andrew", "978-3-8273-7", "");
        result = MEDIA_RESOURCE.updateBook(newBook2);
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
        Disc newDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        Response result = MEDIA_RESOURCE.createDisc(newDisc);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());
    }

    /**
     * Test zum Hinzufügen einer Disc mit bereits vorhandenem Barcode.
     */
    @Test
    public void addingDiscWithAlreadyExistingBarcode() {
        Disc firstDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        Disc secondDisc = new Disc("5050582810837", "R. Michell", 0, "Notting Hill");

        Response result = MEDIA_RESOURCE.createDisc(firstDisc);
        assertEquals(MediaServiceResult.CREATED.getCode(), result.getStatus());

        result = MEDIA_RESOURCE.createDisc(secondDisc);
        assertEquals(MediaServiceResult.ALREADY_IN_LIST.getCode(), result.getStatus());
    }

    /**
     * Test, ob eine invalide Disc (leeres Feld) erkannt wird.
     */
    @Test
    public void noEmptyFieldsInDiscAccepted() {
        Disc firstDisc = new Disc("", "Roger Michell", 0, "Notting Hill");
        Response result = MEDIA_RESOURCE.createDisc(firstDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Disc secondDisc = new Disc("5050582810837", "", 0, "Notting Hill");
        result = MEDIA_RESOURCE.createDisc(secondDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Disc thirdDisc = new Disc("5050582810837", "Roger Michell", 0, "");
        result = MEDIA_RESOURCE.createDisc(thirdDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob die richtige Disc geladen wird, wenn mit Barcode gesucht wird.
     */
    @Test
    public void getDiscByExistingBarcode() {
        Disc newDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        MEDIA_RESOURCE.createDisc(newDisc);
        Disc disc = MEDIA_RESOURCE.getDiscByBarcode("5050582810837");
        assertEquals(disc, newDisc);
    }

    /**
     * Test, ob null, wenn eine Disc mit der gesuchten Barcode nicht in der Liste ist.
     */
    @Test
    public void getDiscByUnexistingBarcode() {
        Disc disc = MEDIA_RESOURCE.getDiscByBarcode("5050582810837");
        assertEquals(disc, null);
    }

    /**
     * Test, ob alle Discs in Liste.
     */
    @Test
    public void getDiscs() {
        final int sixteen = 16;
        final int twelve = 12;

        Disc discOne = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        MEDIA_RESOURCE.createDisc(discOne);


        Disc discTwo = new Disc("4011976318088", "Paul McGuigan", sixteen, "Lucky Number Slevin");
        MEDIA_RESOURCE.createDisc(discTwo);

        Disc discThree = new Disc("4010232067777", "Ridley Scott", twelve, "The Martian");
        MEDIA_RESOURCE.createDisc(discThree);

        List<Disc> discs = MEDIA_RESOURCE.getDiscs();
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
     * Test, ob Updaten einer Disc funktioniert.
     */
    @Test
    public void updateDisc() {
        Disc oldDisc = new Disc("5050582810837", "R. Michell", 0, "Hill");
        MEDIA_RESOURCE.createDisc(oldDisc);
        assertEquals(oldDisc, MEDIA_RESOURCE.getDiscByBarcode("5050582810837"));

        Disc newDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        MEDIA_RESOURCE.updateDisc(newDisc);
        assertEquals(newDisc, MEDIA_RESOURCE.getDiscByBarcode("5050582810837"));
    }

    /**
     * Test, ob Updaten einer Disc mit nicht vorhander ISBN zu NOT_VALID führt.
     */
    @Test
    public void updateDiscISBNNotInList() {
        Disc newDisc = new Disc("5050582810837", "Roger Michell", 0, "Notting Hill");
        Response result = MEDIA_RESOURCE.updateDisc(newDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }

    /**
     * Test, ob Updaten einer Disc mit fehlenden Attributen zu NOT_VALID führt.
     */
    @Test
    public void updateDiscWithInvalidAttribute() {
        Disc oldDisc = new Disc("5050582810837", "R. Michell", 0, "Notting Hill");
        MEDIA_RESOURCE.createDisc(oldDisc);
        assertEquals(oldDisc, MEDIA_RESOURCE.getDiscByBarcode("5050582810837"));

        Disc newDisc = new Disc("5050582810837", "", 0, "Notting Hill");
        Response result = MEDIA_RESOURCE.updateDisc(newDisc);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());

        Disc newDisc2 = new Disc("5050582810837", "Michell", 0, "");
        result = MEDIA_RESOURCE.updateDisc(newDisc2);
        assertEquals(MediaServiceResult.NOT_VALID.getCode(), result.getStatus());
    }
}