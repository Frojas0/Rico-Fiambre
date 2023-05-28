package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.modelos.Ticket;

public class TicketDTO {
    private long id;
    private long numero;
    private String nombreDueñoTicket;
    private String apellidoDueñoTicket;

    //CONSTRUCTOR
    public TicketDTO(Ticket ticket) {
        this.numero = ticket.getNumero();
        this.nombreDueñoTicket = ticket.getOrden().getClient().getNombre();
        this.apellidoDueñoTicket = ticket.getOrden().getClient().getApellido();
    }

    //GETTERS
    public long getId() {return id;}
    public long getNumero() {return numero;}
    public String getNombreDueñoTicket() {return nombreDueñoTicket;}
    public String getApellidoDueñoTicket() {return apellidoDueñoTicket;}
}
