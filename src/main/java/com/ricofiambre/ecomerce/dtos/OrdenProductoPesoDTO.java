package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.modelos.OrdenProductoPeso;
import com.ricofiambre.ecomerce.modelos.ProductoPeso;

public class OrdenProductoPesoDTO {
    private long id;
    private String nombreProductoPeso;
    private double cantidadKg;
    private double precioKg;
    private double total;
    private double puntuacion;
    private double descuento;

    //CONSTRUCTOR
    public OrdenProductoPesoDTO(OrdenProductoPeso ordenProductoPeso) {
        this.id = ordenProductoPeso.getId();
        this.cantidadKg = ordenProductoPeso.getCantidadKg();
        this.precioKg = ordenProductoPeso.getProductoPeso().getPrecio();
        this.nombreProductoPeso = ordenProductoPeso.getProductoPeso().getNombre();
        this.total = ordenProductoPeso.getTotal();
        this.puntuacion = ordenProductoPeso.getProductoPeso().getPuntuaciones().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        this.descuento = ((ordenProductoPeso.getProductoPeso().getDescuento()) * 100) -100;
    }

    //GETTERS
    public long getId() {return id;}
    public String getNombreProductoPeso() {return nombreProductoPeso;}
    public double getCantidadKg() {return cantidadKg;}
    public double getPrecioKg() {return precioKg;}
    public double getTotal() {return total;}
    public double getPuntuacion() {return puntuacion;}
    public double getDescuento() {return descuento;}
}
