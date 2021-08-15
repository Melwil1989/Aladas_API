package ar.com.ada.api.aladas;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.ada.api.aladas.entities.*;
import ar.com.ada.api.aladas.entities.Vuelo.EstadoVueloEnum;
import ar.com.ada.api.aladas.security.Crypto;
import ar.com.ada.api.aladas.services.*;
import ar.com.ada.api.aladas.services.AeropuertoService.ValidacionAeropuertoDataEnum;
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

	@Test
	void testearEncriptacion() {

		String contraseñaImaginaria = "pitufosasesinos";
		String contraseñaImaginariaEncriptada = Crypto.encrypt(contraseñaImaginaria, "palabra");

		String contraseñaImaginariaEncriptadaDesencriptada = Crypto.decrypt(contraseñaImaginariaEncriptada, "palabra");

		// assertTrue(contraseñaImaginariaEncriptadaDesencriptada.equals(contraseñaImaginaria));
		assertEquals(contraseñaImaginariaEncriptadaDesencriptada, contraseñaImaginaria);
	}

	@Test
	void testearContraseña() {
		Usuario usuario = new Usuario();

		usuario.setUsername("Diana@gmail.com");
		usuario.setPassword("qp5TPhgUtIf7RDylefkIbw==");
		usuario.setEmail("Diana@gmail.com");

		assertFalse(!usuario.getPassword().equals(Crypto.encrypt("AbcdE23", usuario.getUsername().toLowerCase())));

	}

	@Test
	void testearAeropuertoId(){
		Aeropuerto aeropuerto = new Aeropuerto();
		aeropuerto.setAeropuertoId(117);
		aeropuerto.setCodigoIATA("MDZ");
		aeropuerto.setNombre("Mendoza");

		assertEquals(ValidacionAeropuertoDataEnum.ERROR_AEROPUERTO_YA_EXISTE, aeropuertoService.validar(aeropuerto));
	}


	@Test
	void testearAeropuertoCodigoIATA(){
		Aeropuerto aeropuerto = new Aeropuerto();
		aeropuerto.setAeropuertoId(17);
		aeropuerto.setCodigoIATA("  M");
		aeropuerto.setNombre("Mendoza");	

		assertEquals(ValidacionAeropuertoDataEnum.ERROR_CODIGO_IATA, aeropuertoService.validar(aeropuerto));
	}

}
