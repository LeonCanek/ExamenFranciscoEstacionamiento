package com.mx.estacionamiento.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mx.estacionamiento.dominio.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {

	Vehiculo findByPlaca(String placa);

	List<Vehiculo> findByTipoVehiculo(String tipoVehiculo);

}
