package servlets;

import javax.servlet.http.Part;
import javax.servlet.http.HttpSession;

import models.User;
import models.Image;
import models.ImageService;
import models.ImageServiceREST;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
                Image image = new Image(title, description, keywords, author,
                uploader, captureDate, "", fileName);
                boolean uploaded = iS.imageUpload(image, part);
                
                if (uploaded) {
                    response.sendRedirect("imageRegister.jsp?success=1");
                }
                else {
                    response.sendRedirect("Error?code=21");
                }
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
