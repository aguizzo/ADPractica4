<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:if test="${user == null}">
    <c:redirect url="login.jsp"/> 
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Búsqueda de imágenes</title>
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
                    <a class="nav-link active" href="imageSearch.jsp">
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
        <h1>Búsqueda de Imágenes</h1>
    <div class="accordion" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
              <button class="accordion-button collapsed" 
                      type="button" data-bs-toggle="collapse" 
                      data-bs-target="#collapseTwo" 
                      aria-expanded="false" aria-controls="collapseTwo">
                Buscar por titulo
              </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse" 
                 aria-labelledby="headingTwo" 
                 data-bs-parent="#accordionExample">
                <div class="login">
                    <form action="ImageSearch" method="GET">
                        <div class="form-group">
                            <label class="form-label" for="title">
                                Título
                            </label>
                            <input class="form-control"
                                   type="text"
                                   name="title">
                            <input name="numForm" value="1" hidden>
                        </div>
                        <input class="btn btn-primary"
                            type="submit" value="Buscar imagen">
                    </form>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="heading3">
              <button class="accordion-button collapsed" 
                      type="button" data-bs-toggle="collapse" 
                      data-bs-target="#collapse3" 
                      aria-expanded="false" aria-controls="collapse3">
                Buscar por fecha de creación
              </button>
            </h2>
            <div id="collapse3" class="accordion-collapse collapse" 
                 aria-labelledby="heading3" 
                 data-bs-parent="#accordionExample">
                <div class="login">
                    <form action="ImageSearch" method="GET">
                        <div class="form-group">
                            <label class="form-label" for="storageDate">
                                Fecha de creación
                            </label>
                            <input class="form-control"
                                type="date"
                                name="storageDate">
                        </div>
                        <input name="numForm" value="2" hidden>
                        <input class="btn btn-primary"
                            type="submit" value="Buscar imagen">
                    </form>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="heading4">
              <button class="accordion-button collapsed" 
                      type="button" data-bs-toggle="collapse" 
                      data-bs-target="#collapse4" 
                      aria-expanded="false" aria-controls="collapse4">
                Buscar por autor
              </button>
            </h2>
            <div id="collapse4" class="accordion-collapse collapse" 
                 aria-labelledby="heading4" 
                 data-bs-parent="#accordionExample">
                <div class="login">
                    <form action="ImageSearch" method="GET">
                        <div class="form-group">
                            <label class="form-label" for="author">
                                Autor
                            </label>
                            <input class="form-control"
                                type="text"
                                name="author">
                        </div>
                        <input name="numForm" value="3" hidden>
                        <input class="btn btn-primary"
                            type="submit" value="Buscar imagen">
                    </form>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="heading5">
              <button class="accordion-button collapsed" 
                      type="button" data-bs-toggle="collapse" 
                      data-bs-target="#collapse5" 
                      aria-expanded="false" aria-controls="collapse5">
                Buscar por palabra clave
              </button>
            </h2>
            <div id="collapse5" class="accordion-collapse collapse" 
                 aria-labelledby="heading5" 
                 data-bs-parent="#accordionExample">
                <div class="login">
                    <form action="ImageSearch" method="GET">
                        <div class="form-group">
                            <label class="form-label" for="keywords">
                                Palabra clave
                            </label>
                            <input class="form-control"
                                type="text"
                                name="keywords">
                        </div>
                        <input name="numForm" value="4" hidden>
                        <input class="btn btn-primary"
                            type="submit" value="Buscar imagen">
                    </form>
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="heading6">
              <button class="accordion-button collapsed" 
                      type="button" data-bs-toggle="collapse" 
                      data-bs-target="#collapse6" 
                      aria-expanded="false" aria-controls="collapse6">
                Búsqueda combinada
              </button>
            </h2>
            <div id="collapse6" class="accordion-collapse collapse" 
                 aria-labelledby="heading6" 
                 data-bs-parent="#accordionExample">
                <div class="login">
                    <form action="ImageSearch" method="GET">
                      <div class="form-group">
                          <label class="form-label" for="title">
                              Buscar por título
                          </label>
                          <input class="form-control"
                                 type="text"
                                 name="title">
                      </div>
                      <div class="form-group">
                          <label class="form-label" for="keywords">
                              Buscar por palabras clave
                          </label>
                          <input class="form-control"
                                 type="text"
                                 name="keywords">
                      </div>
                      <div class="form-group">
                          <label class="form-label" for="author">
                              Buscar por autor
                          </label>
                          <input class="form-control"
                                 type="text"
                                 name="author">
                      </div>
                      <div class="form-group">
                          <label class="form-label" for="captureDate">
                              Capturada a partir de:
                          </label>
                          <input class="form-control"
                                 type="date"
                                 name="captureDate">
                      </div>
                      <input name="numForm" value="5" hidden>
                      <input class="btn btn-primary"
                             type="submit" value="Buscar imagen">
                    </form>
                </div>
            </div>
        </div>
    </div>
        <br>
        <a class="back" href="menu.jsp">Volver al menú</a>
    </div>  
    </body>
</html>
