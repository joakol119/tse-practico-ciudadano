package uy.edu.fing.tse.negocio;

import java.time.LocalDate;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import uy.edu.fing.tse.datos.CiudadanoDAOLocal;
import uy.edu.fing.tse.entidad.Ciudadano;

@Stateless
public class CiudadanoService implements CiudadanoServiceRemote, CiudadanoServiceLocal {

    // Pesos del algoritmo de validación de cédula uruguaya
    private static final int[] PESOS = {2, 9, 8, 7, 6, 3, 4};

    @EJB
    private CiudadanoDAOLocal ciudadanoDAO;

    @Override
    public void agregarCiudadano(long cedula, String correo, LocalDate fechaPrimerLogin) {
        if (!cedulaValida(cedula)) {
            throw new CedulaInvalidaException(
                "La cedula " + cedula + " no tiene un digito verificador valido");
        }
        Ciudadano c = new Ciudadano(cedula, correo, fechaPrimerLogin);
        ciudadanoDAO.altaCiudadano(c);
    }

    @Override
    public List<Ciudadano> obtenerCiudadanos() {
        return ciudadanoDAO.obtenerCiudadanos();
    }

    @Override
    public Ciudadano buscarPorCedula(long cedula) {
        return ciudadanoDAO.buscarPorCedula(cedula);
    }

    // Regla de negocio: valida el digito verificador segun el
    // algoritmo de la cedula de identidad uruguaya.
    private boolean cedulaValida(long cedula) {
        String cedulaStr = String.valueOf(cedula);
        if (cedulaStr.length() != 8) {
            return false;
        }
        int suma = 0;
        for (int i = 0; i < 7; i++) {
            int digito = Character.getNumericValue(cedulaStr.charAt(i));
            suma += digito * PESOS[i];
        }
        int resto = suma % 10;
        int verificadorEsperado = (resto == 0) ? 0 : (10 - resto);
        int verificadorIngresado = Character.getNumericValue(cedulaStr.charAt(7));
        return verificadorEsperado == verificadorIngresado;
    }
}
