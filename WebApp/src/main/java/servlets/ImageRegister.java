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

@WebServlet(name = "ImageRegister", urlPatterns = {"/ImageRegister"})
@MultipartConfig
public class ImageRegister extends HttpServlet {
    private final ImageService iS = ImageServiceREST.getInstance();
    
    protected void imageRegisterRequest(HttpServletRequest request, HttpServletResponse response)
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
            String fileName = "placeholder.jpg";

            Image image = new Image(title, description, keywords, author,
                uploader, captureDate, "", fileName);
            boolean registered = iS.imageRegister(image);
            if (registered) {
                response.sendRedirect("imageRegister.jsp?success=1");
            }
            else {
                response.sendRedirect("Error?code=21");
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
        imageRegisterRequest(request, response);
    }

}
