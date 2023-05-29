package com.ricofiambre.ecomerce.utilidades;

public final class TicketUtilidad {
    //     do{
    //        long newNumber  = OrdenUtilidad.getNumeroTicket();
    //    }while (ordenService.findByNumeroDeOrden(newNumber) != null);
    public static String getNumeroTicket() {
        String ticketNumber;
        int randomNumber = (int) (Math.random() * 100000000);
        ticketNumber = String.format("%08d", randomNumber);
        return ticketNumber;
    }
}
