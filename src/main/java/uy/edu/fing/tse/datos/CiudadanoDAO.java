package uy.edu.fing.tse.datos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.ejb.Singleton;
import uy.edu.fing.tse.entidad.Ciudadano;

@Singleton
public class CiudadanoDAO implements CiudadanoDAORemote, CiudadanoDAOLocal {

    private final Map<Long, Ciudadano> ciudadanos = new ConcurrentHashMap<>();

    @Override
    public void altaCiudadano(Ciudadano c) {
        ciudadanos.put(c.getCedula(), c);
    }

    @Override
    public List<Ciudadano> obtenerCiudadanos() {
        return new ArrayList<>(ciudadanos.values());
    }

    @Override
    public Ciudadano buscarPorCedula(long cedula) {
        return ciudadanos.get(cedula);
    }
}
