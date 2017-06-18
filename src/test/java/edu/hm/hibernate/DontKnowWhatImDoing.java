package edu.hm.hibernate;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;
import edu.hm.shareit.businessLayer.*;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DontKnowWhatImDoing {


    private static MediaService mediaService;

    @BeforeClass
    public static void setUp() throws Exception {
        Injector injector = Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(IMediaService.class).to(MediaService.class);
                bind(IMediaDAO.class).to(MediaDAO.class);
            }
        });

        mediaService = injector.getInstance(MediaService.class);
    }

    @Before
    public void resetDataBase() {
        mediaService.clearMap();
    }

    @Test
    public void createBook() throws Exception {
        MediaServiceResult result = mediaService.addBook(new Book("TAKEOVER", "Adler Olsen", "978-3-423-21648-7"));
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatusCode());
    }

    @Test
    public void createExistingBook() throws Exception {
        mediaService.addBook(new Book("TAKEOVER", "Adler Olsen", "978-3-423-21648-7"));
        MediaServiceResult result = mediaService.addBook(new Book("TAKEOVER", "Adler Olsen", "978-3-423-21648-7"));
        assertEquals(MediaServiceResult.DUPLICATE_ISBN.getStatusCode(), result.getStatusCode());
    }

    @Test
    public void getBook() throws Exception {
        Book testBook = new Book("C", "D", "978-3-423-21648-7");
        mediaService.addBook(testBook);
        Book book = (Book) mediaService.getBook("978-3-423-21648-7");
        assertEquals(book, testBook);
    }

    @Test
    public void getBooks() throws Exception {
        Book testBook = new Book("TAKEOVER", "Adler Olsen", "978-3-423-21648-7");
        Book testBook2 = new Book("Selfies", "Adler Olsen", "9783423431293");
        mediaService.addBook(testBook);
        mediaService.addBook(testBook2);

        Medium[] books =  mediaService.getBooks();

        boolean book1Found = false;
        boolean book2Found = false;
        for (Medium book : books) {
            if (book.equals(testBook))
                book1Found = true;
            if (book.equals(testBook2))
                book2Found = true;
        }

        assertTrue(book1Found && book2Found);
    }

    @Test
    public void updateBookTitleAndAuthor() throws Exception {
        Book testBook = new Book("TAKEOVER", "Adla Olsen", "978-3-423-21648-7");
        mediaService.addBook(testBook);
        MediaServiceResult response = mediaService.updateBook(new Book("Take Over", "Adler Olsen", "978-3-423-21648-7"), "978-3-423-21648-7");
        assertEquals(response.getStatusCode(), MediaServiceResult.OK.getStatusCode());
        Book result = (Book) mediaService.getBook(testBook.getIsbn());
        assertEquals("Take Over", result.getTitle());
        assertEquals("Adler Olsen", result.getAuthor());
    }


    @Test
    public void updateBookISBN() throws Exception {
        Book testBook = new Book("TAKEOVER", "Adler Olsen", "978-3-423-21648-7");
        mediaService.addBook(testBook);
        MediaServiceResult response = mediaService.updateBook(new Book("", "", "3423431296"), "978-3-423-21648-7");
        assertEquals(MediaServiceResult.MODIFYING_ISBN_NOT_ALLOWED.getStatusCode(), response.getStatusCode());
        Book result = (Book) mediaService.getBook(testBook.getIsbn());
        assertEquals(result.getTitle(), testBook.getTitle());
        assertEquals(result.getAuthor(), testBook.getAuthor());
        Book notFound = (Book) mediaService.getBook("test");
        assertEquals(notFound, null);
    }

    @Test
    public void updateBookNotFound() throws Exception {

        MediaServiceResult response = mediaService.updateBook(new Book("Lucky Number Slevin", "Paul McGuigan", null), "978-3-423-21648-7");
        assertEquals(MediaServiceResult.ISBN_NOT_FOUND.getStatusCode(), response.getStatusCode());

    }

    @Test
    public void createDisc() throws Exception {

        MediaServiceResult result = mediaService.addDisc(new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16));
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatusCode());

    }

    @Test
    public void createExistingDisc() throws Exception {

        mediaService.addDisc(new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16));
        MediaServiceResult result = mediaService.addDisc(new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16));
        assertEquals(MediaServiceResult.DUPLICATE_Barcode.getStatusCode(), result.getStatusCode());
    }

    @Test
    public void getDisc() throws Exception {
        Disc testDisc = new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16);
        mediaService.addDisc(testDisc);
        Disc disc = (Disc) mediaService.getDisc("4011976318088");
        assertEquals(disc, testDisc);

    }

    @Test
    public void getDiscs() throws Exception {

        Disc testDisc = new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16);
        Disc testDisc2 = new Disc("The Martian", "4010232067777", "Ridley Scott", 12);
        mediaService.addDisc(testDisc);
        mediaService.addDisc(testDisc2);

        Medium[] discs = mediaService.getDiscs();

        boolean disc1Found = false;
        boolean disc2Found = false;
        for (Medium disc : discs) {
            if (disc.equals(testDisc))
                disc1Found = true;
            if (disc.equals(testDisc2))
                disc2Found = true;
        }

        assertTrue(disc1Found && disc2Found);
    }

    @Test
    public void updateDiscTitle() throws Exception {
        Disc testDisc = new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16);
        mediaService.addDisc(testDisc);
        MediaServiceResult response = mediaService.updateDisc(new Disc("Lucky#Slevin", "", "", -1), testDisc.getBarcode());
        assertEquals(response.getStatusCode(), MediaServiceResult.OK.getStatusCode());
        Disc result = (Disc) mediaService.getDisc(testDisc.getBarcode());
        assertEquals(result.getTitle(), "Lucky#Slevin");
        assertEquals(result.getDirector(), testDisc.getDirector());
        assertEquals(result.getFsk(), testDisc.getFsk());
    }

    @Test
    public void updateDiscDirector() throws Exception {

        Disc testDisc = new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16);
        mediaService.addDisc(testDisc);
        MediaServiceResult response = mediaService.updateDisc(new Disc("", "", "P.McGuigan", -1), testDisc.getBarcode());
        assertEquals(MediaServiceResult.OK.getStatusCode(), response.getStatusCode());
        Disc result = (Disc) mediaService.getDisc(testDisc.getBarcode());
        assertEquals(result.getTitle(), testDisc.getTitle());
        assertEquals(result.getDirector(), "P.McGuigan");
        assertEquals(result.getFsk(), testDisc.getFsk());
    }

    @Test
    public void updateDiscFSK() throws Exception {

        Disc testDisc = new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16);
        mediaService.addDisc(testDisc);
        MediaServiceResult response = mediaService.updateDisc(new Disc("", "", "", 18), testDisc.getBarcode());
        assertEquals(MediaServiceResult.OK.getStatusCode(), response.getStatusCode());
        Disc result = (Disc) mediaService.getDisc(testDisc.getBarcode());
        assertEquals(result.getTitle(), testDisc.getTitle());
        assertEquals(result.getDirector(), testDisc.getDirector());
        assertEquals(18, result.getFsk());
    }

    @Test
    public void updateDiscBarcode() throws Exception {

        Disc testDisc = new Disc("Lucky Number Slevin", "4011976318088", "Paul McGuigan", 16);
        mediaService.addDisc(testDisc);
        MediaServiceResult response = mediaService.updateDisc(new Disc("", "4010232067777", "", -1), testDisc.getBarcode());
        assertEquals(MediaServiceResult.MODIFYING_BARCODE_NOT_ALLOWED.getStatusCode(), response.getStatusCode());
        Disc result = (Disc) mediaService.getDisc(testDisc.getBarcode());
        assertEquals(result.getTitle(), testDisc.getTitle());
        assertEquals(result.getDirector(), testDisc.getDirector());
        assertEquals(result.getFsk(), testDisc.getFsk());
        result = (Disc) mediaService.getDisc("haaaaa");
        assertEquals(result, null);
    }

    @Test
    public void updateDiscNotFound() throws Exception {

        MediaServiceResult response = mediaService.updateDisc(new Disc("", "", "", -1), "4010232067777");
        assertEquals(MediaServiceResult.BARCODE_NOT_FOUND.getStatusCode(), response.getStatusCode());


    }

}
