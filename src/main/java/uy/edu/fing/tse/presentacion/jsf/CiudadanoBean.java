package uy.edu.fing.tse.presentacion.jsf;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import uy.edu.fing.tse.entidad.Ciudadano;
import uy.edu.fing.tse.negocio.CedulaInvalidaException;
import uy.edu.fing.tse.negocio.CiudadanoServiceLocal;

@Named
@RequestScoped
public class CiudadanoBean implements Serializable {

    @EJB
    private CiudadanoServiceLocal ciudadanoService;

    private String cedula;
    private String correo;
    private LocalDate fecha;
    private String mensaje;

    public void agregar() {
        try {
            long cedulaNum = Long.parseLong(cedula);
            ciudadanoService.agregarCiudadano(cedulaNum, correo, fecha);
            mensaje = "Ciudadano agregado correctamente.";
            cedula = null;
            correo = null;
            fecha = null;
        } catch (CedulaInvalidaException e) {
            mensaje = "Error: " + e.getMessage();
        } catch (NumberFormatException e) {
            mensaje = "Error: la cedula debe ser numerica.";
        }
    }

    public List<Ciudadano> getCiudadanos() {
        return ciudadanoService.obtenerCiudadanos();
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }
}
