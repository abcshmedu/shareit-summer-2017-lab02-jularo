package edu.hm.shareit.resource;

import edu.hm.shareit.businessLayer.MediaService;
import edu.hm.shareit.businessLayer.MediaServiceImpl;
import edu.hm.shareit.businessLayer.MediaServiceResult;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;
import edu.hm.shareit.models.Medium;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Carolin Direnberger
 * @author Juliane Seidl
 */
@Path("media")
public class MediaResource {

    private static final MediaService mediaService = new MediaServiceImpl();

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
     * @param book
     * @return
     */
    @POST
    @Path("books")
    @Consumes(MediaType.APPLICATION_JSON) //Jersey will use Jackson to handle the JSON conversion automatically
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book, @QueryParam("token") String token) {

        if (callOAuthServer(token).isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        MediaServiceResult result = mediaService.addBook(book);

        return Response.status(result.getStatusCode()).entity(result).build();
    }


    /**
     * URI-Template     Verb    Wirkung
     * /media/books     GET     Alle Buecher auflisten
     *
     * @return
     */
    @GET
    @Path("books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@QueryParam("token") String token) {

        String test = callOAuthServer(token);
        System.out.println("Test!" + test);

        if (test.isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        Medium[] allBooks = mediaService.getBooks();

        return Response.status(Response.Status.OK).entity(allBooks).build();
    }

    @GET
    @Path("books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") String isbn, @QueryParam("token") String token) {

        if (callOAuthServer(token).isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        Medium book = mediaService.getBook(isbn);

        return Response.status(200).entity(book).build();
    }


    @POST
    @Path("discs")
    @Consumes(MediaType.APPLICATION_JSON) //Jersey will use Jackson to handle the JSON conversion automatically
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDisc(Disc disc, @QueryParam("token") String token) {

        if (callOAuthServer(token).isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        MediaServiceResult result = mediaService.addDisc(disc);

        return Response.status(result.getStatusCode()).entity(result).build();
    }


    @GET
    @Path("discs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs(@QueryParam("token") String token) {

        if (callOAuthServer(token).isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        Medium[] allBooks = mediaService.getDiscs();

        return Response.status(200).entity(allBooks).build();
    }

    @GET
    @Path("discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode, @QueryParam("token") String token) {

        if (callOAuthServer(token).isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        Medium disc = mediaService.getDisc(barcode);

        return Response.status(200).entity(disc).build();
    }

    /**
     * URI-Template         Verb    Wirkung
     * /media/books/{isbn}  PUT     Daten zu vorhandenem Buch modifizieren
     * <p>
     * JSONDaten enthalten nur die zu modifizierenden Attribute
     * Moeoglicher Fehler: ISBN nicht gefunden
     * Moeglicher Fehler: ISBN soll modifiziert werden (also die JSON-Daten enthalten eine andere ISBN als die URI)
     * Moeglicher Fehler: Autor und Titel fehlen
     *
     * @param isbn
     * @return
     */
    @PUT
    @Path("books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn") String isbn, Book book, @QueryParam("token") String token) {

        if (callOAuthServer(token).isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        MediaServiceResult result = mediaService.updateBook(book, isbn);

        return Response.status(result.getStatusCode()).entity(result).build();
    }


    @PUT
    @Path("discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc, @QueryParam("token") String token) {

        if (callOAuthServer(token).isEmpty()) {
            return Response.status(MediaServiceResult.TOKEN_NOT_VALID.getStatusCode()).entity(MediaServiceResult.TOKEN_NOT_VALID).build();
        }

        MediaServiceResult result = mediaService.updateDisc(disc, barcode);

        return Response.status(result.getStatusCode()).entity(result).build();
    }


    /**
     * Call OAuthServer for validating a token.
     *
     * @param token token as string
     * @return JSON with information, if user is admin or not. Empty string if token is not valid.
     */
    private String callOAuthServer(String token) {
        //call oAuth-Server
        // get "admin": "true"
        // or empty String if token is not valid

        String urlLocal = "http://localhost:8333/shareit/users/login/";
        String urlHeroku = "https://jularo.herokuapp.com/shareit/users/login/";


        String result = "";

        //System.out.println("token: " + token);

        try {
            URL url = new URL(urlHeroku + token);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("GET"); // PUT is another valid option
            http.setDoOutput(true);

            //System.out.println(http.getResponseMessage());    //OK
            //System.out.println(http.getResponseCode());       //200

            if (200 == http.getResponseCode()) {
                BufferedReader br = new BufferedReader(new InputStreamReader((http.getInputStream())));
                String currentLine = "";
                while ((currentLine = br.readLine()) != null) {
                    result += currentLine;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void resetDataBase() {
        mediaService.clearMap();
    }

}
