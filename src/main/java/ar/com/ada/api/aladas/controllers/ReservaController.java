package ar.com.ada.api.aladas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Usuario;
import ar.com.ada.api.aladas.models.request.InfoReservaNueva;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.*;

@RestController
public class ReservaController {

    @Autowired
    ReservaService service;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/api/reservas")
    public ResponseEntity<GenericResponse> generarReserva(@RequestBody InfoReservaNueva infoReserva) {

        GenericResponse respuesta = new GenericResponse();

        //obtengo a quien esta autenticado del otro lado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //de lo que esta autenticado obtengo su USERNAME
        String username = authentication.getName();
        //buscar el usuario por username
        Usuario usuario = usuarioService.buscarPorUsername(username);

        //con el usuario obtengo el pasajero, y con ese, obtengo el Id
        Integer numeroReserva = service.generarReserva(infoReserva.vueloId, usuario.getPasajero().getPasajeroId());

        respuesta.isOk = true;
        respuesta.message = "La reserva fue creada correctamente";
        respuesta.id = numeroReserva;

        return ResponseEntity.ok(respuesta);       
    }
    
}
