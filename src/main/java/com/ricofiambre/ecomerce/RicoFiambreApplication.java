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
			ProductoPeso productoPeso01 = (new ProductoPeso("QUESO AZUL", TipoProducto.LACTEO,"Queso que tienen en su pasta cultivos de Penicillium añadidos al producto final",10.0,4800.0, PaisProducto.EUROPEO, 9.0,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-azul-estilo-frances1-6aeaff98b29d94b88516316352988110-1024-1024.jpg"));

			//CREACION DE PRODUCTO POR UNIDAD
			ProductoUni productoUni01 = (new ProductoUni("SALAMIN PURO", TipoProducto.EMBUTIDO, "Salame de cerdo", 25, 1000, PaisProducto.NACIONAL, 7.2,"http://www.lafrancisca.com/public/images/productos/thumbs/16-salamin-picado-grueso-20220830221302.jpg"));

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

			//CREACION DE PRODUCTO POR PESO
			ProductoPeso productoPeso02 =(new ProductoPeso("JAMON COCIDO NATURAL",TipoProducto.ENCURTIDO,"Se obtiene de la pata trasera del cerdo curada y cocida. Se elabora tratando a las patas de cerdo, deshuesadas y limpias, con salmuera, solución de agua, sal y conservantes (nitrito de sodio o de potasio).",50.0,2310.0,PaisProducto.EUROPEO , 6,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-jamon-cocido-natural11-4dce97ebbc458688f316305865267092-1024-1024.jpg"));
			ProductoPeso productoPeso03 =(new ProductoPeso("JAMON CRUDO",TipoProducto.ENCURTIDO ,"Jamón Crudo nacional realizado a partir de recetas Italianas, estacionado entre 12 y 18 meses.",15.0,3510.0,PaisProducto.NACIONAL,7,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/jamon-crudo-tipo-parma-86161-22333ab89cb0113a2016305858751740-1024-1024.jpg"));
			ProductoPeso productoPeso04 =(new ProductoPeso("MORTADELA TIPO ITALIANA CON PISTACHOS",TipoProducto.ENCURTIDO ,"Elaborada con carne de cerdo con agregados de trozos de tocino y pistachos los cuales le dan un colorido y aroma especial.",12.0,1170.0,PaisProducto.EUROPEO ,7,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-mortadela-tipo-italiana-con-pistachos1-768c517b1c5541beb916305946591525-1024-1024.jpg"));
			ProductoPeso productoPeso05 =(new ProductoPeso("JAMON CRUDO SERRANO ESPAÑOL",TipoProducto.ENCURTIDO ,"Jamón crudo serrano español, 15 meses estacionamiento.",9.0,5400.0,PaisProducto.EUROPEO ,8.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-jamon-crudo-espanol1-1888a76572b1fc565e16305885450932-640-0.jpg"));
			ProductoPeso productoPeso06 =(new ProductoPeso("PASTRON",TipoProducto.ENCURTIDO ,"Elaborado artesanalmente con la mejor selección de cortes de carne vacuna ahumado.",6.0,1890.0,PaisProducto.NACIONAL ,7.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/pastron1-519ae9710e404eb79016305963070735-640-0.jpg"));
			ProductoPeso productoPeso07 =(new ProductoPeso("LOMO PRAGA",TipoProducto.ENCURTIDO ,"Elaborado con cinta de lomo, cocido en su punto justo, horneado y ahumado en forma tradicional.",9.0,820.0,PaisProducto.NACIONAL ,5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/lomo-praga1-c0bab762c7a012ce1016305936852063-1024-1024.jpg"));
			ProductoPeso productoPeso08 =(new ProductoPeso("SALCHICHA COPETIN",TipoProducto.EMBUTIDO ,"Tradicional salchicha de viena con piel en tamaño pequeño.",12.0,1570.0,PaisProducto.NACIONAL ,7,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-embutidos-salchichas-copetin1-6b88222eb929ab1a2316315489935432-1024-1024.jpg"));
			ProductoPeso productoPeso09 =(new ProductoPeso("SALAME FUET",TipoProducto.ENCURTIDO,"Salame de origen catalán de sabor suave y ligero para el paladar. Embutido estrecho y largo.",50.0,1710.0,PaisProducto.EUROPEO,7.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-fuet1-2f07d056e7c283166a16305914839194-1024-1024.jpg"));
			ProductoPeso productoPeso10 =(new ProductoPeso("LOMO HORNEADO",TipoProducto.ENCURTIDO ,"Carré de cerdo deshuesado y horneado con una terminación dorada y agregado de romero para darle un aroma único.",14.0,1480.0,PaisProducto.NACIONAL ,7.9,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/lomo-horneado1-1e83a0531e00a1990116305937814605-1024-1024.jpg"));
			ProductoPeso productoPeso11 =(new ProductoPeso("JAMON COCIDO ASADO",TipoProducto.ENCURTIDO ,"Jamón natural prensado y terminado a la parrilla lo que le da un toque único y exquisito",10.0,2230.0,PaisProducto.NACIONAL,7,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-jamon-cocido-asado1-832fffc980238996cf16316243033866-1024-1024.jpg"));
			ProductoPeso productoPeso12 =(new ProductoPeso("LEBERWURST",TipoProducto.EMBUTIDO ,"Paté artesanal elaborado con hígado de cerdo, embutido en tripa natural de textura untuosa y sabor delicado.",15.0,810.0, PaisProducto.NACIONAL ,6,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/embutidos-leberwurst11-c5de899c2346ab8d3116315581182055-1024-1024.jpg"));
			ProductoPeso productoPeso13 =(new ProductoPeso("SOPRESATTA TIPO ITALIANA",TipoProducto.ENCURTIDO ,"Salame sobreprensado elaborado de carne vacuna, carne de cerdo y tocino. Condimentada con distintas especias molidas, pimienta negra en grano.",5.0,3080.0,PaisProducto.EUROPEO ,7,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/sopresatta-tipo-italiana-86381-55108b524f8537742716305960837612-1024-1024.jpg"));
			ProductoPeso productoPeso14 =(new ProductoPeso("CHORIZO TIPO ESPAÑOL",TipoProducto.ENCURTIDO ,"Salame puro de cerdo picado grueso condimentado con pimentón dulce español.",15.0,1470.0,PaisProducto.EUROPEO ,8,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/chorizo-tipo-espanol-86501-46ce895363ffdc8f2016305939069785-1024-1024.jpg"));
			ProductoPeso productoPeso15 =(new ProductoPeso("SPIANATTA TIPO ITALIANA",TipoProducto.ENCURTIDO ,"Salame prensado picado grueso, elaborado de carne vacuna, carne de cerdo y tocino. Condimentada con distintas especias molidas, pimienta negra en grano y anis",30.0,950.0,PaisProducto.EUROPEO ,6.8,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/spianatta-tipo-italiana1-170360385f5293c8cb16305962217728-1024-1024.jpg"));
			ProductoPeso productoPeso16 =(new ProductoPeso("SALCHICHA VIENA",TipoProducto.EMBUTIDO ,"Tradicional salchicha de viena con piel.",60.0,1790.0,PaisProducto.EUROPEO ,6.9,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-embutidos-salchicha-vienax41-e24b84bbfed0e2669916315487832788-1024-1024.jpg"));
			ProductoPeso productoPeso17 =(new ProductoPeso("SALAMITO",TipoProducto.ENCURTIDO ,"Salame cordobés picado fino estilo Milán de rodajas pequeñas y un sabor típico de esa región",30.0,1950.0,PaisProducto.NACIONAL ,7.6,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/salamito-86181-687d6569af6800d64b16305897746288-1024-1024.jpg"));
			ProductoPeso productoPeso18 =(new ProductoPeso("PEPPERONI",TipoProducto.ENCURTIDO ,"Salame de carne de vaca y cerdo con pimienta feteado.",100.0,990.0,PaisProducto.NACIONAL ,8,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fiambres-pepperoni1-22f66593a4efd2a7ad16305909316082-1024-1024.jpg"));
			ProductoPeso productoPeso19 =(new ProductoPeso("PROSCIUTTO DI PARMA",TipoProducto.EMBUTIDO ,"amon crudo italiano originario de la region de parma. Se caracteriza por la corona, el sello que viene impreso a fuego solo en las piezas originales. Estacionamiento 18 meses.",10.0,9800.0,PaisProducto.EUROPEO ,8.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/prosciutto-di-parma1-05cd143cf118e19c5116316495544217-1024-1024.jpg"));
			ProductoPeso productoPeso20 =(new ProductoPeso("EDAM PARA MAQUINA",TipoProducto.LACTEO ,"Nuestro queso de maquina de textura blanda y pasta de color amarillenta tiene el estacionamiento ideal para consumir en fetas.",25.0,1800.0,PaisProducto.NACIONAL ,7,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-edam-para-maquina1-deb7b288bb6c2a93a016306163398687-1024-1024.jpg"));
			ProductoPeso productoPeso21 =(new ProductoPeso("QUESO BRIE",TipoProducto.LACTEO ,"Queso de textura cremosa, de sabor delicado y suave, con corteza aterciopelada",40.0,1780.0,PaisProducto.NACIONAL ,6,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-brie1-89a1fec5c9f4872bc316316455199964-1024-1024.jpg"));
			ProductoPeso productoPeso22 =(new ProductoPeso("BURRATA",TipoProducto.LACTEO ,"Queso de pasta hilada fresco con crema originario del sur de Italia.",20.0,2140.0,PaisProducto.EUROPEO ,6.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/quesos-burrata1-18b31fa125413a7ac616306190978353-1024-1024.jpg"));
//			ProductoPeso productoPeso23 =(new ProductoPeso("AZUL ESTILO FRANCES",TipoProducto.LACTEO ,"Queso de alta cremosidad que tienen en su pasta cultivos de Penicillium añadidos al producto final y que proporcionan un color entre el azul y el gris-verdoso característico debido a los mohos.",30.0,1300.0,PaisProducto.NACIONAL ,7.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-azul-estilo-frances1-6aeaff98b29d94b88516316352988110-1024-1024.jpg"));
			ProductoPeso productoPeso24 =(new ProductoPeso("FONTINA ESTILO ITALIANO",TipoProducto.LACTEO ,"Queso de pasta semi blanda con agujeros irregulares de un sabor dulce y picante, ideal para picadas.",45.0,2930.0,PaisProducto.NACIONAL ,8.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/fontina-estilo-italiano1-5e67f75ed3d28e712c16306037503228-1024-1024.jpg"));
			ProductoPeso productoPeso25 =(new ProductoPeso("GRUYERE PREMIUM",TipoProducto.LACTEO ,"Tradicional queso semi blando que se destaca por su gran tamaño y su pasta elástica compuesta de agujeros regulares y un sabor picante.",40.0,2950.0,PaisProducto.EUROPEO ,9.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/gruyere-premium1-20c3e94f83b24370ac16306037994944-1024-1024.jpg"));
			ProductoPeso productoPeso26 =(new ProductoPeso("QUESO BLANCO CON CIBOULETTE",TipoProducto.LACTEO ,"Queso blanco salpimentado con ciboulette finamente picado. Ideal para todo tipo de dips.",20.0,3000.0,PaisProducto.NACIONAL ,7.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/elaboracion-propia-queso-blanco-ciboulette1-0217ab25474def519b16306167371111-1024-1024.jpg"));
			ProductoPeso productoPeso27 =(new ProductoPeso("CHEDDAR TIPO INGLES",TipoProducto.LACTEO ,"Queso de origen ingles elaborado tradicionalmente con leche fresca de alta calidad, fermento, tela y maduracion adecuada.",30.0,2000.0,PaisProducto.NACIONAL ,7.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-cheddar-tipo-ingles1-23c112afcd031ef3e316306104108887-1024-1024.jpg"));
			ProductoPeso productoPeso28 =(new ProductoPeso("SCALUNI",TipoProducto.LACTEO ,"Sabrosa masa hilada, ideal para acompañar con frutas u otros quesos como Parmesano Grana, Gruyere Premium, Manchego de Oveja, Morbier o Camembert entre otros.",20.0,2600.0,PaisProducto.NACIONAL ,7.9,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-scaluni1-a6f4ad87a94367d40716316349395085-1024-1024.jpg"));
			ProductoPeso productoPeso29 =(new ProductoPeso("MORBIER",TipoProducto.LACTEO ,"Queso de pasta blanda elaborado con leche de dos ordeñes, separados por cenizas vegetales que le dan un colorido y sabor especial.",30.0,3000.0,PaisProducto.NACIONAL ,8.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-morbier1-0dbdc2d1068c5b0f8a16306105731605-1024-1024.jpg"));
			ProductoPeso productoPeso30 =(new ProductoPeso("PECORINO",TipoProducto.LACTEO ,"Queso de origen italiano elaborado con leche de oveja de alta calidad",10.0,4000.0,PaisProducto.NACIONAL ,8.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-pecorino1-a536bd5d41997dd46d16306097441179-1024-1024.jpg"));
			ProductoPeso productoPeso31 =(new ProductoPeso("PARMESANO GRANA",TipoProducto.LACTEO ,"Queso Duro de Grana originario de Argentina con 10 meses de estacionamiento. Combina un sabor suave y complejo donde puede sentir que su guarda ha jugado un rol decisivo.",20.0,3000.0,PaisProducto.NACIONAL ,7.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/queso-parmesano-grana1-b428f84c70295a61ab16306035536171-1024-1024.jpg"));
			ProductoPeso productoPeso32 =(new ProductoPeso("PROVOLONE",TipoProducto.LACTEO ,"Este queso es reconocido por su particular forma de pera. Durante su guarda de 10 meses, el queso permanece colgado de cabeza, sujeto con una red.",15.0,3500.0,PaisProducto.NACIONAL ,8.0,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/provolone1-fe4c1e2b0e804f357f16306034274865-1024-1024.jpg"));
			ProductoPeso productoPeso33 =(new ProductoPeso("ROQUEFORT DE OVEJA",TipoProducto.LACTEO ,"Queso de alta cremosidad que tienen en su pasta cultivos de Penicillium añadidos al producto final y que proporcionan un color entre el azul y el gris-verdoso característico debido a los mohos",20.0,2500.0,PaisProducto.NACIONAL ,6.5,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/roquefort-de-oveja-dop1-be711561506f7620c416316454060992-1024-1024.jpg"));

			//CREACION DE PRODUCTO POR UNIDAD
			ProductoUni productoUni02 =(new ProductoUni("CERVEZA MILLER LATA",TipoProducto.BEBIDAALC, "Es una cerveza americana de tipo lager, suave y fácil de beber. De contenido alcohólico moderado y combinado con su punto amargo, hacen que esta cerveza sea una de las mas refrescantes dentro de su segmento", 100, 350.0, PaisProducto.NORTEAMERICANO,  7, "https://ardiaprod.vtexassets.com/arquivos/ids/231206-800-auto?v=638026469267100000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni03 =(new ProductoUni("CERVEZA HEINEKEN LATA",TipoProducto.BEBIDAALC,"Es una cerveza única, y desde 1873 hasta la fecha conserva la misma calidad, principios e ingredientes. A diferencia de cualquier otra cerveza en México, lo que la hace mas clara, pura y durable. 5% Alc.",100,455.0,PaisProducto.SUDAMERICANO,8,"https://ardiaprod.vtexassets.com/arquivos/ids/231095-800-auto?v=638026467435200000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni04 =(new ProductoUni("CERVEZA QUILMES LATA",TipoProducto.BEBIDAALC,"Es una cerveza lager argentina, elaborada con ingredientes nacionales. Con equilibrio entre el suave amargor del lúpulo y el sabor del cereal. Color amarillo dorado brillante. Es una cerveza equilibrada, de gran refrescancia y cuerpo balanceado.",120,450.0,PaisProducto.NACIONAL,5,"https://ardiaprod.vtexassets.com/arquivos/ids/184142-800-auto?v=637427537470730000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni05 =(new ProductoUni("CERVEZA ANDES ORIGEN ROJA LATA",TipoProducto.BEBIDAALC,"Es una cerveza que posee un final seco y una leve acidez que la hacen muy versátil. Es una cerveza de color ámbar cobrizo que tiene estilo Vienna Lager, su aroma a suave caramelo y leve lúpulo logran un balance perfecto.",150,370.0,PaisProducto.NACIONAL ,7.5,"https://ardiaprod.vtexassets.com/arquivos/ids/196306-800-auto?v=637545920180300000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni06 =(new ProductoUni("CERVEZA CORONA LATA",TipoProducto.BEBIDAALC ,"Es una cerveza clara y brillante, de espuma blanca y consistente. Destacan sus ligeras notas afrutadas, resultado de la fermentación. De cuerpo medio, fresca, balanceada y muy fácil de beber. En boca es moderadamente dulce y recuerda al sabor del cereal.",100,320.0,PaisProducto.SUDAMERICANO,7,"https://ardiaprod.vtexassets.com/arquivos/ids/231313-800-auto?v=638026471022630000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni07 =(new ProductoUni("CERVEZA ANDES ORIGEN RUBIA LATA",TipoProducto.BEBIDAALC ,"Es una cerveza estilo american pilsner, una rubia con cuerpo y carácter maltoso. Aroma leve a lúpulo, cereal y toque de levadura. Debe servirse en copa para apreciar mejor sus aromas y resaltar así su color dorado brillante.",100,350.0,PaisProducto.NACIONAL,5,"https://ardiaprod.vtexassets.com/arquivos/ids/247892-800-auto?v=638167491755330000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni08 =(new ProductoUni("CERVEZA BRAHMA LATA",TipoProducto.BEBIDAALC ,"Es una cerveza bastante plana, casi podríamos decir sosa, con un ligero regusto un poco artificial. A pesar de ello, resulta refrescante, siempre sirviéndola muy fría.",150,300.0,PaisProducto.SUDAMERICANO,5,"https://ardiaprod.vtexassets.com/arquivos/ids/192108-800-auto?v=637506170079900000&width=800&height=auto&aspect=true"));
//			ProductoUni productoUni09 =(new ProductoUni("GASEOSA COCA COLA SIN AZUCAR",TipoProducto.BEBIDA ," ",200,290.0,PaisProducto.SUDAMERICANO,5,"https://ardiaprod.vtexassets.com/arquivos/ids/247386-800-auto?v=638157866953570000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni10 =(new ProductoUni("GASEOSA SPRITE LIMALIMON",TipoProducto.BEBIDA ," ",200,380.0,PaisProducto.SUDAMERICANO,8.5,"https://ardiaprod.vtexassets.com/arquivos/ids/253441-800-auto?v=638211302931070000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni11 =(new ProductoUni("GASEOSA FANTA NARANJA SIN AZUCAR",TipoProducto.BEBIDA ," ",150,290.0, PaisProducto.SUDAMERICANO,7,"https://ardiaprod.vtexassets.com/arquivos/ids/245449-800-auto?v=638121507743870000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni12 =(new ProductoUni("GASEOSA COCA COLA SIN AZUCAR",TipoProducto.BEBIDA," ",150,240.0,PaisProducto.SUDAMERICANO,5,"https://ardiaprod.vtexassets.com/arquivos/ids/245376-800-auto?v=638121506477330000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni13 =(new ProductoUni("GASEOSA SEVEN UP LIMA LIMON",TipoProducto.BEBIDA ," ",300,170.0,PaisProducto.SUDAMERICANO ,6,"https://ardiaprod.vtexassets.com/arquivos/ids/245226-800-auto?v=638121504033530000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni14 =(new ProductoUni("GASEOSA LEVITE FIZZ POMELO",TipoProducto.BEBIDA ," ",400,140.0,PaisProducto.SUDAMERICANO,2,"https://ardiaprod.vtexassets.com/arquivos/ids/249302-800-auto?v=638181574478270000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni15 =(new ProductoUni("PATA DE JAMON CRUDO CON JAMONERA",TipoProducto.ENCURTIDO ,"Pata de Jamón crudo seleccionado con 12 meses de estacionamiento con jamonera",25,59000.0,PaisProducto.NACIONAL ,10,"https://d3ugyf2ht6aenh.cloudfront.net/stores/001/429/739/products/jamon-crudo-con-jamonera-021-d074da5124eab70af316305882910895-1024-1024.jpg"));
			ProductoUni productoUni16 =(new ProductoUni("GASEOSA COCA COLA SABOR ORIGINAL",TipoProducto.BEBIDA ," ",700,350.0,PaisProducto.SUDAMERICANO ,6,"https://ardiaprod.vtexassets.com/arquivos/ids/253451-800-auto?v=638211303044670000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni17 =(new ProductoUni("GASEOSA COLA PEPSI",TipoProducto.BEBIDA ," ",180,170.0,PaisProducto.SUDAMERICANO ,6,"https://ardiaprod.vtexassets.com/arquivos/ids/244675-800-auto?v=638120919633630000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni18 =(new ProductoUni("GASEOSA SEVEN UP LIMA LATA",TipoProducto.BEBIDA ," ",140,150.0,PaisProducto.SUDAMERICANO ,7.5,"https://ardiaprod.vtexassets.com/arquivos/ids/245237-800-auto?v=638121504184370000&width=800&height=auto&aspect=true"));
			ProductoUni productoUni19 =(new ProductoUni("PICADA TRADICIONAL PARA 2/3 PERSONAS",TipoProducto.PICADA,"Panes saborizados, salchichón con jamon, aceitunas verdes y queso blanco.",100,9000.0,PaisProducto.NACIONAL ,8.5,"https://res.cloudinary.com/dehmotgth/image/upload/v1686011812/1_ukry3n.png"));
			ProductoUni productoUni20 =(new ProductoUni("PICADA TRADICIONAL PARA 5/6 PERSONAS",TipoProducto.PICADA ,"Panes saborizados, salchichón con jamon, aceitunas verdes y queso blanco.",100,20000.0,PaisProducto.NACIONAL,7,"https://res.cloudinary.com/dehmotgth/image/upload/v1686011813/2_bz6sge.png"));
			ProductoUni productoUni21 =(new ProductoUni("PICADA CLASICA PARA 2/3 PERSONAS",TipoProducto.PICADA,"Paté de tomate seco, jamón crudo, aceitunas verdes y queso brio.",100,10000.0,PaisProducto.SUDAMERICANO,5,"https://res.cloudinary.com/dehmotgth/image/upload/v1686011812/3_zfw2k7.png"));
			ProductoUni productoUni22 =(new ProductoUni("PICADA CLASICA PARA 5/6 PERSONAS",TipoProducto.PICADA,"Paté de tomate seco, jamón crudo, aceitunas verdes y queso brio.",100,22000.0,PaisProducto.SUDAMERICANO ,7,"https://res.cloudinary.com/dehmotgth/image/upload/v1686011812/1_ukry3n.png"));
			ProductoUni productoUni23 =(new ProductoUni("PICADA SELECCION DE FIAMBRES 2/3 PERSONAS",TipoProducto.PICADA ,"Uvas verdes, jamón crudo, queso blanco con ciboulette, aceitunas verdes y negras, salamin y queso roquefort.",50,12000.0,PaisProducto.SUDAMERICANO,9.5,"https://res.cloudinary.com/dehmotgth/image/upload/v1686011813/2_bz6sge.png"));
			ProductoUni productoUni24 =(new ProductoUni("PICADA SELECCION DE FIAMBRES 5/6 PERSONAS",TipoProducto.PICADA ,"Uvas verdes, jamón crudo, queso blanco con ciboulette, aceitunas verdes y negras, salamin y queso roquefort.",50,24000.0,PaisProducto.SUDAMERICANO ,7.5,"https://res.cloudinary.com/dehmotgth/image/upload/v1686011812/3_zfw2k7.png"));
//			ProductoUni productoUni25 =(new ProductoUni("",TipoProducto.PICADA ," ",0,0.0,PaisProducto. ,5,""));
//			ProductoUni productoUni26 =(new ProductoUni("",TipoProducto. ," ",0,0.0,PaisProducto. ,5,""));
//			ProductoUni productoUni27 =(new ProductoUni("",TipoProducto. ," ",0,0.0,PaisProducto. ,5,""));
//			ProductoUni productoUni28 =(new ProductoUni("",TipoProducto. ," ",0,0.0,PaisProducto. ,5,""));
//			ProductoUni productoUni29 =(new ProductoUni("",TipoProducto. ," ",0,0.0,PaisProducto. ,5,""));
//			ProductoUni productoUni30 =(new ProductoUni("",TipoProducto. ," ",0,0.0,PaisProducto. ,5,""));
//			ProductoUni productoUni31 =(new ProductoUni("",TipoProducto. ," ",0,0.0,PaisProducto. ,5,""));
//			ProductoUni productoUni32 =(new ProductoUni("",TipoProducto. ," ",0,0.0,PaisProducto. ,5,""));

			//GUARDADO DE DATOS
			//productos
			productoPesoRepositorio.save(productoPeso01);
			productoPesoRepositorio.save(productoPeso02);
			productoPesoRepositorio.save(productoPeso03);
			productoPesoRepositorio.save(productoPeso04);
			productoPesoRepositorio.save(productoPeso05);
			productoPesoRepositorio.save(productoPeso06);
			productoPesoRepositorio.save(productoPeso07);
			productoPesoRepositorio.save(productoPeso08);
			productoPesoRepositorio.save(productoPeso09);
			productoPesoRepositorio.save(productoPeso10);
			productoPesoRepositorio.save(productoPeso11);
			productoPesoRepositorio.save(productoPeso12);
			productoPesoRepositorio.save(productoPeso13);
			productoPesoRepositorio.save(productoPeso14);
			productoPesoRepositorio.save(productoPeso15);
			productoPesoRepositorio.save(productoPeso16);
			productoPesoRepositorio.save(productoPeso17);
			productoPesoRepositorio.save(productoPeso18);
			productoPesoRepositorio.save(productoPeso19);
			productoPesoRepositorio.save(productoPeso20);
			productoPesoRepositorio.save(productoPeso21);
			productoPesoRepositorio.save(productoPeso22);
//			productoPesoRepositorio.save(productoPeso23);
			productoPesoRepositorio.save(productoPeso24);
			productoPesoRepositorio.save(productoPeso25);
			productoPesoRepositorio.save(productoPeso26);
			productoPesoRepositorio.save(productoPeso27);
			productoPesoRepositorio.save(productoPeso28);
			productoPesoRepositorio.save(productoPeso29);
			productoPesoRepositorio.save(productoPeso30);
			productoPesoRepositorio.save(productoPeso31);
			productoPesoRepositorio.save(productoPeso32);
			productoPesoRepositorio.save(productoPeso33);

			productoUniRepositorio.save(productoUni02);
			productoUniRepositorio.save(productoUni03);
			productoUniRepositorio.save(productoUni04);
			productoUniRepositorio.save(productoUni05);
			productoUniRepositorio.save(productoUni06);
			productoUniRepositorio.save(productoUni07);
			productoUniRepositorio.save(productoUni08);
//			productoUniRepositorio.save(productoUni09);
			productoUniRepositorio.save(productoUni10);
			productoUniRepositorio.save(productoUni11);
			productoUniRepositorio.save(productoUni12);
			productoUniRepositorio.save(productoUni13);
			productoUniRepositorio.save(productoUni14);
			productoUniRepositorio.save(productoUni15);
			productoUniRepositorio.save(productoUni16);
			productoUniRepositorio.save(productoUni17);
			productoUniRepositorio.save(productoUni18);
			productoUniRepositorio.save(productoUni19);
			productoUniRepositorio.save(productoUni20);
			productoUniRepositorio.save(productoUni21);
			productoUniRepositorio.save(productoUni22);
			productoUniRepositorio.save(productoUni23);
			productoUniRepositorio.save(productoUni24);
//			productoUniRepositorio.save(productoUni25);
//			productoUniRepositorio.save(productoUni26);
//			productoUniRepositorio.save(productoUni27);
//			productoUniRepositorio.save(productoUni28);
//			productoUniRepositorio.save(productoUni29);
//			productoUniRepositorio.save(productoUni30);
//			productoUniRepositorio.save(productoUni31);
//			productoUniRepositorio.save(productoUni32);
//			productoUniRepositorio.save(productoUni33);


		};
	}
}
