package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.modelos.OrdenProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;

public class OrdenProductoPesoDTO {
    private long id;
    private double cantidadKg;
    private double total;


    //CONSTRUCTOR

    public OrdenProductoPesoDTO(OrdenProductoPeso ordenProductoPeso) {
        this.id = ordenProductoPeso.getId();
        this.cantidadKg = ordenProductoPeso.getCantidadKg();
        this.total = ordenProductoPeso.getTotal();
    }

    //GETTERS
    public long getId() {return id;}
    public double getCantidadKg() {return cantidadKg;}
    public double getTotal() {return total;}
}
