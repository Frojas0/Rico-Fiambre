package com.ricofiambre.ecomerce;

import com.ricofiambre.ecomerce.modelos.*;
import com.ricofiambre.ecomerce.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class RicoFiambreApplication {

	public static void main(String[] args) {
		SpringApplication.run(RicoFiambreApplication.class, args);
	}
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Bean
	public CommandLineRunner initData(ClienteRepositorio clienteRepositorio, OrdenRepositorio ordenRepositorio, ProductoPesoRepositorio productoPesoRepositorio, ProductoUniRepositorio productoUniRepositorio, TicketRepositorio ticketRepositorio, OrdenProductoPesoRepositorio ordenProductoPesoRepositorio, OrdenProductoUniRepositorio ordenProductoUniRepositorio) {
		return (args) -> {
			//CREACION DE CLIENTES
			Cliente cliente01 = (new Cliente("Juanjo", "Sepaso","juanjo@mail.com","Av. Siempreviva 123", "Springfield", 2400, "2604-112233", passwordEncoder.encode("Juanjo123")));
			Cliente cliente02 = (new Cliente("Melba", "Morel","melba@mindhub.com","Av. Nuncaviva 321", "Shelbyville", 5600, "2604-112247", passwordEncoder.encode("Melba123")));
			Cliente admin01 = (new Cliente("admin", "istrador","admin@admin.com","Av. Todo Permitido 322", "Usa", 5600, "2604-312123", passwordEncoder.encode("Admin123")));

			//CREACION DE PRODUCTO POR PESO
			ProductoPeso productoPeso01 = (new ProductoPeso("QUESO AZUL", TipoProducto.LACTEO,"Queso que tienen en su pasta cultivos de Penicillium añadidos al producto final",10.0,4800.0, PaisProducto.EUROPEO, 9.0));

			//CREACION DE PRODUCTO POR UNIDAD
			ProductoUni productoUni01 = (new ProductoUni("SALAMIN PURO", TipoProducto.EMBUTIDO, "Salame de cerdo", 25, 1000, PaisProducto.ARGENTINA, 7.2));

			//CREACION DE ORDEN
			Orden orden01 = (new Orden(LocalDateTime.now(),false,true,2000, "00000001"));
			cliente01.addOrden(orden01);
			Orden orden02 = (new Orden(LocalDateTime.now(), false, true, 14400, "00000002"));
			cliente02.addOrden(orden02);

			//CREACION DE ORDENPRODUCTOUNI
			OrdenProductoUni ordenProductoUni01 = (new OrdenProductoUni(2,2000));
			productoUni01.setStock(productoUni01.getStock()-2);
			//asignacion a orden
			orden01.addOrdenProductoUni(ordenProductoUni01);
			//asignacion a producto
			productoUni01.addOrdenProductoUni(ordenProductoUni01);

			//CREACION DE ORDENPRODUCTOPESO
			OrdenProductoPeso ordenProductoPeso01 = (new OrdenProductoPeso(3,14400));
			productoPeso01.setStock(productoPeso01.getStock()-3);
			//asignacion a orden
			orden02.addOrdenProductoPeso(ordenProductoPeso01);
			//asignacion a producto
			productoPeso01.addOrdenProductoPeso(ordenProductoPeso01);


			//GUARDADO DE DATOS
			//cliente
			clienteRepositorio.save(cliente01);
			clienteRepositorio.save(cliente02);
			clienteRepositorio.save(admin01);

			//orden
			ordenRepositorio.save(orden01);
			ordenRepositorio.save(orden02);

			//productos
			productoPesoRepositorio.save(productoPeso01);
			productoUniRepositorio.save(productoUni01);

			//orden producto
			ordenProductoPesoRepositorio.save(ordenProductoPeso01);
			ordenProductoUniRepositorio.save(ordenProductoUni01);


			//CREACION DE TICKET
			Ticket ticket01 = (new Ticket("00012345"));
			Ticket ticket02 = (new Ticket("00152321"));
			//asignacion a orden
			ticket01.addOrden(orden01);
			ticket02.addOrden(orden02);
			//ticket
			ticketRepositorio.save(ticket01);
			ticketRepositorio.save(ticket02);
		};
	}
}
