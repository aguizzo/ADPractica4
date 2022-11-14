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
            /*
            Part part = request.getPart("image");
            String contentType = part.getContentType();
            if (contentType.equals("image/jpeg")) {
                String fileName = part.getSubmittedFileName();
                boolean uploaded = uploadFile(part, fileName);
                if(uploaded) {
                    Image image = new Image(title, description, keywords, author,
                        uploader, captureDate, storageDate, fileName);
                    boolean registered = iS.imageRegister(image);
                    if (registered) {
                        response.sendRedirect("imageRegister.jsp?success=1");
                    }
                    else {
                        response.sendRedirect("Error?code=21");
                    }
                }
                else {
                    response.sendRedirect("Error?code=22");
                }     
            }
            else {
                response.sendRedirect("Error?code=27");
            }
            */
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

    private boolean uploadFile(Part part, String fileName)
            throws IOException {
        boolean uploaded = false;
        String path = getPath(fileName);
        FileOutputStream fops = null;
        InputStream is = null;
        try {
            int read;
            is = part.getInputStream();
            fops = new FileOutputStream(new File(path));
            final byte[] bytes = new byte[1024];
            
            while((read = is.read(bytes)) != -1) {
                fops.write(bytes, 0, read);
            }
            uploaded = true;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fops != null) {
                fops.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return uploaded;
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
        imageRegisterRequest(request, response);
    }

}
