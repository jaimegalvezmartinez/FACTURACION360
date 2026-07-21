package edu.xtd.facturacion360.dto;

import java.util.List;

/**
 * Lo que devuelve el endpoint de paginación (GET /cliente/listar-pagina) como JSON.
 * Además de los clientes de ESTA página, incluye "metadatos" para que el frontend sepa
 * en qué página está y pueda activar/desactivar los botones sin recalcular nada.
 */
public record PaginaClienteResponse(
		List<ClienteResponse> contenido,   // los clientes de esta página
		int paginaActual,                  // índice de la página (empieza en 0)
		int totalPaginas,                  // cuántas páginas hay en total
		long totalElementos,               // cuántos clientes hay en total
		boolean hayAnterior,               // ¿existe una página anterior?
		boolean haySiguiente) {            // ¿existe una página siguiente?
}
