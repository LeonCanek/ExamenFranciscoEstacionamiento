package com.mx.estacionamiento.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Estancia")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Estancia {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estancia_seq")
	@SequenceGenerator(name = "estancia_seq", sequenceName = "ESTANCIA_SEQ", allocationSize = 1)
	private Long id;
	@ManyToOne
	private Vehiculo vehiculo;
	@Column(name = "hora_entrada")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Calendar horaEntrada;
	@Column(name = "hora_salida")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Calendar horaSalida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Calendar getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(Calendar horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public Calendar getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Calendar horaSalida) {
		this.horaSalida = horaSalida;
	}
}
