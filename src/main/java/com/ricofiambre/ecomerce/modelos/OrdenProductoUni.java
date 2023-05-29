package com.ricofiambre.ecomerce.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class OrdenProductoUni {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private int cantidadUni;
    private double total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idOrden")
    private Orden orden;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idProductoUni")
    private ProductoUni productoUni;

    //CONSTRUCTORES
    public OrdenProductoUni(){}

    public OrdenProductoUni(int cantidadUni, double total) {
        this.cantidadUni = cantidadUni;
        this.total = total;
    }

    //GETTERS
    public long getId() {return id;}
    public int getCantidadUni() {return cantidadUni;}
    public double getTotal() {return total;}
    public Orden getOrden() {return orden;}
    public ProductoUni getProductoUni() {return productoUni;}


    //SETTERS
    public void setCantidadUni(int cantidadUni) {this.cantidadUni = cantidadUni;}
    public void setTotal(double total) {this.total = total;}
    public void setOrden(Orden orden) {this.orden = orden;}
    public void setProductoUni(ProductoUni productoUni) {this.productoUni = productoUni;}

}
