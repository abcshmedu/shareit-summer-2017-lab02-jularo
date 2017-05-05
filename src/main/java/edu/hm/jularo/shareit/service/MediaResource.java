package edu.hm.jularo.shareit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.jularo.shareit.models.Book;
import edu.hm.jularo.shareit.models.Disc;

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
    private ObjectMapper jsonMapper = new ObjectMapper();

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
        try {
            return Response.status(result.getCode()).entity(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(book)).build();
        } catch (JsonProcessingException e) {
            return Response.status(result.getCode()).entity(result.getDetail()).build();
        }
    }

    /**
     * Sucht ein Buch mit Hilfe der ISBN.
     *
     * @param isbn Die ISBN des Buches
     * @return gesuchtes Buch, falls vorhanden
     */
    @GET
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBookByISBN(@PathParam("isbn") String isbn) {
        return mediaService.getBookByISBN(isbn);
    }

    /**
     * Gibt die Bücherliste aus.
     *
     * @return Bücherliste
     */
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return mediaService.getBooks();
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
        try {
            return Response.status(result.getCode()).entity(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(book)).build();
        } catch (JsonProcessingException e) {
            return Response.status(result.getCode()).entity(result.getDetail()).build();
        }
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
        try {
            return Response.status(result.getCode()).entity(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(disc)).build();
        } catch (JsonProcessingException e) {
            return Response.status(result.getCode()).entity(result.getDetail()).build();
        }
    }

    /**
     * Sucht eine Disk mit Hilfe des Barcodes.
     *
     * @param barcode Der Barcode der Disk
     * @return gesuchte Disk, falls vorhanden
     */
    @GET
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Disc getDiscByBarcode(@PathParam("barcode") String barcode) {
        return mediaService.getDiscByBarcode(barcode);
    }

    /**
     * Gibt die Diskliste aus.
     *
     * @return Diskliste
     */
    @GET
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Disc> getDiscs() {
        return mediaService.getDiscs();
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
        try {
            return Response.status(result.getCode()).entity(jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(disc)).build();
        } catch (JsonProcessingException e) {
            return Response.status(result.getCode()).entity(result.getDetail()).build();
        }
    }

    /**
     * Methode zum Löschen der Meidenlisten.
     */
    public void clearLists() {
        mediaService.clearLists();
    }
}