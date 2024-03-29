package com.mycompany.restservice.resources;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.Image;
import models.ImageDTO;
import models.ImageService;
import models.ImageServiceDB;


/**
 *
 * @author 
 */
@Path("image")
public class ImageResource {
    
    private final ImageServiceDB iS = ImageServiceDB.getInstance();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final String path = "/home/alumne/images/";
    
    @GET
    public Response ping(){
        return Response
            .ok("ping")
            .build();
    }
    
    /**
     * POST method to register the metada of a image
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param uploader
     * @param captureDate
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
        @FormParam("uploader") String uploader,
        @FormParam("captureDate") String captureDate, 
        @FormParam("fileName") String fileName) 
            throws Exception {
        try {
            Image im = new Image(title, description, keywords, author, uploader, 
                captureDate, "", fileName);
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
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
    }
    
    /** 
    * POST method to register and upload a new image 
    * @param title 
    * @param description 
    * @param keywords      
    * @param author 
    * @param uploader 
    * @param captureDate     
    * @param filename     
    * @param fileInputStream     
    * @param fileMetaData     
    * @return 
    * @throws java.lang.Exception 
   */ 
    @Path("upload") 
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Produces(MediaType.APPLICATION_JSON) 
    public Response uploadImage (@FormDataParam("title") String title, 
        @FormDataParam("description") String description, 
        @FormDataParam("keywords") String keywords, 
        @FormDataParam("author") String author, 
        @FormDataParam("uploader") String uploader, 
        @FormDataParam("capture") String captureDate,
        @FormDataParam("filename") String filename,
        @FormDataParam("file") InputStream fileInputStream,
        @FormDataParam("file") FormDataContentDisposition fileMetaData)
        throws Exception {
        Image im = new Image(title, description, keywords, author, uploader, 
            captureDate, "", filename);
        
        boolean registered = iS.imageRegister(im);
        if (!registered) {
            return Response
                .status(Response.Status.CONFLICT)
                .build();
        }
        else{
            String uploadedFileLocation = path + filename;
            try {
                OutputStream out;
                int read = 0;
                byte[] bytes = new byte[1024];

                out = new FileOutputStream(new File(uploadedFileLocation));
                while ((read = fileInputStream.read(bytes)) != -1) {
                  out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            } 
            catch (IOException e) {
              e.printStackTrace();
              return Response
                .status(Response.Status.CONFLICT)
                .build();
            }
            return Response
                     .ok(200, MediaType.APPLICATION_JSON)
                     .build(); 
        }
    }
    
    /**
     * PUT method to modify an existing image
     * @param id
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param uploader, used for checking image ownership
     * @param captureDate
     * @return
     * @throws java.lang.Exception
    */
    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyImage (@PathParam("id") int id,
        @FormParam("title") String title,
        @FormParam("description") String description,
        @FormParam("keywords") String keywords,
        @FormParam("author") String author,
        @FormParam("uploader") String uploader,
        @FormParam("captureDate") String captureDate)
            throws Exception {
        try {
            Image im = new Image(title, description, keywords, author, uploader, 
                captureDate, "", "");
            im.setId(id);
            boolean isOwner = iS.checkOwnership(id, uploader);
            if (isOwner) {
                ImageDTO dto = iS.modifyImage2(im);
                boolean modified = dto.isOperationSucess();
                if (modified) {
                    im = dto.getImage();
                    final String json = gson.toJson(im);
                    return Response
                        .ok(json, MediaType.APPLICATION_JSON)
                        .build();
                }
                else {
                    return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity("La imagen no existe")
                        .build();
                }
            }
            else {
                return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("No eres el propietario de la imagen")
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
     * POST method to delete an existing image
     * @param id
     * @param uploader, used for checking image ownership
     * @return
     * @throws java.lang.Exception
     */
    @Path("delete")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteImage (@FormParam("id") String id,
        @FormParam("uploader") String uploader)
        throws Exception {
        try {
            int ID = Integer.valueOf(id);
            boolean isOwner = iS.checkOwnership(ID, uploader);
            if (isOwner) {
                boolean deleted = iS.deleteImage(ID);
                if (deleted) {
                    return Response
                        .ok("ok", MediaType.APPLICATION_JSON)
                        .build();
                }
                else {
                    return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
                }
            }
            else {
                return Response
                    .status(Response.Status.FORBIDDEN)
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
     * GET method to search images by id
     * @param id
     * @return
     * @throws java.lang.Exception
    */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID (@PathParam("id") int id)
            throws Exception {
        try {
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
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
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
        try {
            List<Image> list = iS.getImageList();
            final String json = gson.toJson(list);
            return Response
                .ok(json, MediaType.APPLICATION_JSON)
                .build();
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
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
                return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            String filename = im.getFileName();
            File f = new File(path + filename);
            if (!f.exists())
                return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
            String mt = new MimetypesFileTypeMap().getContentType(f);     
            return Response.
                    ok(f, mt).header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + filename)
                    .build();
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
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
        try {
            List<Image> list = iS.searchByTitle(title);
            final String json = gson.toJson(list);
            return Response
                .ok(json, MediaType.APPLICATION_JSON)
                .build();
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
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
        try {
            List<Image> list = iS.searchByStorageDate(date);
            final String json = gson.toJson(list);
            return Response
                .ok(json, MediaType.APPLICATION_JSON)
                .build();
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
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
        try {
            List<Image> list = iS.searchByAuthor(author);
            final String json = gson.toJson(list);
            return Response
                .ok(json, MediaType.APPLICATION_JSON)
                .build();
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
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
        try {
        List<Image> list = iS.searchByKeywords(keywords);
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build();     
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
    }

   /**
     * POST method to use a combined search for images
     * @param title
     * @param keywords
     * @param author
     * @param captureDate
     * @return
     * @throws java.lang.Exception
    */
    @Path("combinedSearch")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response combinedSearch (@QueryParam("title") String title,
        @QueryParam("keywords") String keywords,
        @QueryParam("author") String author,
        @QueryParam("captureDate") String captureDate)
        throws Exception{
        try {
        List<Image> list = iS.
                searchImages(title, keywords, author, captureDate);
        final String json = gson.toJson(list);
        return Response
            .ok(json, MediaType.APPLICATION_JSON)
            .build(); 
        }
        catch (Exception e) {
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
        }
    }
}
