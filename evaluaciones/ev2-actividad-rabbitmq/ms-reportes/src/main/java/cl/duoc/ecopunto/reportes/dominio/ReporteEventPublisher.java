package cl.duoc.ecopunto.reportes.dominio;

/**
 * Puerto de salida: el dominio solo sabe que "publica un evento".
 * La implementación con RabbitMQ vive en el paquete mensajeria.
 */
public interface ReporteEventPublisher {

    void publicar(ReporteCreadoEvent event);
}
