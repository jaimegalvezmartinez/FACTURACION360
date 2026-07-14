package edu.xtd.facturacion360.cliente;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Siembra clientes de ejemplo al arrancar la aplicación.
 *
 * Por qué un CommandLineRunner en lugar de un data.sql:
 *  - Se ejecuta DESPUÉS de que Hibernate haya creado las tablas, así que evita
 *    el clásico problema de orden con la BD en memoria (data.sql se ejecuta
 *    antes salvo que actives spring.jpa.defer-datasource-initialization).
 *  - Es solo para desarrollo/clase. En producción los datos vendrían de una BD
 *    real, así que esta clase se eliminaría.
 *
 * Genera 28 clientes -> 3 páginas de 10 (10 + 10 + 8), suficiente para probar
 * la paginación de principio a fin.
 */
@Component
public class CargaDatosInicial implements CommandLineRunner {

    private final ClienteRepository repositorio;

    public CargaDatosInicial(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void run(String... args) {
        // Si ya hay datos, no duplicamos (útil si algún día usas BD persistente).
        if (repositorio.count() > 0) {
            return;
        }

        for (int i = 1; i <= 28; i++) {
            String n = String.format("%02d", i);
            repositorio.save(new Cliente(
                    "Empresa " + n + " S.L.",
                    "B" + (10_000_000 + i),
                    "contacto" + n + "@empresa" + n + ".es",
                    "6" + String.format("%08d", i)
            ));
        }
    }
}
