package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Ticket;

import java.util.List;
import java.util.stream.Collectors;

public class TicketMuestraDTO {
    private String nombreFiambreria;
    private String direccion;
    private long id;
    private long numero;
    private String nombreDueñoTicket;
    private String apellidoDueñoTicket;
    private List<String> productosCompradosPeso;
    private List<String> productosCompradosUnidad;

    //CONSTRUCTOR
    public TicketMuestraDTO(Ticket ticket) {
        this.direccion = "Direccion de la fiambreria 123";
//        this.id = ticket.getId();
        this.nombreFiambreria = "Rico Fiambre";
        this.productosCompradosPeso = ticket.getOrden().getOrdenProductoPesos().stream().map(producto -> producto.getProductoPeso().getNombre()).collect(Collectors.toList());
        this.numero = ticket.getNumero();
        this.nombreDueñoTicket = ticket.getOrden().getClient().getNombre();
        this.apellidoDueñoTicket = ticket.getOrden().getClient().getApellido();
    }

    //GETTERS
//    public long getId() {return id;}

    public long getNumero() {return numero;}
    public String getNombreDueñoTicket() {return nombreDueñoTicket;}
    public String getApellidoDueñoTicket() {return apellidoDueñoTicket;}

}
