package com.ricofiambre.ecomerce.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class OrdenProductoPeso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double cantidadKg;
    private double total;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idOrden")
    private Orden orden;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idProductoPeso")
    private ProductoPeso productoPeso;

    //CONSTRUCTORES
    public OrdenProductoPeso(){}

    public OrdenProductoPeso(double cantidadKg, double total) {
        this.cantidadKg = cantidadKg;
        this.total = total;
    }

    //GETTERS
    public long getId() {return id;}
    public double getCantidadKg() {return cantidadKg;}
    public Orden getOrden() {return orden;}
    public ProductoPeso getProductoPeso() {return productoPeso;}
    public double getTotal() {return total;}

    //SETTERS
    public void setCantidadKg(double cantidadKg) {this.cantidadKg = cantidadKg;}
    public void setOrden(Orden orden) {this.orden = orden;}
    public void setProductoPeso(ProductoPeso productoPeso) {this.productoPeso = productoPeso;}
    public void setTotal(double total) {this.total = total;}

}
