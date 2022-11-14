package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Image;
import models.ImageService;
import models.ImageServiceREST;


@WebServlet(name = "ImageShow", urlPatterns = {"/ImageShow"})
public class ImageShow extends HttpServlet {
    
    private final ImageService iS = ImageServiceREST.getInstance();
    
    protected void getImageRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            RequestDispatcher dispatcher = null;
            String idParam = request.getParameter("ID");
            int id  = Integer.parseInt(idParam); 
            Image im = iS.getImage(id);
            if (im != null) {
                 request.setAttribute("image", im);   
                 dispatcher = request.
                     getRequestDispatcher("imageShow.jsp");           
                 dispatcher.forward(request, response);
            }   
            else {
                response.sendRedirect("Error?code=20");
            }
        }
        catch (Exception e) {
                System.err.println(e.getMessage());
                response.sendRedirect("Error?code=0");
        } 
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getImageRequest(request, response);
    }

}
