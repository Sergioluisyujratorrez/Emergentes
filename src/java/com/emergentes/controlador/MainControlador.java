package com.emergentes.controlador;

import com.emergentes.modelo.Blog;
import com.emergentes.utiles.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MainControlador", urlPatterns = {"/MainControlador"})
public class MainControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op;
        op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";

        ArrayList<Blog> lista = new ArrayList<Blog>();

        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();

        PreparedStatement ps;
        ResultSet rs;

        if (op.equals("list")) {
            try {
                String sql = "select * from salud";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Blog lib = new Blog();
                    lib.setId(rs.getInt("id"));
                    lib.setFecha(rs.getString("fecha"));
                    lib.setTitulo(rs.getString("titulo"));
                    lib.setContenido(rs.getString("contenido"));

                    lista.add(lib);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("panel.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error en SQl " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
        }
        if (op.equals("nuevo")) {
            Blog l = new Blog();
            request.setAttribute("salud", l);
            request.getRequestDispatcher("editar.jsp").forward(request, response);

        }
        //editar
        if (op.equals("editar")) {
            try {
                int id = Integer.parseInt(request.getParameter(("id")));
                String sql = "select * from salud where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                rs = ps.executeQuery();

                Blog blo = new Blog();

                while (rs.next()) {
                    blo.setId(rs.getInt("id"));
                    blo.setFecha(rs.getString("fecha"));
                    blo.setTitulo(rs.getString("titulo"));
                    blo.setContenido(rs.getString("contenido"));
                }
                request.setAttribute("salud", blo);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL " + ex.getMessage());
            }
        }

        if (op.equals("eliminar")) {
            try {
                int id = Integer.parseInt(request.getParameter(("id")));

                String sql = "delete from salud where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);

                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error de SQL " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("MainControlador");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String fecha = request.getParameter("fecha");
        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");

        Blog l = new Blog();
        l.setId(id);
        l.setFecha(fecha);
        l.setTitulo(titulo);
        l.setContenido(contenido);

        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;

        if (id == 0) {
            String sql = "insert into salud (fecha,titulo,contenido) values (?,?,?)";
            try {

                ps = conn.prepareStatement(sql);
                ps.setString(1, l.getFecha());
                ps.setString(2, l.getTitulo());
                ps.setString(3, l.getContenido());

                ps.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Error de SQL " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("MainControlador");
        }
        //si el registro ya existe
        else{
            try {
                String sql ="update salud set fecha=?,titulo=?,contenido=? where id = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, l.getFecha());
                ps.setString(2, l.getTitulo());
                ps.setString(3, l.getContenido());
                ps.setInt(4, l.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
               System.out.println("Error de al actualizar" + ex.getMessage());
            }
             response.sendRedirect("MainControlador");
            
        }
    }
}
