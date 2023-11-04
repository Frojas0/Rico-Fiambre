package com.ricofiambre.ecomerce.utilidades;

import com.ricofiambre.ecomerce.dtos.CarritoCompraDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class PagarConTarjetaUtilidad {

    public static ResponseEntity<Object> pagarConTarjeta(@RequestBody CarritoCompraDTO carritoCompraDTO, double montoAPagar) {
        int cvv = carritoCompraDTO.getCvv();
        double monto = montoAPagar;
        String numeroTarjeta = carritoCompraDTO.getNumero();
        String descripcion = carritoCompraDTO.getDescripcion();

        if (monto < 0){
            return new ResponseEntity<>("El monto no debe ser negativo", HttpStatus.FORBIDDEN);
        }
        if (descripcion.isBlank()){
            return new ResponseEntity<>("Debes proporcionar una descripcion", HttpStatus.FORBIDDEN);
        }
        if (numeroTarjeta.isBlank()){
            return new ResponseEntity<>("Debes proporcionar un numero de tarjeta", HttpStatus.FORBIDDEN);
        }
        if (numeroTarjeta.length() != 19){
            return new ResponseEntity<>("Debes proporcionar un numero de tarjeta valido", HttpStatus.FORBIDDEN);
        }

        try {
            URL url = new URL("https://homebanking-v3ar.onrender.com/api/card/payment");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String cuerpoDeSolicitud = "{\"cvv\": " + cvv + ", \"amount\": " + monto + ", \"number\": \"" + numeroTarjeta + "\", \"description\": \"" + descripcion + "\"}";
            connection.getOutputStream().write(cuerpoDeSolicitud.getBytes());

            int codigoDeRespuesta = connection.getResponseCode();
            if (codigoDeRespuesta == HttpURLConnection.HTTP_CREATED) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                System.out.println("Respuesta del servidor: " + response);

                connection.getInputStream().close();
                connection.disconnect();

                return new ResponseEntity<>("Pago aceptado", HttpStatus.CREATED);
            } else {
                connection.getInputStream().close();
                connection.disconnect();

                return new ResponseEntity<>("Pago rechazado", HttpStatus.FORBIDDEN);
            }
        } catch (Exception err) {
            err.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar el pago");
        }
    }
}
