package com.mx.estacionamiento.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mx.estacionamiento.dominio.Estancia;
import com.mx.estacionamiento.dominio.Vehiculo;

import jakarta.transaction.Transactional;

@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {

	@Query("SELECT e FROM Estancia e WHERE e.vehiculo.placa = :placa AND e.horaSalida IS NULL ORDER BY e.horaEntrada DESC")
	List<Estancia> findEstanciasAbiertas(@Param("placa") String placa);

	default Estancia findUltimaEstanciaAbierta(String placa) {
		List<Estancia> estancias = findEstanciasAbiertas(placa);
		return estancias.isEmpty() ? null : estancias.get(0);
	}

	@Transactional
	void deleteByVehiculo(Vehiculo vehiculo);

	@Query("SELECT e FROM Estancia e WHERE e.vehiculo.tipoVehiculo = 'RESIDENTE'")
	List<Estancia> findEstanciasResidentes();

}