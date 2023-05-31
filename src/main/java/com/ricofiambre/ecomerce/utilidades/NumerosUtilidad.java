package com.ricofiambre.ecomerce.utilidades;

public final class NumerosUtilidad {
    public static String getNumero() {
        String numero;
        int randomNumber = (int) (Math.random() * 100000000);
        numero = String.format("%08d", randomNumber);
        return numero;
    }
}
