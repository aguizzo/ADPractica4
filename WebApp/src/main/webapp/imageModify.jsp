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
        <title>Modificar imagen</title>
        <link rel="stylesheet" href="./css/bootstrap.min.css">
        <script src="./scripts/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="./css/styles.css">
    </head>
    <body>
        <div class="login">
        <h1>Modificar Imagen</h1>
            <form 
                action="ImageModify" 
                method="POST" 
            >
                <div class="form-group">
                    <label class="form-label" for="title">
                        Título:
                    </label>
                    <input class="form-control"
                           type="text"
                           name="title"
                           value="${im.title}" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="description">
                        Descripción:
                    </label>
                    <input class="form-control"
                           type="text"
                           name="description" 
                           value="${im.description}" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="keywords">
                        Palabras clave:
                    </label>
                    <input class="form-control"
                           type="text"
                           name="keywords"
                           value="${im.keywords}" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="author">
                        Autor:
                    </label>
                    <input class="form-control"
                           type="text"
                           name="author"
                           value="${user.username}" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="captureDate">
                        Fecha de captura:
                    </label>
                    <input class="form-control"
                           type="date"
                           name="captureDate"
                           value="${im.captureDate}"
                           required>
                </div>
                <input name="ID" value="${im.id}" hidden>
                <input name="uploader" value="${im.uploader}" hidden>
                <input class="btn btn-primary"
                       type="submit" value="Modificar imagen">
            </form>
            <br>
            <a class="back" href="menu.jsp">Volver al menú</a>
        </div>
    </body>
</html>

