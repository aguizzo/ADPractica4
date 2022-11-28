<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${user == null}">
    <c:redirect url="login.jsp"/> 
</c:if>

<c:url var="imdel" value="imageDelete.jsp">
    <c:param name="ID" value="${image.id}"></c:param>
</c:url>
<c:url var="immod" value="imageModify.jsp">
    <c:param name="ID" value="${image.id}"></c:param>
</c:url>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Imagen ${image.id}</title>
        <link rel="stylesheet" href="./css/bootstrap.min.css">
        <script src="./scripts/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="./css/styles.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
              <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                  <a class="nav-link" aria-current="page" href="menu.jsp">
                      Menú
                  </a>
                </li>                  
                <li class="nav-item">
                  <a class="nav-link" aria-current="page" href="ImageList">
                      Lista de imágenes
                  </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="imageRegister.jsp">
                      Registrar imagen
                  </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="imageSearch.jsp">
                        Buscar imágenes
                    </a>
                </li>
              </ul>
              <form class="d-flex" action="Logout" method="POST">
                <input class="btn btn-outline-success"
                    type="submit" name="logout" value="Logout"/>
              </form>
            </div>
        </div>
        </nav>
        <div class="container-fluid">
            <c:if test="${param.success == '1'}">
                <p class="success">¡Imagen modificada con éxito!</p>
            </c:if>
            <h1 class="display-6">${image.title}</h1>
            <p>Subida por <b>${image.uploader}</b> en ${image.storageDate}</p>
        
            <img src="http://192.168.122.238:8080/RESTService/resources/api/getImage/${image.id}" alt="error">
            <form
               class="Imdesc"
               action="ImageDownload" 
               method="GET" 
               >
               <input name="ID" value="${image.id}" hidden>
               <input name="filename" value="${image.fileName}" hidden>
               <input class="btn btn-primary"
                    type="submit" value="Descargar imagen">
            </form>
            <p class="Imdesc">Descripción:</p>
                <p class="Imattr">${image.description}</p>
            <p class="Imdesc">Autor de la imagen:</p>
                <p class="Imattr"><b>${image.author}</b></p>
            <p class="Imdesc">Fecha de captura:</p>
                <p class="Imattr"> ${image.captureDate}</p>
            <p class="Imdesc">Palabras clave:</p>
                <p class="Imattr">${image.keywords}</p>
            <c:if test="${user.username == image.uploader}">
                <a class="btn btn-primary" href="${imdel}">
                    Eliminar Imagen
                </a>
                <a class="btn btn-primary" href="${immod}">
                    Modificar Imagen
                </a>
            </c:if>
            </div>
        
        <br>
        <a class="back" href="menu.jsp">Volver al menú</a>
    </body>
</html>
