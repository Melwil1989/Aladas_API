package ar.com.ada.api.aladas.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.aladas.entities.*;
import ar.com.ada.api.aladas.entities.Reserva.EstadoReservaEnum;
import ar.com.ada.api.aladas.repos.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    ReservaRepository repo;

    public void crear(Integer reservaId, Vuelo vuelo, Pasajero pasajero, EstadoReservaEnum estadoReservaId,
            Date fechaEmision, Date fechaVencimiento) {

        Reserva reserva = new Reserva();

        reserva.setReservaId(reservaId);
        reserva.setVuelo(vuelo);
        reserva.setPasajero(pasajero);
        reserva.setEstadoReservaId(estadoReservaId);
        reserva.setFechaEmision(fechaEmision);
        reserva.setFechaVencimiento(fechaVencimiento);

        repo.save(reserva);
    }
    
}
