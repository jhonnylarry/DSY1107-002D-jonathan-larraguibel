package cl.duoc.ecopunto.reportes.dominio;

public class ServicioNoDisponibleException extends RuntimeException {

    public ServicioNoDisponibleException(String servicio, Throwable causa) {
        super(servicio + " no está disponible", causa);
    }
}
