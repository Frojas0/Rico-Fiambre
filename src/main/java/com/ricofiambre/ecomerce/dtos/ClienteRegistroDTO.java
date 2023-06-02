package com.ricofiambre.ecomerce.dtos;
public class ClienteRegistroDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private String ciudad;
    private int codPostal;
    private String telefono;
    private String contrasena;

    //GETTERS
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getEmail() {return email;}
    public String getDireccion() {return direccion;}
    public String getCiudad() {return ciudad;}
    public int getCodPostal() {return codPostal;}
    public String getTelefono() {return telefono;}
    public String getContrasena() {return contrasena;}
}
