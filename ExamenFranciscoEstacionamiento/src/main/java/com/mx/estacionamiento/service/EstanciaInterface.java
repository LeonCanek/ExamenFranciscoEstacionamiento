package com.mx.estacionamiento.service;

import java.util.List;

import com.mx.estacionamiento.dominio.Estancia;

public interface EstanciaInterface {
	
    public Estancia createEstancia(Estancia estancia);
    public Estancia getEstanciaById(Long id);    
    public List<Estancia> getAllEstancias();
    public void registrarEntrada(String placa);

}
