package com.ricofiambre.ecomerce.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDateTime fecha;
    private boolean envio;
    private boolean pagado;
    private double total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idCliente")
    private Client client;

    @OneToOne(mappedBy = "orden")
    private Ticket ticket;

    //CONSTRUCTORES
    public Orden(){}

    public Orden(LocalDateTime fecha, boolean envio, boolean pagado, double total) {
        this.fecha = fecha;
        this.envio = envio;
        this.pagado = pagado;
        this.total = total;
    }

    //GETTERS
    public long getId() {return id;}
    public LocalDateTime getFecha() {return fecha;}
    public boolean isEnvio() {return envio;}
    public boolean isPagado() {return pagado;}
    public double getTotal() {return total;}
    public Client getClient() {return client;}
    public Ticket getTicket() {return ticket;}

    //SETTERS
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
    public void setEnvio(boolean envio) {this.envio = envio;}
    public void setPagado(boolean pagado) {this.pagado = pagado;}
    public void setTotal(double total) {this.total = total;}
    public void setClient(Client client) {this.client = client;}
    public void setTicket(Ticket ticket) {this.ticket = ticket;}
}
