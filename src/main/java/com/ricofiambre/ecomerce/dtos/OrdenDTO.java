package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Orden;
import com.ricofiambre.ecomerce.modelos.OrdenProductoPeso;
import com.ricofiambre.ecomerce.modelos.OrdenProductoUni;
import com.ricofiambre.ecomerce.modelos.Ticket;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class OrdenDTO {
    private long id;
    private LocalDateTime fecha;
    private boolean envio;
    private boolean pagado;
    private double total;
    private Ticket ticket;
    private Set<OrdenProductoUniDTO> ordenProductoUnis = new HashSet<>();
    private Set<OrdenProductoPesoDTO> ordenProductoPesos = new HashSet<>();

    //CONSTRUCTOR

    public OrdenDTO(Orden orden) {
        this.id = orden.getId();
        this.fecha = orden.getFecha();
        this.envio = orden.getEnvio();
        this.pagado = orden.getPagado();
        this.total = orden.getTotal();
        this.ticket = orden.getTicket();
        this.ordenProductoUnis = orden.getOrdenProductoUnis().stream().map(opu -> new OrdenProductoUniDTO(opu)).collect(Collectors.toSet());
        this.ordenProductoPesos = orden.getOrdenProductoPesos().stream().map(opp -> new OrdenProductoPesoDTO(opp)).collect(Collectors.toSet());
    }
}
