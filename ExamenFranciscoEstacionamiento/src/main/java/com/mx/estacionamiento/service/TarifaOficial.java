package com.mx.estacionamiento.service;

public class TarifaOficial implements TarifaStrategy {
	@Override
	public double calcularCobro(long minutos) {
		return 0.0;
	}
}
