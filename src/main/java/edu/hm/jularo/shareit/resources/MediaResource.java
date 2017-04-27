package edu.hm.jularo.shareit.resources;

import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;
import edu.hm.jularo.shareit.service.MediaService;
import edu.hm.jularo.shareit.service.MediaServiceImpl;
import edu.hm.jularo.shareit.service.MediaServiceResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Handler für die Anfragen.
 *
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
@Path("/media")
public class MediaResource {

    private final MediaService mediaService = new MediaServiceImpl();

    /**
     * Hilfsmethode zur Ausgabe der Medienlisten.
     *
     * @param media Die Medienliste
     * @param <T>   Disc oder Book
     * @return Die Medienliste als String
     */
    private static <T> String jsonBuilder(List<T> media) {
        StringBuilder builder = new StringBuilder();
        for (T medium : media) {
            builder.append(medium.toString());
        }
        return builder.toString();
    }

    /**
     * Fügt ein Buch zur Bücherliste hinzu.
     *
     * @param book Das Buch, welches hinzugefügt werden soll.
     * @return Responsestatus
     */
    @POST
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        MediaServiceResult result = mediaService.addBook(book);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

    /**
     * Sucht ein Buch mit Hilfe der ISBN.
     *
     * @param isbn Die ISBN des Buches
     * @return Responsestatus
     */
    @GET
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") String isbn) {
        Book book = mediaService.getBookByISBN(isbn);
        if (book != null) {
            return Response.status(MediaServiceResult.FOUND.getCode()).entity(book.toString()).build();
        }
        return Response.status(MediaServiceResult.MEDIUM_NOT_IN_LIST.getCode()).entity(MediaServiceResult.MEDIUM_NOT_IN_LIST.getDetail()).build();
    }

    /**
     * Gibt die Bücherliste aus.
     *
     * @return Responsestatus
     */
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        if (!mediaService.getBooks().isEmpty()) {
            return Response.status(MediaServiceResult.FOUND_LIST.getCode()).entity(jsonBuilder(mediaService.getBooks())).build();
        }
        return Response.status(MediaServiceResult.EMPTY_LIST.getCode()).entity(MediaServiceResult.EMPTY_LIST.getDetail()).build();
    }

    /**
     * Aktualisiert ein Buch.
     *
     * @param book Das zu ändernde Buch
     * @return Responsestatus
     */
    @PUT
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        MediaServiceResult result = mediaService.updateBook(book);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

    /**
     * Fügt eine Disc zur Discliste hinzu.
     *
     * @param disc Die Disc, welche hinzugefügt werden soll.
     * @return Responsestatus
     */
    @POST
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {
        MediaServiceResult result = mediaService.addDisc(disc);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

    /**
     * Sucht ein Buch mit Hilfe des Barcodes.
     *
     * @param barcode Der Barcode des Buches
     * @return Responsestatus
     */
    @GET
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode) {
        Disc disc = mediaService.getDiscByBarcode(barcode);
        if (disc != null) {
            return Response.status(MediaServiceResult.FOUND.getCode()).entity(disc.toString()).build();
        }
        return Response.status(MediaServiceResult.MEDIUM_NOT_IN_LIST.getCode()).entity(MediaServiceResult.MEDIUM_NOT_IN_LIST.getDetail()).build();
    }

    /**
     * Gibt die Discliste aus.
     *
     * @return Responsestatus
     */
    @GET
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {
        if (!mediaService.getDiscs().isEmpty()) {
            return Response.status(MediaServiceResult.FOUND_LIST.getCode()).entity(jsonBuilder(mediaService.getDiscs())).build();
        }
        return Response.status(MediaServiceResult.EMPTY_LIST.getCode()).entity(MediaServiceResult.EMPTY_LIST.getDetail()).build();
    }

    /**
     * Aktualisiert eine Disc.
     *
     * @param disc Die zu ändernde Disc
     * @return Responsestatus
     */
    @PUT
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDisc(Disc disc) {
        MediaServiceResult result = mediaService.updateDisc(disc);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }
}