package edu.hm.shareit.resource;

import edu.hm.shareit.businessLayer.MediaService;
import edu.hm.shareit.businessLayer.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
@Path("media")
public class MediaRessource {

    private MediaService mediaService;

    @Inject
    public MediaRessource(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    /**
     * URI-Template     Verb    Wirkung
     * /media/books     POST    Neues Medium Buch anlegen
     * <p>
     * .../media/books?token=asdkfjpaweoi
     * <p>
     * Moeglicher Fehler: Ungueltige ISBN
     * Moeglicher Fehler: ISBN bereits vorhanden
     * Moeglicher Fehler: Autor oder Titel fehlt
     *
     * @param book book
     * @return Response.status
     */
    @POST
    @Path("books")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        MediaServiceResult result = mediaService.addBook(book);
        return Response.status(result.getStatusCode()).entity(result).build();
    }


    /**
     * URI-Template     Verb    Wirkung
     * /media/books     GET     Alle Buecher auflisten
     *
     * @return Response.status
     */
    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        List<Book> books = mediaService.getBooks();
        return Response.status(Response.Status.OK).entity(books).build();
    }

    @GET
    @Path("books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") String isbn) {
        Medium book = mediaService.getBook(isbn);
        return Response.status(Response.Status.OK).entity(book).build();
    }


    @POST
    @Path("discs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDisc(Disc disc) {
        MediaServiceResult result = mediaService.addDisc(disc);
        return Response.status(result.getStatusCode()).entity(result).build();
    }


    @GET
    @Path("discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {
        List<Disc> discs = mediaService.getDiscs();
        return Response.status(Response.Status.OK).entity(discs).build();
    }

    @GET
    @Path("discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode) {
        Medium disc = mediaService.getDisc(barcode);
        return Response.status(Response.Status.OK).entity(disc).build();
    }

    /**
     * URI-Template         Verb    Wirkung.
     * /media/books/{isbn}  PUT     Daten zu vorhandenem Buch modifizieren
     * <p>
     * JSONDaten enthalten nur die zu modifizierenden Attribute
     * Moeoglicher Fehler: ISBN nicht gefunden
     * Moeglicher Fehler: ISBN soll modifiziert werden (also die JSON-Daten enthalten eine andere ISBN als die URI)
     * Moeglicher Fehler: Autor und Titel fehlen
     *
     * @param isbn isbn
     * @param book book
     * @return Response.status
     */
    @PUT
    @Path("books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn") String isbn, Book book) {
        MediaServiceResult result = mediaService.updateBook(book, isbn);
        return Response.status(result.getStatusCode()).entity(result).build();
    }

    @PUT
    @Path("discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc) {
        MediaServiceResult result = mediaService.updateDisc(disc, barcode);
        return Response.status(result.getStatusCode()).entity(result).build();
    }

}
