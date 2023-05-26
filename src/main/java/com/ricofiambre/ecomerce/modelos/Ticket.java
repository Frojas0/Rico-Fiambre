package com.ricofiambre.ecomerce.modelos;

import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private long numero;

    @OneToOne
    @JoinColumn(name = "idOrden")
    private Orden orden;

    //CONSTRUCTORES
    public Ticket(){}

    public Ticket(long numero) {
        this.numero = numero;
    }

    //GETTERS
    public long getId() {return id;}
    public long getNumero() {return numero;}
    public Orden getOrden() {return orden;}

    //SETTERS
    public void setNumero(long numero) {this.numero = numero;}
    public void setOrden(Orden orden) {this.orden = orden;}

    //ADDERS
    public void addOrden(Orden orden) {
        orden.setTicket(this);
        this.orden = orden;
    }
}
