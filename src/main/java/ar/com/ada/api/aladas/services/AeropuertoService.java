package ar.com.ada.api.aladas.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.repos.AeropuertoRepository;

@Service
public class AeropuertoService {
    
    @Autowired
    AeropuertoRepository repo;

    public void crear(Integer aeropuertoId, String nombre, String codigoIATA) { // en este caso al metodo crear() hay que pasarle 
                                                                                // como parametro el aeropuertoId porque no es AI
        Aeropuerto aeropuerto = new Aeropuerto();

        aeropuerto.setAeropuertoId(aeropuertoId);
        aeropuerto.setNombre(nombre);
        aeropuerto.setCodigoIATA(codigoIATA);

        repo.save(aeropuerto);
        
    }

    public List<Aeropuerto> obtenerTodos() {

        return repo.findAll();
    }
}
