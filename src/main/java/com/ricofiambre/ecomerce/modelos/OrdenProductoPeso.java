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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idOrden")
    private Orden orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idProductoPeso")
    private ProductoPeso productoPeso;


    //CONSTRUCTORES
    public OrdenProductoPeso(){}

    public OrdenProductoPeso(double cantidadKg, Orden orden, ProductoPeso productoPeso) {
        this.cantidadKg = cantidadKg;
        this.orden = orden;
        this.productoPeso = productoPeso;
    }
    //GETTERS
    public double getCantidadKg() {return cantidadKg;}
    public Orden getOrden() {return orden;}
    public ProductoPeso getProductoPeso() {return productoPeso;}
    public long getId() {return id;}


    //SETTERS
    public void setCantidadKg(double cantidadKg) {this.cantidadKg = cantidadKg;}
    public void setOrden(Orden orden) {this.orden = orden;}
    public void setProductoPeso(ProductoPeso productoPeso) {this.productoPeso = productoPeso;}

}
