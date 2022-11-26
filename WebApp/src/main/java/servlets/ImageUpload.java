package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;

import models.User;
import models.Image;
import models.ImageService;
import models.ImageServiceREST;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ImageUpload", urlPatterns = {"/ImageUpload"})
@MultipartConfig
public class ImageUpload extends HttpServlet {

private final ImageService iS = ImageServiceREST.getInstance();
    
    protected void imageUploadRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        try {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String keywords = request.getParameter("keywords");
            String author = request.getParameter("author");
            String uploader = user.getUsername();
            String captureDate = request.getParameter("captureDate");
            Part part = request.getPart("image");
            String contentType = part.getContentType();
            
            if (contentType.equals("image/jpeg")) { 
                String fileName = part.getSubmittedFileName();
                 
                final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();

                StreamDataBodyPart filePart = new StreamDataBodyPart("file", part.getInputStream());
                FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
                final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
                        .field("title", title, MediaType.TEXT_PLAIN_TYPE)
                        .field("description", description, MediaType.TEXT_PLAIN_TYPE)
                        .field("keywords", keywords, MediaType.TEXT_PLAIN_TYPE)
                        .field("author", author, MediaType.TEXT_PLAIN_TYPE)
                        .field("uploader", uploader, MediaType.TEXT_PLAIN_TYPE)
                        .field("capture", captureDate, MediaType.TEXT_PLAIN_TYPE)
                        .field("filename", fileName, MediaType.TEXT_PLAIN_TYPE)
                        .bodyPart(filePart);

                final WebTarget target = client.target("http://localhost:8080/RESTService/resources/api/upload");
                final Response resp = target.request().post(Entity.entity(multipart, multipart.getMediaType()));

                //int result = Integer.parseInt(resp.readEntity(String.class));

                formDataMultiPart.close();
                multipart.close();
                
            }  
            else {
                response.sendRedirect("Error?code=27");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Error?code=0");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        imageUploadRequest(request, response);
    }
}