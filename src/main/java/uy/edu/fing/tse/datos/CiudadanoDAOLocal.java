package uy.edu.fing.tse.datos;

import java.util.List;
import jakarta.ejb.Local;
import uy.edu.fing.tse.entidad.Ciudadano;

@Local
public interface CiudadanoDAOLocal {
    void altaCiudadano(Ciudadano c);
    List<Ciudadano> obtenerCiudadanos();
    Ciudadano buscarPorCedula(long cedula);
}
