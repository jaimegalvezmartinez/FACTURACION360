package edu.xtd.facturacion360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import edu.xtd.facturacion360.dto.Cliente;
import edu.xtd.facturacion360.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public List<Cliente> listarUltimos(int limite) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente obtenerPorId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente crear(Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente actualizar(int id, Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void eliminar(int id) {

		boolean borrado = this.clienteRepository.deleteById(id);
		if (!borrado) {
			System.err.println("El cliente con ese ID no existe");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error, No se encontró el cliente");

		}
		/**
	     * Orquesta el proceso de eliminación de un cliente.
	     * Llama al repositorio para realizar el borrado. Si el repositorio indica 
	     * que no se borró nada (false), asume que el cliente no existe y lanza 
	     * una excepción para interrumpir el flujo.
	     *
	     * @param id El identificador del cliente.
	     * @throws ResponseStatusException si el cliente con ese ID no se encuentra (404).
	     */
	}

	// TODO: valorar la programación del método privado validarCifUnico mirar el
	// Diagrama de Clases

}
