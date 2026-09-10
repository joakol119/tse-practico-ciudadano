package uy.edu.fing.tse.datos;

import java.util.List;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import uy.edu.fing.tse.entidad.Ciudadano;

@Singleton
public class CiudadanoDAO implements CiudadanoDAORemote, CiudadanoDAOLocal {

    @PersistenceContext(unitName = "ciudadanoPU")
    private EntityManager em;

    @Override
    public void altaCiudadano(Ciudadano c) {
        em.persist(c);
    }

    @Override
    public List<Ciudadano> obtenerCiudadanos() {
        return em.createQuery("SELECT c FROM Ciudadano c", Ciudadano.class).getResultList();
    }

    @Override
    public Ciudadano buscarPorCedula(long cedula) {
        return em.find(Ciudadano.class, cedula);
    }
}
