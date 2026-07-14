package edu.xtd.facturacion360.cliente;

import java.util.List;

/**
 * Envoltorio de respuesta para UNA página de clientes.
 *
 * Por qué NO devolvemos directamente el Page<> de Spring Data:
 *  - Su JSON incluye muchos campos internos (pageable, sort, etc.) que el
 *    frontend no necesita.
 *  - Su estructura no es estable entre versiones y el propio Spring desaconseja
 *    serializarlo tal cual.
 * Con este record decidimos EXACTAMENTE qué metadatos de paginación recibe el
 * navegador, y le damos nombres claros en español.
 */
public record PaginaClientesDTO(
        List<ClienteDTO> contenido,
        int paginaActual,      // índice de página empezando en 0
        int totalPaginas,
        long totalElementos,
        boolean hayAnterior,   // ¿existe una página de clientes MÁS RECIENTES?
        boolean haySiguiente   // ¿existe una página de clientes MÁS ANTIGUOS?
) {
}
