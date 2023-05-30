package com.ricofiambre.ecomerce.dtos;
import com.ricofiambre.ecomerce.modelos.OrdenProductoUni;

public class OrdenProductoUniDTO {
    private long id;
    private String nombreProductoUni;
    private int cantidadUni;
    private double precioUnidad;
    private double total;
    private double puntuacion;

    //CONSTRUCTOR
    public OrdenProductoUniDTO(OrdenProductoUni ordenProductoUni) {
        this.id = ordenProductoUni.getId();
        this.nombreProductoUni = ordenProductoUni.getProductoUni().getNombre();
        this.cantidadUni = ordenProductoUni.getCantidadUni();
        this.precioUnidad = ordenProductoUni.getProductoUni().getPrecio();
        this.total = ordenProductoUni.getTotal();
        this.puntuacion = ordenProductoUni.getProductoUni().getPuntuaciones().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    //GETTERS
    public long getId() {return id;}
    public String getNombreProductoUni() {return nombreProductoUni;}
    public int getCantidadUni() {return cantidadUni;}
    public double getPrecioUnidad() {return precioUnidad;}
    public double getTotal() {return total;}
    public double getPuntuacion() {return puntuacion;}
}
