package com.ricofiambre.ecomerce.dtos;

import com.ricofiambre.ecomerce.modelos.Cliente;
import com.ricofiambre.ecomerce.modelos.Orden;
import net.minidev.json.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteDTO {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String calle;
    private String ciudad;
    private int codPostal;
    private String telefono;
    private Set<OrdenDTO> ordenes;

    //CONSTRUCTOR
    public ClienteDTO (Cliente cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.apellido = cliente.getApellido();
        this.email = cliente.getEmail();
        this.calle = cliente.getCalle();
        this.ciudad = cliente.getCiudad();
        this.codPostal = cliente.getCodPostal();
        this.telefono = cliente.getTelefono();
        this.ordenes = cliente.getOrdenes().stream().map(orden -> new OrdenDTO(orden)).collect(Collectors.toSet());
    }


    //GETTERS
    public long getId(){return id;}
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getEmail() {return email;}
    public String getCalle() {return calle;}
    public String getCiudad() {return ciudad;}
    public int getCodPostal() {return codPostal;}
    public String getTelefono() {return telefono;}
    public Set<OrdenDTO> getOrdenes() {return ordenes;}
}
