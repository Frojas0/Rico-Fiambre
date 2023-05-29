package com.ricofiambre.ecomerce.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String numeroDeOrden;
    private LocalDateTime fecha;
    private boolean envio;
    private boolean pagado;
    private double total;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idCliente")
    private Cliente cliente;

    @OneToOne(mappedBy = "orden", fetch=FetchType.EAGER)
    private Ticket ticket;

    @OneToMany(mappedBy="orden", fetch=FetchType.EAGER)
    private Set<OrdenProductoUni> ordenProductoUnis = new HashSet<>();

    @OneToMany(mappedBy="orden", fetch=FetchType.EAGER)
    private Set<OrdenProductoPeso> ordenProductoPesos = new HashSet<>();


    //CONSTRUCTORES
    public Orden(){}

    public Orden(LocalDateTime fecha, boolean envio, boolean pagado, double total, String numeroDeOrden) {
        this.numeroDeOrden = numeroDeOrden;
        this.fecha = fecha;
        this.envio = envio;
        this.pagado = pagado;
        this.total = total;
    }

    //GETTERS
    public long getId() {return id;}
    public LocalDateTime getFecha() {return fecha;}
    public boolean getEnvio() {return envio;}
    public boolean getPagado() {return pagado;}
    public double getTotal() {return total;}
    public Cliente getClient() {return cliente;}
    public Ticket getTicket() {return ticket;}
    public Cliente getCliente() {return cliente;}
    public String getNumeroDeOrden(){return numeroDeOrden;}
    public Set<OrdenProductoUni> getOrdenProductoUnis() {return ordenProductoUnis;}
    public Set<OrdenProductoPeso> getOrdenProductoPesos() {return ordenProductoPesos;}

    //SETTERS
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}
    public void setEnvio(boolean envio) {this.envio = envio;}
    public void setPagado(boolean pagado) {this.pagado = pagado;}
    public void setTotal(double total) {this.total = total;}
    public void setClient(Cliente cliente) {this.cliente = cliente;}
    public void setTicket(Ticket ticket) {this.ticket = ticket;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    public void setNumeroDeOrden(String numeroDeOrden){this.numeroDeOrden = numeroDeOrden;}
    public void setOrdenProductoUnis(Set<OrdenProductoUni> ordenProductoUnis) {this.ordenProductoUnis = ordenProductoUnis;}
    public void setOrdenProductoPesos(Set<OrdenProductoPeso> ordenProductoPesos) {this.ordenProductoPesos = ordenProductoPesos;}

    //ADDER
    public void addOrdenProductoUni(OrdenProductoUni ordenProductoUni){
        ordenProductoUni.setOrden(this);
        ordenProductoUnis.add(ordenProductoUni);
    }
    public void addOrdenProductoPeso(OrdenProductoPeso ordenProductoPeso) {
        ordenProductoPeso.setOrden(this);
        ordenProductoPesos.add(ordenProductoPeso);
    }

}
