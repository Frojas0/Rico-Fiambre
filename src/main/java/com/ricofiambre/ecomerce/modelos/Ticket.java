package com.ricofiambre.ecomerce.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String numero;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ticket")
    private Orden orden;

    //CONSTRUCTORES
    public Ticket(){}

    public Ticket(String numero) {
        this.numero = numero;
    }

    //GETTERS
    public long getId() {return id;}
    public String getNumero() {return numero;}

    public Orden getOrden() {return orden;}

    //SETTERS
    public void setNumero(String numero) {this.numero = numero;}
    public void setOrden(Orden orden) {this.orden = orden;}

    //ADDERS
    public void addOrden(Orden orden) {
        orden.setTicket(this);
        this.orden = orden;
    }
}
