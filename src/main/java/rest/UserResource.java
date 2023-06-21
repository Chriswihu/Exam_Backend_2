package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import dtos.UserDTO;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
@Path("user")
public class UserResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String info() {
        return "{\"msg\":\"Hello User\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {return GSON.toJson(FACADE.getAllUsers());}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{userName}")
    public String getUserMovies(@PathParam("userName") String userName) {
        return GSON.toJson(FACADE.getUserMovies(userName));

    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("create")
    public Response createUser(String user) {
        UserDTO u = GSON.fromJson(user, UserDTO.class);
        User newUser = FACADE.addUser(u.getUserName(), u.getUserPass(), u.getPhone(), u.getEmail(), u.getStatus());
        return Response.ok(newUser).build();
    }

//    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("delete/{username}")
//    public Response deleteUser(@PathParam("username") String username) {
//        UserDto
//        return Response.ok(user).build();
//    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("edit/{username}")
    public Response editUser(@PathParam("username") String username, String jsonUser) {
        UserDTO updatedUser = GSON.fromJson(jsonUser, UserDTO.class);
        UserDTO editedUser = FACADE.editUser(username, updatedUser);
        return Response.ok(editedUser).build();

    }



}