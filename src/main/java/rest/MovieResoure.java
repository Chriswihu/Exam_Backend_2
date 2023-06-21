package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MovieDTO;
import facades.MovieFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class MovieResoure {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final MovieFacade FACADE = MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello Movie\"}";
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addMovie(String movie) {
        MovieDTO m = GSON.fromJson(movie, MovieDTO.class);
        MovieDTO movieAdded = FACADE.create(m);
        return Response.ok(GSON.toJson(movieAdded)).build();
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllMovies() {
        return GSON.toJson(FACADE.getAllMovies());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getMovieById(@PathParam("id") Long id) {
        return GSON.toJson(FACADE.getMovieById(id));
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteMovieById(@PathParam("id") Long id) {
        return GSON.toJson(FACADE.deleteMovie(id));
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editMovieById(String movie) {
        MovieDTO m = GSON.fromJson(movie, MovieDTO.class);
        MovieDTO movieEdited = FACADE.editMovie(m);
        return Response.ok(GSON.toJson(movieEdited)).build();
    }

}
