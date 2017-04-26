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
    @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {

        MediaServiceResult result = mediaService.addBook(book);

        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }

    @PUT
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        return Response.status(Response.Status.OK).entity("json").build();
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {

        return Response.status(Response.Status.OK).entity(jsonBuilder(mediaService.getBooks())).build();
    }



    @GET
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn")String isbn) {
        return Response.status(Response.Status.OK).entity("json").build();
    }



    @POST
    @Path("/discs")
    @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc) {

        MediaServiceResult result = mediaService.addDisc(disc);

        return Response.status(result.getCode()).entity(result.getDetail()).build();
    }



    private String jsonBuilder(List<Book> books) {
        StringBuilder builder = new StringBuilder();
        for(Book book:books){
            builder.append(book.toString());
        }
        return builder.toString();
    }

}