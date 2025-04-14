package com.mx.estacionamiento.service;

public class TarifaResidente implements TarifaStrategy {
	@Override
	public double calcularCobro(long minutos) {
		return minutos * 0.05;
	}
}
