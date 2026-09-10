package uy.edu.fing.tse.entidad;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@Table(name = "ciudadano")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ciudadano implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private long cedula;

    private String correoElectronico;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaPrimerLogin;

    public Ciudadano() {
    }

    public Ciudadano(long cedula, String correoElectronico, LocalDate fechaPrimerLogin) {
        this.cedula = cedula;
        this.correoElectronico = correoElectronico;
        this.fechaPrimerLogin = fechaPrimerLogin;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFechaPrimerLogin() {
        return fechaPrimerLogin;
    }

    public void setFechaPrimerLogin(LocalDate fechaPrimerLogin) {
        this.fechaPrimerLogin = fechaPrimerLogin;
    }

    @Override
    public String toString() {
        return "Ciudadano{cedula=" + cedula +
               ", correoElectronico='" + correoElectronico + '\'' +
               ", fechaPrimerLogin=" + fechaPrimerLogin + '}';
    }
}
