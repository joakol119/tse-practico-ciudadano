package uy.edu.fing.tse.entidad;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

// JAXB no sabe serializar LocalDate por defecto (no tiene un
// mapeo XML nativo). Este adapter le enseña a convertirlo
// hacia y desde un String en formato ISO (yyyy-MM-dd).
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) {
        return (v == null || v.isBlank()) ? null : LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) {
        return (v == null) ? null : v.toString();
    }
}
