package com.ricofiambre.ecomerce.utilidades;

public final class OrdenUtilidad {

//                                      Order.getid
    public static String getNumeroOrden(long id) {
        String numeroOrden;
        numeroOrden = String.format("%08d", id);
        return numeroOrden;
    }
}
