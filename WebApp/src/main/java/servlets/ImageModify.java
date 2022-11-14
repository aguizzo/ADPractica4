package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Image;
import models.ImageService;
import models.ImageServiceREST;
import models.User;

@WebServlet(name = "ImageModify", urlPatterns = {"/ImageModify"})
public class ImageModify extends HttpServlet {
    
    private final ImageService iS = ImageServiceREST.getInstance();

    protected void imageModifyRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {             
            String id = request.getParameter("ID");
            int ID = Integer.parseInt(id);
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String keywords = request.getParameter("keywords");
            String author = request.getParameter("author");
            String captureDate = request.getParameter("captureDate");
            String uploader = request.getParameter("uploader");
            Image image = iS.getImage(ID); 
            if (image != null) {           
                HttpSession session = request.getSession();
                User user = (User)session.getAttribute("user");
                if (user.getUsername().equals(uploader)) {  
                    Image im = new Image(title, description, keywords, author, 
                            uploader, captureDate, "", "");
                    im.setId(ID);
                    boolean modified = iS.modifyImage(im);
                    if (modified) {
                        response.sendRedirect("ImageShow?ID=" + id + "&success=1");
                    }
                    else {
                        response.sendRedirect("Error?code=26");
                    }
                }
                else {
                    response.sendRedirect("Error?code=403");
                }
            }
            else {
                response.sendRedirect("Error?code=20");
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
        imageModifyRequest(request, response);
    }

}
