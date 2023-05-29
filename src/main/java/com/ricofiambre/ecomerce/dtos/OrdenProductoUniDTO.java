package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.modelos.OrdenProductoUni;
import com.ricofiambre.ecomerce.modelos.ProductoUni;

public class OrdenProductoUniDTO {
    private long id;
    private int cantidadUni;
    private double total;

    //CONSTRUCTOR
    public OrdenProductoUniDTO(OrdenProductoUni ordenProductoUni) {
        this.id = ordenProductoUni.getId();
        this.cantidadUni = ordenProductoUni.getCantidadUni();
        this.total = ordenProductoUni.getTotal();
    }

    //GETTERS
    public long getId() {return id;}
    public int getCantidadUni() {return cantidadUni;}
    public double getTotal() {return total;}
}
