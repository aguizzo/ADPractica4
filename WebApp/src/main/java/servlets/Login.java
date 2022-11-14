package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.User;
import models.UserService;
import models.UserServiceREST;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
    private final UserService uS = UserServiceREST.getInstance();
    
    protected void loginRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = uS.userLogin(username, password);
            
            if (user == null) {
                 response.sendRedirect("Error?code=11");
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setMaxInactiveInterval(3000);
                response.sendRedirect(request.getContextPath() + "/menu.jsp");
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            response.sendRedirect("Error?code=0");
        }
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        loginRequest(request, response);
    }
}
