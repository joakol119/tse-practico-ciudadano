package uy.edu.fing.tse.negocio;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.EJB;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import java.time.LocalDate;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/queue_alta_ciudadano")
})
public class CiudadanoMDB implements MessageListener {

    @EJB
    private CiudadanoServiceLocal ciudadanoService;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage textMessage) {
                String texto = textMessage.getText();
                // Formato esperado: cedula|correo|fecha (yyyy-MM-dd)
                String[] partes = texto.split("\\|");
                if (partes.length != 3) {
                    System.out.println("Mensaje con formato invalido, se descarta: " + texto);
                    return;
                }
                long cedula = Long.parseLong(partes[0].trim());
                String correo = partes[1].trim();
                LocalDate fecha = LocalDate.parse(partes[2].trim());

                ciudadanoService.agregarCiudadano(cedula, correo, fecha);
                System.out.println("Alta procesada via JMS para cedula " + cedula);
            }
        } catch (CedulaInvalidaException e) {
            System.out.println("Mensaje JMS rechazado por regla de negocio: " + e.getMessage());
        } catch (JMSException | NumberFormatException | java.time.format.DateTimeParseException e) {
            System.out.println("Error procesando mensaje JMS: " + e.getMessage());
        }
    }
}
