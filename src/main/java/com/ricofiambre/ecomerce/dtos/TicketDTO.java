package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Ticket;

public class TicketDTO {
    private long id;
    private String numero;
    private String nombreDueñoTicket;
    private String apellidoDueñoTicket;

    //CONSTRUCTOR
    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.numero = ticket.getNumero();
        this.nombreDueñoTicket = ticket.getOrden().getClient().getNombre();
        this.apellidoDueñoTicket = ticket.getOrden().getClient().getApellido();
    }

    //GETTERS
    public long getId() {return id;}
    public String getNumero() {return numero;}
    public String getNombreDueñoTicket() {return nombreDueñoTicket;}
    public String getApellidoDueñoTicket() {return apellidoDueñoTicket;}
}
