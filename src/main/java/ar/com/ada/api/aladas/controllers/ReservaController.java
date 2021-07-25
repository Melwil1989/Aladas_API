package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Reserva;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.ReservaService;

@RestController
public class ReservaController {

    @Autowired
    ReservaService service;

    @PostMapping("/api/reservas")
    public ResponseEntity<GenericResponse> crear(@RequestBody Reserva reserva) {

        GenericResponse respuesta = new GenericResponse();

        service.crear(reserva.getReservaId(), reserva.getVuelo(), reserva.getPasajero(), reserva.getEstadoReservaId(), 
        reserva.getFechaEmision(), reserva.getFechaVencimiento());

        respuesta.isOk = true;
        respuesta.message = "La reserva fue creada correctamente";
        respuesta.id = reserva.getReservaId();

        return ResponseEntity.ok(respuesta);
        
    }
    
}
