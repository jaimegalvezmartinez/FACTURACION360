package edu.xtd.facturacion360.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.xtd.facturacion360.dto.Cliente;
import edu.xtd.facturacion360.dto.ClienteMapper;
import edu.xtd.facturacion360.dto.ClienteRequest;
import edu.xtd.facturacion360.dto.ClienteResponse;
import edu.xtd.facturacion360.service.ClienteService;
import jakarta.validation.Valid;

/**
 * Recibe las peticiones HTTP relativas a los clientes y devuelve su respuesta.
 * 
 * MÉTODO HTTP - OPERACIÓN LÓGICA - OPERACIÓN SQL
 * 
 * GET - LEER - SELECT
 * POST - CREAR - INSERT
 * PUT - MODIFICAR - UPDATE
 * DELETE - BORRAR - DELETE
 * 
 * 
 */

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	ClienteService clienteService;

	ClienteMapper clienteMapper = new ClienteMapper();

	Logger logger = LoggerFactory.getLogger(ClienteController.class);

	@GetMapping("/listar-ultimos")
	public ResponseEntity<List<ClienteResponse>> listarUltimos() {

		ResponseEntity<List<ClienteResponse>> respuesta = null;

		return respuesta;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponse> obtenerPorId(@PathVariable int id) {

		ResponseEntity<ClienteResponse> respuesta = null;

		return respuesta;
	}

	/**
	 * Crea un cliente a partir de los datos recibidos.
	 * ClienteResponse contiene los datos que se devuelven en la respuesta HTTP.
	 *
	 * @Valid indica que se debe validar el objeto recibido según las anotaciones de
	 *        validación definidas en la clase ClienteRequest.
	 * @RequestBody indica que el objeto ClienteRequest se debe obtener del cuerpo
	 *              de la petición HTTP.
	 *              ClienteRequest contiene los datos recibidos en la petición HTTP.
	 *              BindingResult contiene el resultado de la validación, incluyendo
	 *              errores si los hubiera.
	 *
	 * Devuelve 201 si se crea el cliente, 400 si hay errores de validación y 500 si
	 * no se consigue guardar.
	 */
	@PostMapping
	public ResponseEntity<ClienteResponse> crear(@Valid @RequestBody ClienteRequest clienteRequest,
			BindingResult bindingResult) {
		ResponseEntity<ClienteResponse> respuesta;
			
		if (bindingResult.hasErrors()) {
			logger.error("Cliente recibido con errores");
			respuesta = ResponseEntity.badRequest().build();
		} else {
			try {
				logger.debug("Cliente sin errores de validación");
				Cliente cliente = clienteMapper.toDomain(clienteRequest);
				boolean clienteCreado = clienteService.crear(cliente);
			
				if (clienteCreado) {
					logger.debug("Cliente creado correctamente");
					respuesta = ResponseEntity.status(HttpStatus.CREATED).build();
				} else {
					logger.error("Fallo creando cliente");
					respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			} catch (Exception e) {
				logger.error("Excepción creando cliente", e);
				respuesta = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	
		return respuesta;
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteResponse> actualizar(@PathVariable int id,
			@Valid @RequestBody ClienteRequest clienteRequest, BindingResult bindingResult) {
		ResponseEntity<ClienteResponse> respuesta = null;

		return respuesta;
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable int id) {
		ResponseEntity<Void> respuesta = null;

		return respuesta;
	}

}
