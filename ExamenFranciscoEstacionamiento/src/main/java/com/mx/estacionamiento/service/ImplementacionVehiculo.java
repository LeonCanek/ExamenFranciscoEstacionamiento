package com.mx.estacionamiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.estacionamiento.dao.VehiculoRepository;
import com.mx.estacionamiento.dominio.Vehiculo;

@Service
public class ImplementacionVehiculo implements VehiculoInterface {

	@Autowired
	VehiculoRepository vehiculoRepository;

	@Override
	public List<Vehiculo> getAllVehiculos() {

		return vehiculoRepository.findAll();
	}

	@Override
	public Vehiculo createVehiculo(Vehiculo vehiculo) {
		// TODO Auto-generated method stub
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public Vehiculo getVehiculoByPlaca(String placa) {
		// TODO Auto-generated method stub
		return vehiculoRepository.findById(placa).orElse(null);
	}

	public Vehiculo altaVehiculo(String placa, String tipoVehiculo) {
		if (vehiculoRepository.existsById(placa)) {
			throw new RuntimeException("El vehículo con placa " + placa + " ya existe.");
		}

		Vehiculo vehiculo = new Vehiculo();
		vehiculo.setPlaca(placa);
		vehiculo.setTipoVehiculo(tipoVehiculo);

		if ("RESIDENTE".equalsIgnoreCase(tipoVehiculo)) {
			vehiculo.setMinutosAcumulados(0);
		}

		return vehiculoRepository.save(vehiculo);
	}

}
