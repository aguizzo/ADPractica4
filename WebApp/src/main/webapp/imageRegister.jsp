<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${user == null}">
    <c:redirect url="login.jsp"/> 
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro de Imagen</title>
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
                  <a class="nav-link active" href="imageRegister.jsp">
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
        <div class="login">
            <h1>Registro de Imagen</h1>
            <c:if test="${param.success == '1'}">
                <p class="success">¡Imagen registrada con éxito!</p>
            </c:if>
            <form 
                action="ImageRegister" 
                method="POST"
                enctype="multipart/form-data"
            >
                <div class="form-group">
                    <label class="form-label" for="title">
                        Título:
                    </label>
                    <input class="form-control"
                           type="text"
                           name="title" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="description">
                        Descripción:
                    </label>
                    <input class="form-control"
                           type="text"
                           name="description" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="keywords">
                        Palabras clave:
                    </label>
                    <input class="form-control"
                           type="text"
                           name="keywords" required>
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
                           name="captureDate" required>
                </div>
                <div class="form-group">
                    <label class="form-label" for="image">
                        Selecciona una imagen (tipo jpg/jpeg):
                    </label>
                    <input class="form-control"
                           type="file"
                           name="image" required>
                </div>
                <input class="btn btn-primary"
                       type="submit" value="Subir imagen">
            </form>
            <br>
            <a class="back" href="menu.jsp">Volver al menú</a>
        </div>
    </body>
</html>
