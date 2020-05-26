<%@page import="com.emergentes.modelo.Blog"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    if (session.getAttribute("logueado") != "posi") {
        response.sendRedirect("login.jsp");
    }
%>

<%
    List<Blog> lista = (List<Blog>) request.getAttribute("lista");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <p  align="right"  >${sessionScope.usuario} 

        <a href="LoginControlador?action=logout">Salir al login   </a></p>
    <body>
    <center>  <h1>BLOG-SALUD</h1> </center>

            <a href="MainControlador?op=nuevo" >Nuevo Entrada</a><br><br>

            <c:forEach var="item" items="${lista}">
            <tr>
            <br>
           
            ${item.fecha}
            <br>
            <br>
            ${item.titulo}
            <br>
            <br>
            ${item.contenido}
            <br>
            <p align="left" >     Autor: ${sessionScope.usuario} </p> 
            <p  align="right" >  
            <a href="MainControlador?op=eliminar&id=${item.id}"
            onclick="return(confirm('Seguro que quiere eliminar?'))"> Eliminar</a>

            <a href="MainControlador?op=editar&id=${item.id}">Editar</a> </p>

        </tr>

  <p>------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ </p>       

    </body>
</c:forEach>

</body>


</html>





