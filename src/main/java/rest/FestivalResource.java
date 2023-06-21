package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.FestivalDTO;
import facades.FestivalFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

//Todo Remove or change relevant parts before ACTUAL use
@Path("festival")
public class FestivalResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final FestivalFacade FACADE = FestivalFacade.getFestivalFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello Festival\"}";
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addFestival(String festival) {
        FestivalDTO f = GSON.fromJson(festival, FestivalDTO.class);
        FestivalDTO festivalAdded = FACADE.create(f);
        return Response.ok(GSON.toJson(festivalAdded)).build();
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllFestivals() {
        return GSON.toJson(FACADE.getAllFestivals());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getFestivalById(@PathParam("id") Long id) {
        return GSON.toJson(FACADE.getById(id));
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteFestivalById(@PathParam("id") Long id) {
        return GSON.toJson(FACADE.deleteFestival(id));

    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editFestival(@PathParam("id") Long id, String festival) {
        FestivalDTO f = GSON.fromJson(festival, FestivalDTO.class);
        FestivalDTO festivalEdited = FACADE.editFestival(f);
        return Response.ok(GSON.toJson(festivalEdited)).build();
    }
}


