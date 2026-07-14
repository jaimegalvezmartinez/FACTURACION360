package edu.xtd.facturacion360.cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa a un cliente en la base de datos.
 *
 * Por qué existe como clase aparte y no se expone directamente en el API:
 * la entidad es el MODELO DE PERSISTENCIA (lo que vive en la BD). Nunca la
 * enviamos tal cual al navegador; la traducimos a un {@link ClienteDTO}. Así
 * podemos cambiar la tabla sin romper el contrato del API y evitamos exponer
 * campos internos o relaciones perezosas que provocarían errores al serializar.
 */
@Entity
@Table(name = "clientes")
public class Cliente {

    // El id lo genera la propia BD (autoincremental). Nos sirve además como
    // orden de alta: el id más alto = el cliente más reciente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String cif;
    private String email;
    private String telefono;

    // JPA/Hibernate exige un constructor sin argumentos (lo usa por reflexión).
    protected Cliente() {
    }

    public Cliente(String nombre, String cif, String email, String telefono) {
        this.nombre = nombre;
        this.cif = cif;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters: los necesita el mapper para leer los datos y Hibernate para
    // gestionar la entidad. No añadimos setters porque en esta tarea el
    // cliente no se modifica una vez creado (menos superficie de error).
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCif() {
        return cif;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }
}
