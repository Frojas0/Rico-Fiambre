package com.ricofiambre.ecomerce.modelos;

import java.util.Arrays;
import java.util.List;

public enum PaisProducto {
    NACIONAL,
    EUROPEO,
    ASIATICO,
    SUDAMERICANO,
    NORTEAMERICANO;

    public static List<PaisProducto> obtenerPaisProducto() {
        return Arrays.asList(com.ricofiambre.ecomerce.modelos.PaisProducto.values());
    }
}
