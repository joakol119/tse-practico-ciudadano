package uy.edu.fing.tse.negocio;

import jakarta.ejb.ApplicationException;

// @ApplicationException le dice al contenedor EJB que esta excepcion
// NO debe envolverse en EJBException: debe propagarse tal cual hasta
// el cliente (el Servlet), preservando su tipo original.
@ApplicationException(rollback = true)
public class CedulaInvalidaException extends RuntimeException {
    public CedulaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
