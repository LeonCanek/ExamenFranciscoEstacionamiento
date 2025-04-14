package com.mx.estacionamiento.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.estacionamiento.dao.EstanciaRepository;
import com.mx.estacionamiento.dao.VehiculoRepository;
import com.mx.estacionamiento.dominio.Estancia;
import com.mx.estacionamiento.dominio.Vehiculo;

import jakarta.transaction.Transactional;

@Service
public class ImplementacionEstancia implements EstanciaInterface {

	@Autowired
	EstanciaRepository estanciaRepository;

	@Autowired
	VehiculoRepository vehiculoRepository;

	@Override
	public Estancia createEstancia(Estancia estancia) {
		return estanciaRepository.save(estancia);
	}
	
    public void eliminarEstancia(long id) {
        estanciaRepository.deleteById(id);
    }

	@Override
	public Estancia getEstanciaById(Long id) {
		return estanciaRepository.findById(id).orElse(null);
	}

	@Override
	public List<Estancia> getAllEstancias() {
		return estanciaRepository.findAll();
	}

	public double calcularCobro(Estancia estancia) {
		if (estancia.getHoraEntrada() == null || estancia.getHoraSalida() == null) {
			throw new IllegalArgumentException("Faltan datos de entrada o salida");
		}

		long minutos = calcularMinutos(estancia.getHoraEntrada(), estancia.getHoraSalida());
		String tipoVehiculo = estancia.getVehiculo().getTipoVehiculo();

		TarifaStrategy estrategia = obtenerEstrategia(tipoVehiculo);
		return estrategia.calcularCobro(minutos);
	}

	private TarifaStrategy obtenerEstrategia(String tipoVehiculo) {
		switch (tipoVehiculo.toUpperCase()) {
		case "OFICIAL":
			return new TarifaOficial();
		case "RESIDENTE":
			return new TarifaResidente();
		case "NO_RESIDENTE":
			return new TarifaNoResidente();
		default:
			throw new IllegalArgumentException("Tipo de vehículo no soportado: " + tipoVehiculo);
		}
	}

	private long calcularMinutos(Calendar entrada, Calendar salida) {
		return (salida.getTimeInMillis() - entrada.getTimeInMillis()) / (60 * 1000);
	}

	@Override
	public void registrarEntrada(String placa) {
		Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa);
		if (vehiculo == null)
			throw new RuntimeException("Vehículo no encontrado");

		Estancia estancia = new Estancia();
		estancia.setVehiculo(vehiculo);
		estancia.setHoraEntrada(Calendar.getInstance());
		estanciaRepository.save(estancia);
	}

	public double registrarSalida(String placa) {
		Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa);
		if (vehiculo == null)
			throw new RuntimeException("Vehículo no encontrado");

		Estancia estancia = estanciaRepository.findUltimaEstanciaAbierta(vehiculo.getPlaca());
		if (estancia == null)
			throw new RuntimeException("No hay estancia abierta");

		estancia.setHoraSalida(Calendar.getInstance());
		estanciaRepository.save(estancia);

		long minutos = calcularMinutos(estancia.getHoraEntrada(), estancia.getHoraSalida());

		switch (vehiculo.getTipoVehiculo()) {
		case "OFICIAL":
			return 0.0;
		case "RESIDENTE":
			vehiculo.setMinutosAcumulados(vehiculo.getMinutosAcumulados() + minutos);
			vehiculoRepository.save(vehiculo);
			return 0.0;
		case "NO_RESIDENTE":
			return minutos * 0.5;
		default:
			throw new RuntimeException("Tipo de vehículo no reconocido");
		}
	}

	public void comenzarMes() {
		List<Vehiculo> vehiculos = vehiculoRepository.findAll();

		for (Vehiculo v : vehiculos) {
			if (v.getTipoVehiculo().equals("OFICIAL")) {
				estanciaRepository.deleteByVehiculo(v);
			} else if (v.getTipoVehiculo().equals("RESIDENTE")) {
				v.setMinutosAcumulados(0L);
				vehiculoRepository.save(v);
			}
		}
	}

	public void generarInformeResidentes(String nombreArchivo) throws IOException {
	    List<Vehiculo> residentes = vehiculoRepository.findByTipoVehiculo("RESIDENTE");

	    BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo));
	    writer.write("Núm. placa\tTiempo estacionado (min.)\tCantidad a pagar\n");

	    for (Vehiculo v : residentes) {
	        long minutosAcumulados = v.getMinutosAcumulados();

	        Estancia estanciaAbierta = estanciaRepository.findUltimaEstanciaAbierta(v.getPlaca());
	        if (estanciaAbierta != null && estanciaAbierta.getHoraEntrada() != null) {
	            long minutosEnCurso = (Calendar.getInstance().getTimeInMillis() - estanciaAbierta.getHoraEntrada().getTimeInMillis()) / (60 * 1000);
	            minutosAcumulados += minutosEnCurso;
	        }

	        double total = minutosAcumulados * 0.05;
	        writer.write(v.getPlaca() + "\t" + minutosAcumulados + "\t" + String.format("%.2f", total) + "\n");
	    }

	    writer.close();
	}
}