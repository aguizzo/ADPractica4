package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Image;
import models.ImageService;
import models.ImageServiceREST;

@WebServlet(name = "ImageList", urlPatterns = {"/ImageList"})
public class ImageList extends HttpServlet {
    
    private final ImageService iS = ImageServiceREST.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getImageListRequest(request, response);
    }
    
    protected void getImageListRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        try {
            RequestDispatcher dispatcher = null;
            List<Image> list = iS.getImageList();
            request.setAttribute("imageList", list);   
            dispatcher = request.
                getRequestDispatcher("imageList.jsp");           
            dispatcher.forward(request, response);    
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            response.sendRedirect("Error?code=23");
        } 
    }
    
    private String getPath(String fileName) {
        String path = getServletContext().getRealPath("");
        path = path.replace("target/WebApp-1.0-SNAPSHOT", "");
        String relativePath = "src" + File.separator + "main" + File.separator
            + "webapp" + File.separator + "images" + File.separator + fileName;
        return path + relativePath;
    }
      
}
