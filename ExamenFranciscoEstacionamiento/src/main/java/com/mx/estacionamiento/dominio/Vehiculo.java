package com.mx.estacionamiento.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Vehiculo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vehiculo {

	@Id
	private String placa;
	private String tipoVehiculo;
	long minutosAcumulados = 0;

	public String getPlaca() {
		return placa;
	}

	public long getMinutosAcumulados() {
		return minutosAcumulados;
	}

	public void setMinutosAcumulados(long minutosAcumulados) {
		this.minutosAcumulados = minutosAcumulados;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
}
