package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.ImageServiceREST;

@WebServlet(name = "ImageDownload", urlPatterns = {"/ImageDownload"})
public class ImageDownload extends HttpServlet {
    
    private final ImageServiceREST iS = ImageServiceREST.getInstance();

    void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String idParam = request.getParameter("ID");
            String filename = request.getParameter("filename");
            int id  = Integer.parseInt(idParam); 
            boolean downloaded = iS.downloadImage(id, filename);
            if (downloaded) {
                out.println("Funciona!");
            }
            else {
                out.println("Error");
            }
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
