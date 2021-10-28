package ar.com.ada.api.aladas.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.models.request.EstadoVueloRequest;
import ar.com.ada.api.aladas.models.response.GenericResponse;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@RestController
public class VueloController {

    /*version simple para declarar un VueloService

    @Autowired
    VueloService service;

    */

    // version PRO 
    private VueloService service;

    private AeropuertoService aeropuertoService;

    public VueloController(VueloService service, AeropuertoService aeropuertoService) {
        this.service = service;
        this.aeropuertoService = aeropuertoService;
    }

    @PostMapping("/api/vuelos")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<GenericResponse> postCrearVuelo(@RequestBody Vuelo vuelo) {

        GenericResponse respuesta = new GenericResponse();

        ValidacionVueloDataEnum resultadoValidacion = service.validar(vuelo);

        if(resultadoValidacion == ValidacionVueloDataEnum.OK) {

            service.crear(vuelo);

            respuesta.isOk = true;
            respuesta.id = vuelo.getVueloId();
            respuesta.message = "Vuelo creado correctamente";

            return ResponseEntity.ok(respuesta);

        } else {

            respuesta.isOk = false;
            respuesta.message = "Error(" + resultadoValidacion.toString() + ")";

            return ResponseEntity.badRequest().body(respuesta);
        }

    }

    /* @PostMapping("/api/v2/vuelos")
    public ResponseEntity<GenericResponse> postCrearVueloV2(@RequestBody Vuelo vuelo) {

        GenericResponse respuesta = new GenericResponse();

        //Aeropuerto ao = aeropuertoService;

        Vuelo vueloCreado = service.crear(vuelo.getFecha(), vuelo.getCapacidad(), vuelo.getAeropuertoOrigen(),
        vuelo.getAeropuertoDestino(), vuelo.getPrecio(), vuelo.getCodigoMoneda());

        respuesta.isOk = true;
        respuesta.id = vueloCreado.getVueloId();
        respuesta.message = "Vuelo creado correctamente";

        return ResponseEntity.ok(respuesta);

    } */

    @PutMapping("/api/vuelos/{id}/estados")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<GenericResponse> putActualizarEstadoVuelo(@PathVariable Integer id,
        @RequestBody EstadoVueloRequest estadoVuelo) {

        GenericResponse r = new GenericResponse();
        r.isOk = true;
        // Pasos:
        // 1 Buscar un vuelo por id y lo asignamos a una variable (vuelo)
        Vuelo vuelo = service.buscarPorId(id);
        // 2 Setearle el nuevo estado, que vino en estadoVuelo al vuelo
        vuelo.setEstadoVueloId(estadoVuelo.estado); 
        // 3 Grabar el vuelo en la base de datos 
        service.actualizar(vuelo);
        // 4 Que devuelva el status final
        r.message = "Actualizado";
        return ResponseEntity.ok(r);
    }

    @GetMapping("/api/vuelos/abiertos")
    public ResponseEntity<List<Vuelo>> getVuelosAbiertos() {
        
        return ResponseEntity.ok(service.traerVuelosAbiertos());
    }

    @GetMapping("/api/vuelos/{id}")
    //@PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<?> getVueloPorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorId(id)) {

            return ResponseEntity.ok(service.buscarPorId(id));

        } else {

            respuesta.isOk = false;
            respuesta.message = "El vuelo no existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("api/vuelos/aeropuertos/{id}")
    public ResponseEntity<List<Vuelo>> getVueloPorOrigen(@PathVariable Integer id) {

        List<Vuelo> vuelo = service.buscarOrigen(id);
        return ResponseEntity.ok(vuelo);
    }

    @GetMapping("api/vuelos/aeropuertosV2/{id}")
    public ResponseEntity<List<Vuelo>> getVueloPorDestino(@PathVariable Integer id) {

        List<Vuelo> vuelo = service.buscarDestino(id);
        return ResponseEntity.ok(vuelo);
    }

    @GetMapping("api/vuelos/{id}/estadov2")
    //@PreAuthorize("hasAuthority('CLAIM_userType_STAFF')") //Spring expression language
    public ResponseEntity<?> getEstadoVueloV2(@PathVariable Integer id) {
      
        Vuelo vuelo = service.buscarPorId(id);
        return ResponseEntity.ok(vuelo.getEstadoVueloId());
    }
    
}
