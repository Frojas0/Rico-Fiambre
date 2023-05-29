package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.PaisProducto;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.TipoProducto;

public class ProductoPesoDTO {
    private long id;
    private String nombre;
    private TipoProducto tipo;
    private String descripcion;
    private double stock;
    private double precio;
    private PaisProducto origen;

    //CONSTRUCTOR
    public ProductoPesoDTO (ProductoPeso productoPeso){
        this.id = productoPeso.getId();
        this.nombre = productoPeso.getNombre();
        this.tipo = productoPeso.getTipo();
        this.descripcion = productoPeso.getDescripcion();
        this.stock = productoPeso.getStock();
        this.precio = productoPeso.getPrecio();
        this.origen = productoPeso.getOrigen();
    }
    //GETTERS

    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoProducto getTipo() {return tipo;}
    public String getDescripcion() {return descripcion;}
    public double getStock() {return stock;}
    public double getPrecio() {return precio;}
    public PaisProducto getOrigen() {return origen;}
}
