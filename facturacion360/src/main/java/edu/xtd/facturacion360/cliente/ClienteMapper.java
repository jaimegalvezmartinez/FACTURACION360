package edu.xtd.facturacion360.cliente;

import org.springframework.stereotype.Component;

/**
 * Traduce de entidad {@link Cliente} a {@link ClienteDTO}.
 *
 * Por qué en su propia clase y no dentro del service: concentra en un único
 * sitio la regla de "qué campos se exponen", y mantiene el service centrado en
 * la lógica de negocio. Para un proyecto de clase basta un mapper manual; en un
 * proyecto grande se generaría con MapStruct para no escribirlo a mano.
 *
 * Es un @Component para que Spring lo cree y lo inyecte donde haga falta.
 */
@Component
public class ClienteMapper {

    public ClienteDTO aDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCif(),
                cliente.getEmail(),
                cliente.getTelefono()
        );
    }
}
