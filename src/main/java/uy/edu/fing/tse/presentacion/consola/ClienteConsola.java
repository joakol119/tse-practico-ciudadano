package uy.edu.fing.tse.presentacion.consola;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import uy.edu.fing.tse.entidad.Ciudadano;
import uy.edu.fing.tse.negocio.CedulaInvalidaException;
import uy.edu.fing.tse.negocio.CiudadanoServiceRemote;

public class ClienteConsola {

    private static final String JNDI_NAME =
        "ejb:/ciudadano/CiudadanoService!uy.edu.fing.tse.negocio.CiudadanoServiceRemote";

    public static void main(String[] args) throws NamingException {
        CiudadanoServiceRemote service = obtenerService();
        Scanner scanner = new Scanner(System.in);

        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    agregarCiudadano(service, scanner);
                    break;
                case "2":
                    listarCiudadanos(service);
                    break;
                case "3":
                    buscarPorCedula(service, scanner);
                    break;
                case "4":
                    agregarCiudadanoPorJMS(service, scanner);
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        scanner.close();
    }

    private static CiudadanoServiceRemote obtenerService() throws NamingException {
        Properties props = new Properties();
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        Context context = new InitialContext(props);
        return (CiudadanoServiceRemote) context.lookup(JNDI_NAME);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Gestor de Ciudadanos (consola) ---");
        System.out.println("1) Agregar ciudadano");
        System.out.println("2) Listar ciudadanos");
        System.out.println("3) Buscar por cedula");
        System.out.println("4) Agregar ciudadano via JMS (asincrono)");
        System.out.println("0) Salir");
        System.out.print("Opcion: ");
    }

    private static void agregarCiudadano(CiudadanoServiceRemote service, Scanner scanner) {
        try {
            System.out.print("Cedula (8 digitos): ");
            long cedula = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Correo electronico: ");
            String correo = scanner.nextLine().trim();
            System.out.print("Fecha primer login (yyyy-MM-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());

            service.agregarCiudadano(cedula, correo, fecha);
            System.out.println("Ciudadano agregado correctamente.");
        } catch (CedulaInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Error de formato: " + e.getMessage());
        }
    }

    private static void agregarCiudadanoPorJMS(CiudadanoServiceRemote service, Scanner scanner) {
        try {
            System.out.print("Cedula (8 digitos): ");
            long cedula = Long.parseLong(scanner.nextLine().trim());
            System.out.print("Correo electronico: ");
            String correo = scanner.nextLine().trim();
            System.out.print("Fecha primer login (yyyy-MM-dd): ");
            LocalDate fecha = LocalDate.parse(scanner.nextLine().trim());

            service.agregarCiudadanoPorJMS(cedula, correo, fecha);
            System.out.println("Mensaje enviado a la cola. El alta se procesara de forma asincrona.");
            System.out.println("(la validacion de cedula ocurre del lado del MDB al procesar el mensaje)");
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Error de formato: " + e.getMessage());
        }
    }

    private static void listarCiudadanos(CiudadanoServiceRemote service) {
        List<Ciudadano> lista = service.obtenerCiudadanos();
        if (lista.isEmpty()) {
            System.out.println("No hay ciudadanos cargados.");
            return;
        }
        for (Ciudadano c : lista) {
            System.out.println(c);
        }
    }

    private static void buscarPorCedula(CiudadanoServiceRemote service, Scanner scanner) {
        System.out.print("Cedula a buscar: ");
        try {
            long cedula = Long.parseLong(scanner.nextLine().trim());
            Ciudadano c = service.buscarPorCedula(cedula);
            System.out.println(c != null ? c.toString() : "No encontrado.");
        } catch (NumberFormatException e) {
            System.out.println("Cedula invalida.");
        }
    }
}
