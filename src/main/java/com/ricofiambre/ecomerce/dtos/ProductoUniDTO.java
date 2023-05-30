package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.PaisProducto;
import com.ricofiambre.ecomerce.modelos.ProductoUni;
import com.ricofiambre.ecomerce.modelos.TipoProducto;

import java.util.ArrayList;
import java.util.List;

public class ProductoUniDTO {
    private long id;
    private String nombre;
    private TipoProducto tipo;
    private String descripcion;
    private double stock;
    private double precio;
    private PaisProducto origen;
    private double puntuaciones;

    //CONSTRUCTOR
    public ProductoUniDTO(ProductoUni productoUni) {
        this.id = productoUni.getId();
        this.nombre = productoUni.getNombre();
        this.tipo = productoUni.getTipo();
        this.descripcion = productoUni.getDescripcion();
        this.stock = productoUni.getStock();
        this.precio = productoUni.getPrecio();
        this.origen = productoUni.getOrigen();
        this.puntuaciones = productoUni.getPuntuaciones().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    //GETTERS
    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoProducto getTipo() {return tipo;}
    public String getDescripcion() {return descripcion;}
    public double getStock() {return stock;}
    public double getPrecio() {return precio;}
    public PaisProducto getOrigen() {return origen;}
    public double getPuntuaciones() {return puntuaciones;}
}
