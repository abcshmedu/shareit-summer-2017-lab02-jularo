package edu.hm.jularo.shareit.service;

import edu.hm.jularo.shareit.models.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class MediaResource {

    @POST
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        return Response.status(Response.Status.OK).entity("json").build();
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
        return Response.status(Response.Status.OK).entity("json").build();
    }

    @GET
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn")String isbn) {
        return Response.status(Response.Status.OK).entity("json").build();
    }


}