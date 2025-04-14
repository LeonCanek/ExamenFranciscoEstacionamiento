package com.mx.estacionamiento.service;

import java.util.List;

import com.mx.estacionamiento.dominio.Vehiculo;

public interface VehiculoInterface {

	public List<Vehiculo> getAllVehiculos();

	public Vehiculo createVehiculo(Vehiculo vehiculo);

	public Vehiculo getVehiculoByPlaca(String placa);

}
