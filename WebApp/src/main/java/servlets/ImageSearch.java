package servlets;

import java.io.IOException;
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

@WebServlet(name = "ImageSearch", urlPatterns = {"/ImageSearch"})
public class ImageSearch extends HttpServlet {
    
    private final ImageService iS = ImageServiceREST.getInstance();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String form = request.getParameter("numForm");
            int numForm = Integer.parseInt(form);     
            List<Image> result = imSearch(numForm, request);

            request.setAttribute("imageList", result);   
            RequestDispatcher dispatcher = request.
                    getRequestDispatcher("imageList.jsp");           
            dispatcher.forward(request, response);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            response.sendRedirect("Error?code=23");
        } 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private List<Image> imSearch(int numForm, HttpServletRequest request) 
        throws Exception {
        List<Image> list = null;
        String title = request.getParameter("title");
        String keywords = request.getParameter("keywords");
        String author = request.getParameter("author");
        String captureDate = request.getParameter("captureDate");
        String storageDate = request.getParameter("storageDate");
        switch (numForm) {
            case 1:
                list = iS.searchByTitle(title);
                break;
            case 2: 
                list = iS.searchByStorageDate(storageDate);
                break;
            case 3:
                list = iS.searchByAuthor(author);
                break;
            case 4:
                list = iS.searchByKeywords(keywords);
                break;
            case 5:
                list = iS.searchImages(title, keywords, author, captureDate);
                break;
        }
        return list;
    }
}
