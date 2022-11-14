<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
        <link rel="stylesheet" href="./css/bootstrap.min.css">
        <script src="./scripts/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="./css/styles.css">
    </head>
    <body>
        <div class="d-flex align-items-center justify-content-center vh-100">
            <div class="text-center">
                <h1 class="display-1 fw-bold">¡Ha ocurrido un error!</h1>
                <p class="lead">
                    ${Msg}
                </p>
                <c:choose>
                    <c:when test="${user == null}">
                        <a class="back" href="/WebApp">Inicio</a>
                    </c:when>
                    <c:otherwise>
                        <a class="back" href="menu.jsp">Menú</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </body>
</html>
