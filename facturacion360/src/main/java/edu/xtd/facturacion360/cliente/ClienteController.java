package edu.xtd.facturacion360.cliente;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * API REST de clientes.
 *
 * Por qué @RestController: cada método devuelve un objeto Java que Spring
 * serializa a JSON automáticamente (con Jackson). No renderiza plantillas de
 * servidor; el HTML lo sirve Spring como recurso estático y el JavaScript pide
 * los datos a este endpoint.
 *
 * El endpoint es de SOLO LECTURA y paginado. El frontend llamará a
 *   GET /api/clientes?pagina=0&tamano=10
 * y recibirá un {@link PaginaClientesDTO}.
 */
@RestController
public class ClienteController {

    private final ClienteService servicio;

    public ClienteController(ClienteService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/api/clientes")
    public PaginaClientesDTO listar(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano) {
        // Los valores por defecto permiten llamar a /api/clientes sin parámetros
        // y obtener directamente "los 10 últimos".
        return servicio.obtenerPagina(pagina, tamano);
    }
}
