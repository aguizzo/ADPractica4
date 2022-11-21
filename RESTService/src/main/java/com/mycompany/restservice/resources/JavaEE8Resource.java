package com.mycompany.restservice.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
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

import models.Image;
import models.ImageService;
import models.ImageServiceDB;
import models.User;
import models.UserService;
import models.UserServiceDB;

/**
 *
 * @author 
 */
@Path("api")
public class JavaEE8Resource {
    
    private final UserService uS = UserServiceDB.getInstance();
    private final ImageService iS = ImageServiceDB.getInstance();
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
        throws Exception{
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
    
    /**
     * POST method to register a new image
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator
     * @param capt_date
     * @param fileName
     * @return
     * @throws java.lang.Exception
     */
    @Path("register")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerImage (@FormParam("title") String title,
        @FormParam("description") String description,
        @FormParam("keywords") String keywords,
        @FormParam("author") String author,
        @FormParam("creator") String creator,
        @FormParam("capt_date") String capt_date, 
        @FormParam("fileName") String fileName) throws Exception{
        Image im = new Image(title, description, keywords, author, creator, 
            capt_date, "", fileName);
        boolean registered = iS.imageRegister(im);
        if (registered) {
            return Response
                .ok("ok", MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            return Response
                .status(Response.Status.CONFLICT)
                .build();
        }   
    }
    
    /**
     * POST method to modify an existing image
     * @param id
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator, used for checking image ownership
     * @param capt_date
     * @return
     * @throws java.lang.Exception
     */
    @Path("modify")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyImage (@FormParam("id") String id,
        @FormParam("title") String title,
        @FormParam("description") String description,
        @FormParam("keywords") String keywords,
        @FormParam("author") String author,
        @FormParam("creator") String creator,
        @FormParam("capt_date") String capt_date) throws Exception {
        Image im = new Image(title, description, keywords, author, creator, 
            capt_date, "", "");
        int ID = Integer.valueOf(id);
        im.setId(ID);
        boolean isOwner = iS.checkOwnership(ID, creator);
        if (isOwner) {
            boolean modified = iS.modifyImage(im);
            if (modified) {
                return Response
                    .ok("ok", MediaType.APPLICATION_JSON)
                    .build();
            }
            else {
                return Response
                    .status(Response.Status.CONFLICT)
                    .build();
            }
        }
        else {
            return Response
                .status(Response.Status.FORBIDDEN)
                .build();
        }
    }
    
    /**
     * POST method to delete an existing image
     * @param id
     * @param creator, used for checking image ownership
     * @return
     * @throws java.lang.Exception
     */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage (@FormParam("id") String id,
        @FormParam("creator") String creator)
        throws Exception {
        int ID = Integer.valueOf(id);
        boolean isOwner = iS.checkOwnership(ID, creator);
        if (isOwner) {
            boolean deleted = iS.deleteImage(ID);
            if (deleted) {
                return Response
                    .ok("ok", MediaType.APPLICATION_JSON)
                    .build();
            }
            else {
                return Response
                    .status(Response.Status.CONFLICT)
                    .build();
            }
        }
        else {
            return Response
                .status(Response.Status.FORBIDDEN)
                .build();
        }     
    }
    
    /**
     * GET method to list images
     * @return
     * @throws java.lang.Exception
     */
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listImages ()
        throws Exception {
        List<Image> list = iS.getImageList();
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build();  
    }
    
    /**
     * GET method to download images by id
     * @param id
     * @return 
     */
    @Path("getImage/{id}")
    @GET
    @Produces("image/*")
    public Response getImage(@PathParam("id") int id) { 
        try {
            Image im = iS.getImage(id);
            if (im == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            String filename = im.getFileName();
            File f = new File(path + filename);
            if (!f.exists())
                return Response.ok("No existe imagen").build();
            String mt = new MimetypesFileTypeMap().getContentType(f);     
            return Response.
                    ok(f, mt).header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + filename)
                    .build();
        }
        catch (Exception e) {
            return Response.ok("Error").build();
        }
    }
    

    /**
     * GET method to search images by id
     * @param id
     * @return
     * @throws java.lang.Exception
     */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID (@PathParam("id") int id)
            throws Exception {
        Image image = iS.getImage(id);
        if (image != null) {
            final String json = gson.toJson(image);
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

    /**
     * GET method to search images by title
     * @param title
     * @return
     * @throws java.lang.Exception
     */
    @Path("searchTitle/{title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByTitle (@PathParam("title") String title)
        throws Exception {
        List<Image> list = iS.searchByTitle(title);
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build(); 
    }

    /**
     * GET method to search images by storage date.Date format should be
 yyyy-mm-dd
     * @param date
     * @return
     * @throws java.lang.Exception
     */
    @Path("searchCreationDate/{date}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByStorageDate (@PathParam("date") String date)
        throws Exception {
        List<Image> list = iS.searchByStorageDate(date);
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build();
    }

    /**
     * GET method to search images by author
     * @param author
     * @return
     * @throws java.lang.Exception
    */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByAuthor (@PathParam("author") String author)
        throws Exception {
        List<Image> list = iS.searchByAuthor(author);
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build();
    }

    /**
     * GET method to search images by keyword
     * @param keywords
     * @return
     * @throws java.lang.Exception
     */
    @Path("searchKeywords/{keywords}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByKeywords (@PathParam("keywords") String keywords)
        throws Exception {
        List<Image> list = iS.searchByKeywords(keywords);
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build();         
    }

    /*
        Additional API functions
    */
    
    /**
     * POST method to register a new user
     * @param username
     * @param password
     * @return
     * @throws java.lang.Exception
     */
    @Path("userRegister")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(@FormParam("username") String username,
        @FormParam("password") String password) 
        throws Exception{
        boolean registered = uS.userRegister(username, password);
        if (registered) {
            return Response
                .ok("ok", MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            return Response
                .status(Response.Status.CONFLICT)
                .build();
        }       
    }
    
    /**
     * POST method to use a combined search for images
     * @param title
     * @param keywords
     * @param author
     * @param capt_date
     * @return
     * @throws java.lang.Exception
     */
    @Path("combinedSearch")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response combinedSearch (@QueryParam("title") String title,
        @QueryParam("keywords") String keywords,
        @QueryParam("author") String author,
        @QueryParam("capt_date") String capt_date)
        throws Exception{
        List<Image> list = iS.
                searchImages(title, keywords, author, capt_date);
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build();        
    }
}
