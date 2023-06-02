package com.ricofiambre.ecomerce.modelos;

import java.util.Arrays;
import java.util.List;

public enum TipoProducto {
    LACTEO,
    EMBUTIDO,
    ENCURTIDO,
    BEBIDAALC,
    BEBIDA,
    PANIFICADO,
    POSTRE,
    ENSALADA,
    PICADA;

    public static List<TipoProducto> obtenerTiposDeProducto() {
        return Arrays.asList(TipoProducto.values());
    }
}
