package com.ricofiambre.ecomerce;

import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.repositorios.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RicoFiambreApplication {

	public static void main(String[] args) {
		SpringApplication.run(RicoFiambreApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClienteRepositorio clienteRepositorio, OrdenRepositorio ordenRepositorio, ProductoPesoRepositorio productoPesoRepositorio, ProductoUniRepositorio productoUniRepositorio, TicketRepositorio ticketRepositorio) {
		return (args) -> {
			//CREACION DE CLIENTES
			Cliente cliente01 = (new Cliente("Juanjo", "Sepaso","juanjo@mail.com","Av. Siempreviva", "Springfield", 2400, "2604-112233"));
			clienteRepositorio.save(cliente01);
			//CREACION DE PRODUCTO POR PESO

			//CREACION DE PRODUCTO POR UNIDAD


		};
	}
}
