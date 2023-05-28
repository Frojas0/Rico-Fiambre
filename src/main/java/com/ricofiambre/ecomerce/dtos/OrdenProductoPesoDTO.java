package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.modelos.OrdenProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;

public class OrdenProductoPesoDTO {
    private long id;
    private double cantidadKg;
    private Orden orden;
    private ProductoPesoDTO productoPeso;


    //CONSTRUCTOR

    public OrdenProductoPesoDTO(OrdenProductoPeso ordenProductoPeso) {
        this.id = ordenProductoPeso.getId();
        this.cantidadKg = ordenProductoPeso.getCantidadKg();
        this.orden = ordenProductoPeso.getOrden();
        this.productoPeso = new ProductoPesoDTO(ordenProductoPeso.getProductoPeso());
    }

    //GETTERS
    public long getId() {return id;}
    public double getCantidadKg() {return cantidadKg;}
    public Orden getOrden() {return orden;}
    public ProductoPesoDTO getProductoPeso() {return productoPeso;}
}
