package uy.edu.fing.tse.negocio;

import java.time.LocalDate;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import uy.edu.fing.tse.datos.CiudadanoDAOLocal;
import uy.edu.fing.tse.entidad.Ciudadano;

@Stateless
public class CiudadanoService implements CiudadanoServiceRemote, CiudadanoServiceLocal {

    private static final int[] PESOS = {2, 9, 8, 7, 6, 3, 4};

    @EJB
    private CiudadanoDAOLocal ciudadanoDAO;

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:/jms/queue/queue_alta_ciudadano")
    private Queue queueAltaCiudadano;

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
    public void agregarCiudadanoPorJMS(long cedula, String correo, LocalDate fechaPrimerLogin) {
        // Envia el mensaje a la cola; el alta real la procesa el MDB
        // de forma asincrona, permitiendo paralelizar las altas.
        String mensaje = cedula + "|" + correo + "|" + fechaPrimerLogin;
        jmsContext.createProducer().send(queueAltaCiudadano, mensaje);
    }

    @Override
    public List<Ciudadano> obtenerCiudadanos() {
        return ciudadanoDAO.obtenerCiudadanos();
    }

    @Override
    public Ciudadano buscarPorCedula(long cedula) {
        return ciudadanoDAO.buscarPorCedula(cedula);
    }

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
