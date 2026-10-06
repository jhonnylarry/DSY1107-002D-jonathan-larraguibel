package cl.duoc.ecopunto.puntoslimpios;

import java.util.List;

public record PuntoLimpio(
        Long id,
        String nombre,
        String direccion,
        String comuna,
        List<String> materialesAceptados) {

    public boolean acepta(String material) {
        return materialesAceptados.contains(material.toUpperCase());
    }
}
