package com.ricofiambre.ecomerce.dtos;

import java.util.List;

public class CarritoCompraDTO {
    private List<String> productos;

//    public CarritoCompraDTO(List<String> productos) {
//        this.productos = productos;
//    }
    //CONSTRUCTOR
    public CarritoCompraDTO(){}
    //GETTERS
    public List<String> getProductos() {return productos;}
}
