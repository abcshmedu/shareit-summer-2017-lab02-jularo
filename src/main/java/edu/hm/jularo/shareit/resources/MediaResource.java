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

@Path("/media")
public class MediaResource {

    private final MediaService mediaService = new MediaServiceImpl();

    @POST
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        MediaServiceResult result = mediaService.addBook(book);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

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

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        return Response.status(MediaServiceResult.FOUND_LIST.getCode()).entity(jsonBuilder(mediaService.getBooks())).build();
    }

    @PUT
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        MediaServiceResult result = mediaService.updateBook(book);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

    @POST
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {
        MediaServiceResult result = mediaService.addDisc(disc);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

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

    @GET
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs() {
        return Response.status(MediaServiceResult.FOUND_LIST.getCode()).entity(jsonBuilder(mediaService.getDiscs())).build();
    }

    @PUT
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDisc(Disc disc) {
        MediaServiceResult result = mediaService.updateDisc(disc);
        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

    private static <T> String jsonBuilder(List<T> media) {
        StringBuilder builder = new StringBuilder();
        for (T medium : media) {
            builder.append(medium.toString());
        }
        return builder.toString();
    }
}