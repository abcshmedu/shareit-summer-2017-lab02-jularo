package edu.hm.hibernate;

import edu.hm.shareit.businessLayer.MediaService;
import edu.hm.shareit.businessLayer.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.persistence.MediaDAO;
import edu.hm.shareit.resource.MediaRessource;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DontKnowWhatImDoing {


    private static Book bookMock, bookMock2;
    private static Disc discMock, discMock2;
    private static MediaRessource mediaRessource;

    private static MediaDAO mediaDAOMock;

    private static final HashMap<String, Book> books = new HashMap<>();
    private static final HashMap<String, Disc> discs = new HashMap<>();

    @Before
    public void init() throws Exception {
        mocking();

        MediaService mediaService = new MediaService(mediaDAOMock);
        mediaRessource = new MediaRessource(mediaService);
    }

    private void mocking() {
        mediaDAOMock = mock(MediaDAO.class);
        booksMocking();
        discsMocking();
    }

    private void booksMocking() {
        bookMock = mock(Book.class);
        when(bookMock.getAuthor()).thenReturn("Adler Olsen");
        when(bookMock.getTitle()).thenReturn("TAKEOVER");
        when(bookMock.getIsbn()).thenReturn("9783423216487");

        bookMock2 = mock(Book.class);
        when(bookMock2.getAuthor()).thenReturn("Adler Olsen");
        when(bookMock2.getTitle()).thenReturn("Selfies");
        when(bookMock2.getIsbn()).thenReturn("9783423281072");

        doAnswer(
                Void -> {
                    books.put(bookMock.getIsbn(), bookMock);
                    return null;
                }
        ).when(mediaDAOMock).addBook(bookMock);

        doAnswer(
                Void -> {
                    books.remove(bookMock.getIsbn());
                    books.put(bookMock.getIsbn(), bookMock);
                    return null;
                }
        ).when(mediaDAOMock).updateBook(bookMock);
    }

    private void discsMocking() {
        discMock = mock(Disc.class);
        when(discMock.getDirector()).thenReturn("Paul McGuigan");
        when(discMock.getTitle()).thenReturn("Lucky Number Slevin");
        when(discMock.getBarcode()).thenReturn("4011976318088");
        when(discMock.getFsk()).thenReturn(16);

        discMock2 = mock(Disc.class);
        when(discMock2.getDirector()).thenReturn("Roger Michell");
        when(discMock2.getTitle()).thenReturn("Notting Hill");
        when(discMock2.getBarcode()).thenReturn("0044005976021");
        when(discMock2.getFsk()).thenReturn(6);

        doAnswer(
                Void -> {
                    discs.put(bookMock.getIsbn(), discMock);
                    return null;
                }
        ).when(mediaDAOMock).addDisc(discMock);

        doAnswer(
                Void -> {
                    discs.remove(discMock.getBarcode());
                    discs.put(discMock.getBarcode(), discMock);
                    return null;
                }
        ).when(mediaDAOMock).updateDisc(discMock);
    }


    @Test
    public void createBook() throws Exception {
        Response result = mediaRessource.addBook(bookMock);
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
    }

    @Test
    public void createExistingBook() throws Exception {
        when(mediaDAOMock.getBook(bookMock.getIsbn())).thenReturn(bookMock);
        Response result = mediaRessource.addBook(bookMock);
        assertEquals(MediaServiceResult.DUPLICATE_ISBN.getStatusCode(), result.getStatus());
    }

    @Test
    public void getBook() throws Exception {
        Response result = mediaRessource.getBook(bookMock.getIsbn());
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
    }

    @Test
    public void getBooks() throws Exception {
        books.put(bookMock.getIsbn(), bookMock);
        books.put(bookMock2.getIsbn(), bookMock2);

        doAnswer(
                ArrayList -> new ArrayList<>(books.values())
        ).when(mediaDAOMock).getBooks();

        Response result = mediaRessource.getBooks();
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
    }

    @Test
    public void updateBookTitleAndAuthor() throws Exception {
        when(bookMock.getAuthor()).thenReturn("AdlaaaaOlsen");
        when(bookMock.getTitle()).thenReturn("Takeover");
        when(mediaDAOMock.getBook(bookMock.getIsbn())).thenReturn(bookMock);
        Response result = mediaRessource.updateBook(bookMock.getIsbn(), bookMock);
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
        assertEquals("AdlaaaaOlsen", bookMock.getAuthor());
        assertEquals("Takeover", bookMock.getTitle());
    }


    @Test
    public void updateBookISBN() throws Exception {
        when(mediaDAOMock.getBook(bookMock.getIsbn())).thenReturn(bookMock);
        Response result = mediaRessource.updateBook("12345678", bookMock);
        assertEquals(MediaServiceResult.MODIFYING_ISBN_NOT_ALLOWED.getStatusCode(), result.getStatus());
        assertEquals("Adler Olsen", bookMock.getAuthor());
        assertEquals("TAKEOVER", bookMock.getTitle());
        assertEquals("9783423216487", bookMock.getIsbn());
    }

    @Test
    public void updateBookNotFound() throws Exception {
        when(mediaDAOMock.getBook("1234")).thenReturn(null);
        Response result = mediaRessource.updateBook("1234", bookMock);
        assertEquals(MediaServiceResult.ISBN_NOT_FOUND.getStatusCode(), result.getStatus());

    }


    @Test
    public void createDisc() throws Exception {
        Response result = mediaRessource.addDisc(discMock);
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
    }

    @Test
    public void createExistingDisc() throws Exception {
        when(mediaDAOMock.getDisc(discMock.getBarcode())).thenReturn(discMock);
        Response result = mediaRessource.addDisc(discMock);
        assertEquals(MediaServiceResult.DUPLICATE_BARCODE.getStatusCode(), result.getStatus());
    }

    @Test
    public void getDisc() throws Exception {
        Response result = mediaRessource.getDisc(discMock.getBarcode());
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
    }

    @Test
    public void getDiscs() throws Exception {
        discs.put(discMock.getBarcode(), discMock);
        discs.put(discMock2.getBarcode(), discMock2);

        doAnswer(
                ArrayList -> new ArrayList<>(discs.values())
        ).when(mediaDAOMock).getDiscs();

        Response result = mediaRessource.getDiscs();
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
    }

    @Test
    public void updateDiscDirectorAndTitle() throws Exception {
        when(discMock.getDirector()).thenReturn("AdlaaaaOlsen");
        when(discMock.getTitle()).thenReturn("Takeover");
        when(mediaDAOMock.getDisc(discMock.getBarcode())).thenReturn(discMock);
        Response result = mediaRessource.updateDisc(discMock.getBarcode(), discMock);
        assertEquals(MediaServiceResult.OK.getStatusCode(), result.getStatus());
        assertEquals("AdlaaaaOlsen", discMock.getDirector());
        assertEquals("Takeover", discMock.getTitle());
    }


    @Test
    public void updateDiscBarcode() throws Exception {
        when(mediaDAOMock.getDisc(discMock.getBarcode())).thenReturn(discMock);
        Response result = mediaRessource.updateDisc("12345678", discMock);
        assertEquals(MediaServiceResult.MODIFYING_BARCODE_NOT_ALLOWED.getStatusCode(), result.getStatus());
        assertEquals("Paul McGuigan", discMock.getDirector());
        assertEquals("Lucky Number Slevin", discMock.getTitle());
        assertEquals("4011976318088", discMock.getBarcode());
    }

    @Test
    public void updateDiscNotFound() throws Exception {
        when(mediaDAOMock.getDisc("1234")).thenReturn(null);
        Response result = mediaRessource.updateDisc("1234", discMock);
        assertEquals(MediaServiceResult.BARCODE_NOT_FOUND.getStatusCode(), result.getStatus());

    }
}
