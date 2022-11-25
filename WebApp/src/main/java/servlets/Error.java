package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Error", urlPatterns = {"/Error"})
public class Error extends HttpServlet {


    protected void errorRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String errorCode = request.getParameter("code");
            String errorMsg = getErrorMessage(errorCode);
            request.setAttribute("Msg", errorMsg);
            
            RequestDispatcher dispatcher = request.
                    getRequestDispatcher("error.jsp");           
            dispatcher.forward(request, response);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        errorRequest(request, response);
    }
    
    private String getErrorMessage(String errorCode) {
        int code;
        String errorMsg;
        
        if (errorCode == null) {
            errorMsg = "no params";
            return errorMsg;
        }
        else {
            code = Integer.parseInt(errorCode);
        }
        switch(code) {
            case 0:
                errorMsg = "Ha ocurrido una excepción.";
                break;
            // Users errors  
            case 10:
                errorMsg = "Ya existe un usuario con ese nombre.";
                break;
            case 11:
                errorMsg = "El nombre de usuario o la contraseña "
                        + "son incorrectos.";
                break;
            // Image errors   
            case 20:
                errorMsg = "La imagen no existe.";
                break;
            case 21:
                errorMsg = "No se ha podido registrar la imagen en "
                        + "la base de datos.";
                break;
            case 22:
                errorMsg = "No se ha podido guardar el archivo.";
                break;
            case 23:
                errorMsg = "Error en la obtención de imágenes.";
                break;
            case 24:
                errorMsg = "No se ha podido borrar la imagen en "
                        + "la base de datos.";
                break;
            case 25:
                errorMsg = "No se ha podido eliminar el archivo.";
                break;
            case 26:
                errorMsg = "No se ha podido modificar la imagen.";
                break;
            case 27:
                errorMsg = "El archivo no es de tipo jpeg/jpg.";
                break;
            case 28:
                errorMsg = "Fallo en la descarga de la imagen.";
                break;
            case 403:
                errorMsg = "Acceso no autorizado.";
                break;    
            default: 
                errorMsg = "Ha ocurrido algo inesperado.";
                break;
        }
        return errorMsg;
    }

}
