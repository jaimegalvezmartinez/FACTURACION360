package edu.xtd.facturacion360.cliente;

/**
 * DTO (Data Transfer Object) que viaja al navegador como JSON.
 *
 * Por qué un record y no la propia entidad:
 *  - Es inmutable y no arrastra lógica de persistencia (ni proxies de Hibernate).
 *  - Expone SOLO los campos que la tabla necesita: es el "contrato" con el
 *    frontend. Si mañana la entidad {@link Cliente} cambia (p. ej. añade un
 *    campo interno), el DTO nos aísla y el JavaScript del compañero no se rompe.
 *
 * Incluimos el id porque los botones Ver/Editar/Eliminar de cada fila lo
 * necesitarán para saber sobre qué cliente actúan.
 */
public record ClienteDTO(
        Long id,
        String nombre,
        String cif,
        String email,
        String telefono
) {
}
