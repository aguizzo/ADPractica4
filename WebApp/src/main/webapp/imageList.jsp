<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${user == null}">
    <c:redirect url="login.jsp"/> 
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de imágenes</title>
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
                  <a class="nav-link active" aria-current="page" href="ImageList">
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
        <div class="jumbotron">
            <div class="container">
                <h1 class="display-6">Lista de Imágenes</h1>
                <c:if test="${imageList.size() == 0}">
                    <p>No hay imágenes.</p>
                </c:if>
          </div>
        </div>
        
        <c:forEach items="${imageList}" var="im">
            <c:url var="imshow" value="ImageShow">
                <c:param name="ID" value="${im.id}"></c:param>
            </c:url>
            <c:url var="imdel" value="imageDelete.jsp">
                <c:param name="ID" value="${im.id}"></c:param>
            </c:url>
            <c:url var="immod" value="imageModify.jsp">
                <c:param name="ID" value="${im.id}"></c:param>
            </c:url>
            <div class="container border border-dark mt-3 custombg">
                <h3>${im.title}</h3>
                <div class="d-flex p-3">
                    <a href="${imshow}">
                        <img src="./images/${im.fileName}" alt="error"
                            class="flex-shrink-0 me-3 border rounded-circle Imlist"
                        >
                    </a>    
                    <div>
                        <h4>
                            Subida por <b>${im.uploader}</b> en: ${im.storageDate}
                        </h4>
                        <p>Fecha de captura: ${im.captureDate}</p>
                        <p>Autor: ${im.author}</p>
                        <p>Palabras clave: ${im.keywords}</p>
                        <c:if test="${user.username == im.uploader}">
                            <a class="btn btn-primary" href="${imdel}">
                                Eliminar Imagen
                            </a>
                            <a class="btn btn-primary" href="${immod}">
                                Modificar Imagen
                            </a>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="container mt-3">
            <a class="back" href="menu.jsp">Volver al menú</a>
        </div>
    </body>
</html>
