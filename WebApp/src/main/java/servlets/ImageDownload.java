package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
        try {
            String idParam = request.getParameter("ID");
            String filename = request.getParameter("filename");
            int id  = Integer.parseInt(idParam); 
            File downloaded = iS.downloadImage(id, filename);
            
            ServletOutputStream out = response.getOutputStream();
            String mt = new MimetypesFileTypeMap().getContentType(downloaded);
            response.setContentType(mt);
            response.setHeader("Content-disposition",
                "attachment; filename=" +
                filename);
            
            FileInputStream in = new FileInputStream(downloaded);
            byte[] buffer = new byte[4096];
            int length;
            while((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.flush();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
