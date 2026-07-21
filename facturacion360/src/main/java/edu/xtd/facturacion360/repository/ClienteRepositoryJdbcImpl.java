package edu.xtd.facturacion360.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.xtd.facturacion360.dto.Cliente;

@Repository
public class ClienteRepositoryJdbcImpl implements ClienteRepository{
	
	//con este objeto, accedemos a base de datos
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ClienteRowMapper clienteRowMapper;


	@Override
	public List<Cliente> findUltimos(int limite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Cliente> findById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Cliente insert(Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Cliente cliente) {

	    // Sentencia SQL que actualiza los datos de un cliente.
	    // Solo se modifican los campos editables; la fecha de alta se mantiene.
	    String sql = """
	        UPDATE clientes
	        SET nombre = ?,
	            nif_cif = ?,
	            direccion = ?,
	            codigopostal = ?,
	            poblacion = ?,
	            provincia = ?,
	            telefono = ?,
	            email = ?
	        WHERE idcliente = ?
	        """;

	    // Ejecutamos la sentencia SQL utilizando JdbcTemplate.
	    // Cada '?' de la consulta se sustituye por el valor correspondiente
	    // del objeto Cliente.
	    int filas = jdbcTemplate.update(
	            sql,
	            cliente.nombre(),
	            cliente.nifCif(),
	            cliente.direccion(),
	            cliente.codigoPostal(),
	            cliente.poblacion(),
	            cliente.provincia(),
	            cliente.telefono(),
	            cliente.email(),
	            cliente.idCliente()
	    );

	    // Si se ha modificado al menos una fila, devolvemos true.
	    // Si no se ha modificado ninguna, devolvemos false.
	    return filas > 0;
	}
	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
