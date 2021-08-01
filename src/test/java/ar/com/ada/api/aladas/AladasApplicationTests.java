package ar.com.ada.api.aladas;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.Aeropuerto;
import ar.com.ada.api.aladas.entities.Vuelo;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.services.AeropuertoService;
import ar.com.ada.api.aladas.services.VueloService;
import ar.com.ada.api.aladas.services.VueloService.ValidacionVueloDataEnum;

@SpringBootTest
class AladasApplicationTests {

	@Autowired
	VueloService vueloService;

	@Autowired
	AeropuertoService aeropuertoService;

	@Test
	void vueloTestPrecioNegativo() {

		Vuelo vueloConPrecioNegativo = new Vuelo();
		vueloConPrecioNegativo.setPrecio(new BigDecimal(-100));

		assertFalse(vueloService.validarPrecio(vueloConPrecioNegativo));
	}

	@Test
	void vueloTestPrecioOk() {

		Vuelo vueloConPrecioOk = new Vuelo();
		vueloConPrecioOk.setPrecio(new BigDecimal(100));

		assertTrue(vueloService.validarPrecio(vueloConPrecioOk));
	}

	void aeropuertoValidarCodigoIATAOk() {
		
		String codigoIATAOk1 = "EZE";
		String codigoIATAOk2 = "AEP";
		String codigoIATAOk3 = "NQN";
		String codigoIATAOk4 = "N  ";
		String codigoIATAOk5 = "N39";

		/*//String codigoIATAOk4 = "N  ";

		//En este caso, afirmo que espero que el length del codigoIATAOk1 sea 3
		assertEquals(3, codigoIATAOk1.length());

		//En este caso, afirmo que espero que el resultado de la condicion
		//sea verdadero(en este caso, lenght == 3)
		assertTrue(codigoIATAOk2.length() == 3);

		//assertTrue(codigoIATAOk4.length() == 3);*/

		Aeropuerto aeropuerto1 = new Aeropuerto();
		aeropuerto1.setCodigoIATA(codigoIATAOk1);

		Aeropuerto aeropuerto2 = new Aeropuerto();
		aeropuerto2.setCodigoIATA(codigoIATAOk2);

		Aeropuerto aeropuerto3 = new Aeropuerto();
		aeropuerto3.setCodigoIATA(codigoIATAOk3);

		Aeropuerto aeropuerto4 = new Aeropuerto();
		aeropuerto4.setCodigoIATA(codigoIATAOk4);

		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto1));
		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto2));
		assertTrue(aeropuertoService.validarCodigoIATA(aeropuerto3));

		assertFalse(aeropuertoService.validarCodigoIATA(aeropuerto4));
	}

	@Test
	void aeropuertoValidarCodigoIATANoOk() {

	}

	@Test
	void vueloVerificarValidacionAeropuertoOrigenDestino() {

	}

	@Test
	void vueloChequearQueLosPendientesNoTenganVuelosViejos() {

	}

	@Test
	void vueloVerificarCapacidadMinima() {

	}

	@Test
	void vueloVerificarCapacidadMaxima() {

	}

	@Test
	void aeropuertoTestBuscadorIATA() {

	}

	@Test
	void vueloValidarVueloMismoDestinoUsandoGeneral() {

		Vuelo vuelo = new Vuelo();

		vuelo.setPrecio(new BigDecimal(1000));
		vuelo.setEstadoVueloId(EstadoVueloEnum.GENERADO);
		vuelo.setAeropuertoOrigen(116);
		vuelo.setAeropuertoDestino(116);

		assertEquals(ValidacionVueloDataEnum.ERROR_AEROPUERTOS_IGUALES, vueloService.validar(vuelo));

	}

}
