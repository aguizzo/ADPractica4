<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
        
<c:if test="${user == null}">
    <c:redirect url="login.jsp"/> 
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menú</title>
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
                  <a class="nav-link  active" aria-current="page" href="menu.jsp">
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

        <div class="jumbotron">
            <div class="container">
                <c:if test="${param.success == '1'}">
                    <p class="success">¡Imagen eliminada con éxito!</p>
                </c:if>
                <h1 class="display-3">Bienvenido ${user.username}</h1>
                <p>Elige que hacer:</p>
          </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <h2>Ver Imágenes</h2>
                    <p>Mira todas las imágenes guardadas por nuestros usuarios.</p>
                    <p><a class="btn btn-primary"
                          href="ImageList">Lista de Imágenes</a>
                    </p>
                </div>
                <div class="col-md-4">
                    <h2>Registrar tus imágenes</h2>
                    <p>Comparte tus imágenes con el resto de usuarios.</p>
                    <br>
                    <p>
                        <a class="btn btn-primary"
                          href="imageRegister.jsp">Registrar Imagen</a>
                    </p>
                </div>
                <div class="col-md-4">
                    <h2>Buscar Imágines</h2>
                    <p>¿Te interesa buscar cierto tipo de imágenes? ¡Te podemos ayudar!</p>
                    <p>
                        <a class="btn btn-primary"
                           href="imageSearch.jsp">Buscar Imágenes</a>
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>


