package servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ImageServiceREST;
import models.Image;
import models.User;

@WebServlet(name = "ImageDelete", urlPatterns = {"/ImageDelete"})
public class ImageDelete extends HttpServlet {
    
    private final ImageServiceREST iS = ImageServiceREST.getInstance();
    
    protected void deleteImageRequest(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String id = request.getParameter("ID");
            int ID = Integer.parseInt(id);
            Image im = iS.getImage(ID); 
            String uploader = request.getParameter("uploader");
            if (im != null) {
                /*
                String fileName = im.getFileName();
                HttpSession session = request.getSession();
                User user = (User)session.getAttribute("user");
                if (user.getUsername().equals(im.getUploader())) {
                    boolean deleted = deleteFile(fileName);
                    if (deleted) {
                        boolean removed = iS.deleteImage(ID);        
                        if (removed) {
                            response.sendRedirect("menu.jsp?success=1");
                        }
                        else {
                            response.sendRedirect("Error?code=24");
                        }
                    }
                    else {
                      response.sendRedirect("Error?code=25");
                    }   
                }
                else {
                    response.sendRedirect("Error?code=403");
                }
                */
                HttpSession session = request.getSession();
                User user = (User)session.getAttribute("user");
                if (user.getUsername().equals(uploader)) {
                    boolean removed = iS.deleteImage2(ID, uploader);        
                        if (removed) {
                            response.sendRedirect("menu.jsp?success=1");
                        }
                        else {
                            response.sendRedirect("Error?code=24");
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
    
    private boolean deleteFile(String fileName) {
        String path = getPath(fileName);
        File image = new File(path);
        return image.delete();
    }
     
    private String getPath(String fileName) {
        String path = getServletContext().getRealPath("");
        path = path.replace("target/practica2-1.0-SNAPSHOT", "");
        String relativePath = "src" + File.separator + "main" + File.separator
            + "webapp" + File.separator + "images" + File.separator + fileName;
        return path + relativePath;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        deleteImageRequest(request, response);
    }

}
