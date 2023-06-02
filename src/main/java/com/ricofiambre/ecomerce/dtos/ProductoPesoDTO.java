package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.PaisProducto;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;
import com.ricofiambre.ecomerce.modelos.TipoProducto;

import java.util.List;

public class ProductoPesoDTO {
    private long id;
    private String nombre;
    private TipoProducto tipo;
    private String descripcion;
    private double stock;
    private double precio;
    private PaisProducto origen;
    private double puntuaciones;
    private String url;
    private double descuento;
    private boolean estaActivo;
    private boolean esPorPeso;

    //CONSTRUCTOR
    public ProductoPesoDTO (ProductoPeso productoPeso){
        this.id = productoPeso.getId();
        this.nombre = productoPeso.getNombre();
        this.tipo = productoPeso.getTipo();
        this.descripcion = productoPeso.getDescripcion();
        this.stock = productoPeso.getStock();
        this.precio = productoPeso.getPrecio();
        this.origen = productoPeso.getOrigen();
        this.puntuaciones = productoPeso.getPuntuaciones().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        this.url = productoPeso.getUrl();
        this.descuento = productoPeso.getDescuento();
        this.estaActivo = productoPeso.getEstaActivo();
        this.esPorPeso = productoPeso.esPorPeso();
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
    public String getUrl() {return url;}
    public double getDescuento() {return descuento;}
    public boolean getEstaActivo() {return estaActivo;}

    public boolean isEsPorPeso() {return esPorPeso;}
}
