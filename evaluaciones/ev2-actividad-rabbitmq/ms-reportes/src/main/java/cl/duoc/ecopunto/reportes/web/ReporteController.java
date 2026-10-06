package cl.duoc.ecopunto.reportes.web;

import java.net.URI;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ecopunto.reportes.dominio.PuntoLimpioNoEncontradoException;
import cl.duoc.ecopunto.reportes.dominio.Reporte;
import cl.duoc.ecopunto.reportes.dominio.ReporteInvalidoException;
import cl.duoc.ecopunto.reportes.dominio.ReporteService;
import cl.duoc.ecopunto.reportes.dominio.ServicioNoDisponibleException;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody CrearReporteRequest request) {
        Reporte reporte = service.crear(request.puntoLimpioId(), request.material(), request.descripcion(),
                request.autorEmail());
        return ResponseEntity.created(URI.create("/reportes/" + reporte.id())).body(reporte);
    }

    @GetMapping
    public Collection<Reporte> listar() {
        return service.listar();
    }

    @ExceptionHandler(PuntoLimpioNoEncontradoException.class)
    public ProblemDetail puntoNoEncontrado(PuntoLimpioNoEncontradoException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ReporteInvalidoException.class)
    public ProblemDetail reporteInvalido(ReporteInvalidoException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, e.getMessage());
    }

    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ProblemDetail servicioNoDisponible(ServicioNoDisponibleException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
    }

    public record CrearReporteRequest(Long puntoLimpioId, String material, String descripcion, String autorEmail) {
    }
}
