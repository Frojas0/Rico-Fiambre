package com.ricofiambre.ecomerce.dtos;

import java.util.List;

public class CarritoCompraDTO {
    private List<String> productos;
    private String numero;
    private int cvv;
    private String descripcion;


    //CONSTRUCTOR
    public CarritoCompraDTO(){}

    //GETTERS
    public String getNumero() {return numero;}
    public int getCvv() {return cvv;}
    public String getDescripcion() {return descripcion;}
    public List<String> getProductos() {return productos;}
}
