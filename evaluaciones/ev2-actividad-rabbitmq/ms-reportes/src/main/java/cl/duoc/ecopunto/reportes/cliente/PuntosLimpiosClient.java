package cl.duoc.ecopunto.reportes.cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import cl.duoc.ecopunto.reportes.dominio.PuntoLimpioNoEncontradoException;
import cl.duoc.ecopunto.reportes.dominio.ServicioNoDisponibleException;

/**
 * Cliente HTTP síncrono hacia ms-puntos-limpios.
 */
@Component
public class PuntosLimpiosClient {

    private static final Logger log = LoggerFactory.getLogger(PuntosLimpiosClient.class);

    private final RestClient restClient;

    public PuntosLimpiosClient(@Value("${ecopunto.puntos-limpios.url}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public ValidacionMaterial validarMaterial(Long puntoLimpioId, String material) {
        log.info("[SYNC] GET /puntos-limpios/{}/materiales/{} -> ms-puntos-limpios", puntoLimpioId, material);
        try {
            ValidacionMaterial respuesta = restClient.get()
                    .uri("/puntos-limpios/{id}/materiales/{material}", puntoLimpioId, material)
                    .retrieve()
                    .body(ValidacionMaterial.class);
            log.info("[SYNC] respuesta: {}", respuesta);
            return respuesta;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PuntoLimpioNoEncontradoException(puntoLimpioId);
            }
            throw e;
        } catch (ResourceAccessException e) {
            throw new ServicioNoDisponibleException("ms-puntos-limpios", e);
        }
    }

    public record ValidacionMaterial(
            Long puntoLimpioId, String nombre, String comuna, String material, boolean aceptado) {
    }
}
