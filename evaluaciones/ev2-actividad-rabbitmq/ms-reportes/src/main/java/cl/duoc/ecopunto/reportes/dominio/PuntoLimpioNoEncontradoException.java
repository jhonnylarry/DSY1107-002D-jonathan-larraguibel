package cl.duoc.ecopunto.reportes.dominio;

public class PuntoLimpioNoEncontradoException extends RuntimeException {

    public PuntoLimpioNoEncontradoException(Long id) {
        super("No existe el punto limpio " + id);
    }
}
