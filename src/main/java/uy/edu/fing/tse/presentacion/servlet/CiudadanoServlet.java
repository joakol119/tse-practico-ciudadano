package uy.edu.fing.tse.presentacion.servlet;

import java.io.IOException;
import java.time.LocalDate;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uy.edu.fing.tse.entidad.Ciudadano;
import uy.edu.fing.tse.negocio.CedulaInvalidaException;
import uy.edu.fing.tse.negocio.CiudadanoServiceLocal;

@WebServlet("/ciudadano")
public class CiudadanoServlet extends HttpServlet {

    @EJB
    private CiudadanoServiceLocal ciudadanoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String cedulaBuscada = req.getParameter("cedulaBuscar");
        if (cedulaBuscada != null && !cedulaBuscada.isBlank()) {
            try {
                long cedula = Long.parseLong(cedulaBuscada.trim());
                Ciudadano encontrado = ciudadanoService.buscarPorCedula(cedula);
                if (encontrado != null) {
                    req.setAttribute("mensaje", "Ciudadano encontrado.");
                } else {
                    req.setAttribute("mensaje", "No se encontro ningun ciudadano con esa cedula.");
                }
                req.setAttribute("resultadoBusqueda", encontrado);
            } catch (NumberFormatException e) {
                req.setAttribute("mensaje", "La cedula ingresada para buscar no es un numero valido.");
            }
        }
        req.setAttribute("ciudadanos", ciudadanoService.obtenerCiudadanos());
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/ciudadanos.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            long cedula = Long.parseLong(req.getParameter("cedula"));
            String correo = req.getParameter("correo");
            LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
            ciudadanoService.agregarCiudadano(cedula, correo, fecha);
            req.setAttribute("mensaje", "Ciudadano agregado correctamente.");
        } catch (CedulaInvalidaException e) {
            req.setAttribute("mensaje", "Error: " + e.getMessage());
        }
        req.setAttribute("ciudadanos", ciudadanoService.obtenerCiudadanos());
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/ciudadanos.jsp");
        rd.forward(req, resp);
    }
}
