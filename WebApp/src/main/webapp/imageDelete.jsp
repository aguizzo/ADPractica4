<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Image"%>
<%@page import="models.User"%>
<%@page import="models.ImageServiceREST"%>

<c:if test="${user == null}">
    <c:redirect url="login.jsp"/> 
</c:if>

<%
    final ImageServiceREST iS = ImageServiceREST.getInstance();
    String id = request.getParameter("ID");
    int ID = Integer.parseInt(id);
    Image im = iS.getImage(ID);
    if (im == null) {
        response.sendRedirect("Error?code=20");
    }
    else {
        User user = (User)session.getAttribute("user");
        if (!user.getUsername().equals(im.getUploader())) {
            response.sendRedirect("Error?code=403");
        }
        else {
            request.setAttribute("im", im);
        }
    }
%>

    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Eliminar imagen</title>
        <link rel="stylesheet" href="./css/bootstrap.min.css">
        <script src="./scripts/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="./css/styles.css">
    </head>
    <body>
        <h1>¿Quieres eliminar la imagen? </h1>
        <div class="container">
            <div class="row align-t">
                <div class="col-md-1">
                    <form action="ImageDelete" method="POST">
                        <input name="uploader" value="${im.uploader}" hidden>
                        <input name="ID" value="${im.id}" hidden>
                        <input class="btn btn-primary"
                            type="submit" value="Sí">
                    </form>
                </div> 
                <div class="col-md-1">
                    <form action="menu.jsp" method="POST">
                        <input class="btn btn-primary"
                            type="submit" value="No">
                    </form>
                </div>  
            </div>
        </div>
    </body>
</html>
