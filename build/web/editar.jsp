<%@page import="com.emergentes.modelo.Blog"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Blog salud = (Blog) request.getAttribute("salud");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <center><body>
            <h1>Nuevo/ Editar Entrada</h1>
        <form action="MainControlador" method="post">
        <table>
            <input type="hidden" name="id" value="${salud.id}">
            
            <tr>
                <td>fecha:</td>
                <td><input type="date" name="fecha" value="${salud.fecha}"></td>
            </tr>
            <tr>
                <td>Titulo:</td>
                <td><input type="text" name="titulo" value="${salud.titulo}"></td>
            </tr>
            <tr>
                <td>Contenido:</td>
                <td>
                <textarea name="contenido" rows="3" cols="50" value="${salud.contenido}">
                </textarea>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="ENVIAR"></td>
            </tr>
        </table>
            </form>
    </body></center> 
</html>
