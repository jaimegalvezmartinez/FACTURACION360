package edu.xtd.facturacion360.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Lógica de negocio de clientes.
 *
 * Por qué una capa de servicio entre el controller y el repository:
 *  - El controller solo debe traducir HTTP <-> Java.
 *  - El repository solo debe hablar con la BD.
 *  - La regla "los últimos primero, de 10 en 10" es una decisión de negocio y
 *    vive AQUÍ, sin mezclarse con detalles web ni de SQL. Si mañana el criterio
 *    cambia (p. ej. ordenar por fecha real de alta), se toca un único sitio.
 */
@Service
public class ClienteService {

    private final ClienteRepository repositorio;
    private final ClienteMapper mapper;

    // Inyección por constructor: deja las dependencias explícitas e inmutables
    // y hace la clase fácil de testear (le puedes pasar dobles de prueba).
    public ClienteService(ClienteRepository repositorio, ClienteMapper mapper) {
        this.repositorio = repositorio;
        this.mapper = mapper;
    }

    /**
     * Devuelve una página de clientes ordenados del más reciente al más antiguo.
     * La página 0 son "los 10 últimos"; la página 1, "los 10 anteriores", etc.
     *
     * @param pagina índice de página (0 = más recientes)
     * @param tamano número de clientes por página
     */
    public PaginaClientesDTO obtenerPagina(int pagina, int tamano) {
        // Ordenamos por id DESCENDENTE: al ser autoincremental, el id nos da el
        // orden de alta sin necesidad de un campo de fecha. La BD aplica el
        // ORDER BY y el LIMIT/OFFSET; nosotros no troceamos nada en memoria.
        Pageable peticion = PageRequest.of(pagina, tamano, Sort.by("id").descending());

        Page<Cliente> paginaEntidades = repositorio.findAll(peticion);

        // Convertimos cada entidad a DTO y construimos la respuesta con los
        // metadatos que el frontend usará para habilitar/deshabilitar botones.
        return new PaginaClientesDTO(
                paginaEntidades.map(mapper::aDTO).getContent(),
                paginaEntidades.getNumber(),
                paginaEntidades.getTotalPages(),
                paginaEntidades.getTotalElements(),
                paginaEntidades.hasPrevious(), // hay página más reciente
                paginaEntidades.hasNext()      // hay página más antigua
        );
    }
}
