package uy.edu.fing.tse.datos;

import java.util.List;
import jakarta.ejb.Remote;
import uy.edu.fing.tse.entidad.Ciudadano;

@Remote
public interface CiudadanoDAORemote {
    void altaCiudadano(Ciudadano c);
    List<Ciudadano> obtenerCiudadanos();
    Ciudadano buscarPorCedula(long cedula);
}
