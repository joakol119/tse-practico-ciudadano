package uy.edu.fing.tse.presentacion.soap;

import java.time.LocalDate;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import uy.edu.fing.tse.entidad.Ciudadano;
import uy.edu.fing.tse.negocio.CedulaInvalidaException;
import uy.edu.fing.tse.negocio.CiudadanoServiceLocal;

@WebService(serviceName = "CiudadanoWebService")
public class CiudadanoWebService {

    @EJB
    private CiudadanoServiceLocal ciudadanoService;

    @WebMethod
    public String agregarCiudadano(@WebParam(name = "cedula") long cedula,
                                    @WebParam(name = "correo") String correo,
                                    @WebParam(name = "fecha") String fecha) {
        try {
            ciudadanoService.agregarCiudadano(cedula, correo, LocalDate.parse(fecha));
            return "Ciudadano agregado correctamente.";
        } catch (CedulaInvalidaException e) {
            return "Error: " + e.getMessage();
        }
    }

    @WebMethod
    public List<Ciudadano> obtenerCiudadanos() {
        return ciudadanoService.obtenerCiudadanos();
    }

    @WebMethod
    public Ciudadano buscarPorCedula(@WebParam(name = "cedula") long cedula) {
        return ciudadanoService.buscarPorCedula(cedula);
    }
}
