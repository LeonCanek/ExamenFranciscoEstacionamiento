package com.mx.estacionamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.estacionamiento.dao.VehiculoRepository;
import com.mx.estacionamiento.dominio.Vehiculo;
import com.mx.estacionamiento.service.ImplementacionVehiculo;

@RestController
@RequestMapping(path = "api")
@CrossOrigin
public class VehiculoController {

	@Autowired
	ImplementacionVehiculo impVehiculo;

	// http://localhost:9006/api/getAllVehiculos
	@GetMapping(value = "getAllVehiculos")
	public ResponseEntity<?> getAllVehiculos() {
		return ResponseEntity.status(HttpStatus.CREATED).body(impVehiculo.getAllVehiculos());
	}

	// http://localhost:9006/api/createVehiculo
	@PostMapping(value = "createVehiculo")
	public ResponseEntity<?> createVehiculo(@RequestBody Vehiculo vehiculo) {
		impVehiculo.createVehiculo(vehiculo);
		return new ResponseEntity<String>("Create:201", HttpStatus.OK);
	}

	// http://localhost:9006/api/getVehiculo/
	@GetMapping("getVehiculo/{placa}")
	public ResponseEntity<?> getVehiculo(@PathVariable("placa") String placa) {
		return ResponseEntity.status(HttpStatus.CREATED).body(impVehiculo.getVehiculoByPlaca(placa));
	}

	// http://localhost:9006/api/alta/oficial/
	@PostMapping("/alta/oficial/{placa}")
	public ResponseEntity<?> altaOficial(@PathVariable("placa") String placa) {
		impVehiculo.altaVehiculo(placa, "OFICIAL");
		return ResponseEntity.ok("Vehículo oficial dado de alta");
	}
	// http://localhost:9006/api/alta/residente/
	@PostMapping("/alta/residente/{placa}")
	public ResponseEntity<?> altaResidente(@PathVariable("placa") String placa) {
		impVehiculo.altaVehiculo(placa, "RESIDENTE");
		return ResponseEntity.ok("Vehículo residente dado de alta");
	}

}
