package com.mx.estacionamiento.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.estacionamiento.dao.EstanciaRepository;
import com.mx.estacionamiento.dominio.Estancia;
import com.mx.estacionamiento.service.ImplementacionEstancia;

@RestController
@RequestMapping(path = "/api/")
@CrossOrigin
public class EstanciaController {

	@Autowired
	ImplementacionEstancia impEstancia;

	// http://localhost:9006/api/estancias
	@GetMapping(value = "estancias")
	public ResponseEntity<?> getAllEstancias() {
		return ResponseEntity.status(HttpStatus.CREATED).body(impEstancia.getAllEstancias());
	}

	// http://localhost:9006/api/estancias
	@DeleteMapping(value = "estancias/{id}")
	public ResponseEntity<?> deleteEstancias(@PathVariable("id") long id) {
		impEstancia.eliminarEstancia(id);
		return new ResponseEntity<String>("Delete:204", HttpStatus.OK);
	}

	// http://localhost:9006/api/estancias
	@PostMapping(value = "estancias")
	public ResponseEntity<?> createEstancia(@RequestBody Estancia estancia) {
		impEstancia.createEstancia(estancia);
		return new ResponseEntity<String>("Create:201", HttpStatus.OK);
	}

	// http://localhost:9006/api/getEstancia/
	@GetMapping("getEstancia/{id}")
	public ResponseEntity<?> getEstancia(@PathVariable("id") Long id) {
		return ResponseEntity.status(HttpStatus.CREATED).body(impEstancia.getEstanciaById(id));
	}

	// http://localhost:9006/api/entrada/
	@PostMapping("/registra-entrada/{placa}")
	public ResponseEntity<?> registrarEntrada(@PathVariable("placa") String placa) {
		impEstancia.registrarEntrada(placa);
		return ResponseEntity.ok("Entrada registrada para vehículo con placa: " + placa);
	}

	// http://localhost:9006/EstanciaController/salida/
	@PostMapping(value = "/registra-salida/{placa}")
	public ResponseEntity<?> registrarSalida(@PathVariable("placa") String placa) {
		double cobro = impEstancia.registrarSalida(placa);
		return ResponseEntity.ok("Salida registrada. Importe a pagar: $" + cobro);
	}

	// http://localhost:9006/api/comienza-mes/
	@PostMapping("/comienza-mes")
	public ResponseEntity<?> comenzarMes() {
		impEstancia.comenzarMes();
		return ResponseEntity.ok("Mes reiniciado");
	}

	// http://localhost:9006/api/pagos/residentes
	@PostMapping("/pagos/residentes")
	public ResponseEntity<?> generarInforme(@RequestParam("nombreArchivo") String nombreArchivo) {
		try {
			impEstancia.generarInformeResidentes(nombreArchivo);
			return ResponseEntity.ok("Informe generado en: " + nombreArchivo);
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Error al generar informe: " + e.getMessage());
		}
	}

}
