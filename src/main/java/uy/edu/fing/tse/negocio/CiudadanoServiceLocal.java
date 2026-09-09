package uy.edu.fing.tse.negocio;

import java.time.LocalDate;
import java.util.List;
import jakarta.ejb.Local;
import uy.edu.fing.tse.entidad.Ciudadano;

@Local
public interface CiudadanoServiceLocal {
    void agregarCiudadano(long cedula, String correo, LocalDate fechaPrimerLogin);
    void agregarCiudadanoPorJMS(long cedula, String correo, LocalDate fechaPrimerLogin);
    List<Ciudadano> obtenerCiudadanos();
    Ciudadano buscarPorCedula(long cedula);
}
