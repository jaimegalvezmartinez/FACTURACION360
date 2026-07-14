package edu.xtd.facturacion360.cliente;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de acceso a datos de clientes.
 *
 * Por qué es solo una interfaz sin implementación: Spring Data JPA genera la
 * clase concreta en tiempo de arranque. Al heredar de {@link JpaRepository}
 * ya disponemos de save, findById, count, findAll... y —lo importante para
 * esta tarea— de la sobrecarga findAll(Pageable), que devuelve una página con
 * el troceado (LIMIT/OFFSET) y el orden (ORDER BY) resueltos por la BASE DE
 * DATOS, no en memoria. Eso es lo que hace la paginación eficiente aunque haya
 * miles de clientes.
 *
 * Los dos genéricos son: <Cliente> el tipo de la entidad, <Long> el tipo de su id.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // No hace falta declarar nada: findAll(Pageable) se hereda tal cual.
}
