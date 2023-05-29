package com.ricofiambre.ecomerce.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProductoPeso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private TipoProducto tipo;
    private String descripcion;
    private double stock;
    private double precio;
    private PaisProducto origen;

    @OneToMany(mappedBy="productoPeso", fetch = FetchType.EAGER)
    private Set<OrdenProductoPeso> ordenProductoPesos = new HashSet<>();
    

    //CONSTRUCTORES
    public ProductoPeso(){}

    public ProductoPeso(String nombre, TipoProducto tipo, String descripcion, double stock, double precio, PaisProducto origen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.origen = origen;
    }

    //GETTERS
    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoProducto getTipo() {return tipo;}
    public String getDescripcion() {return descripcion;}
    public double getStock() {return stock;}
    public double getPrecio() {return precio;}
    public PaisProducto getOrigen() {return origen;}
    public Set<OrdenProductoPeso> getOrdenProductoPesos() {return ordenProductoPesos;}

    //SETTERS
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipo(TipoProducto tipo) {this.tipo = tipo;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setStock(double stock) {this.stock = stock;}
    public void setPrecio(double precio) {this.precio = precio;}
    public void setOrigen(PaisProducto origen) {this.origen = origen;}
    public void setOrdenProductoPesos(Set<OrdenProductoPeso> ordenProductoPesos) {this.ordenProductoPesos = ordenProductoPesos;}

    //ADDERS
    public void addOrdenProductoPeso(OrdenProductoPeso ordenProductoPeso) {
        ordenProductoPeso.setProductoPeso(this);
        ordenProductoPesos.add(ordenProductoPeso);
    }
}
