package com.mycompany.restservice.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.User;
import models.UserService;
import models.UserServiceDB;

/**
 *
 * @author 
 */
@Path("user")
public class UserResource {
    
    private final UserService uS = UserServiceDB.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String path = "/home/alumne/images/";
    
    @GET
    public Response ping(){
        return Response
                .ok("ping")
                .build();
    }
 
    /**
     * POST method to login in the application
     * @param username
     * @param password
     * @return
     * @throws java.lang.Exception
     */
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(@FormParam("username") String username,
        @FormParam("password") String password) 
        throws Exception {
            try {
            User user = uS.userLogin(username, password);
            if (user != null) {
                final String json = gson.toJson(user);
                return Response
                    .ok(json, MediaType.APPLICATION_JSON)
                    .build();
            }
            else {
                return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            }
        } 
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
    }
    
    /**
     * POST method to register a new user
     * @param username
     * @param password
     * @return
     * @throws java.lang.Exception
     */
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@FormParam("username") String username,
        @FormParam("password") String password) 
        throws Exception{
        try {
            boolean registered = uS.userRegister(username, password);
            if (registered) {
                return Response
                    .ok("ok", MediaType.APPLICATION_JSON)
                    .build();
            }
            else {
                return Response
                    .status(Response.Status.CONFLICT)
                    .entity("error")
                    .build();
            } 
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
    }
    
}
