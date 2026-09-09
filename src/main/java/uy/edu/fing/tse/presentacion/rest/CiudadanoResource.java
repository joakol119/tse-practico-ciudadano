package uy.edu.fing.tse.presentacion.rest;

import java.time.LocalDate;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uy.edu.fing.tse.entidad.Ciudadano;
import uy.edu.fing.tse.negocio.CedulaInvalidaException;
import uy.edu.fing.tse.negocio.CiudadanoServiceLocal;

@Path("/ciudadanos")
public class CiudadanoResource {

    @EJB
    private CiudadanoServiceLocal ciudadanoService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ciudadano> listar() {
        return ciudadanoService.obtenerCiudadanos();
    }

    @GET
    @Path("/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@PathParam("cedula") long cedula) {
        Ciudadano c = ciudadanoService.buscarPorCedula(cedula);
        if (c == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No se encontro ningun ciudadano con esa cedula\"}")
                    .build();
        }
        return Response.ok(c).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregar(CiudadanoDTO dto) {
        try {
            ciudadanoService.agregarCiudadano(dto.cedula, dto.correo, LocalDate.parse(dto.fecha));
            return Response.status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Ciudadano agregado correctamente\"}")
                    .build();
        } catch (CedulaInvalidaException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    public static class CiudadanoDTO {
        public long cedula;
        public String correo;
        public String fecha;
    }
}
