package servlets;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.UserService;
import models.UserServiceREST;


@WebServlet(name = "UserRegister", urlPatterns = {"/UserRegister"})
public class UserRegister extends HttpServlet {

    private final UserService uS = UserServiceREST.getInstance();

    protected void userRegisterRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection connection = null;
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            boolean registered = uS.userRegister(username, password);
            if(registered) {
               response.sendRedirect("login.jsp?success=1");
            }
            else {
                response.sendRedirect("Error?code=10");
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
        userRegisterRequest(request, response);
    }

}
