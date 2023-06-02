package com.ricofiambre.ecomerce.modelos;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String calle;
    private String ciudad;
    private int codPostal;
    private String telefono;
    private String contrasena;

    @OneToMany(mappedBy="cliente", fetch=FetchType.EAGER)
    private Set<Orden> ordenes = new HashSet<>();

    //CONSTRUCTORES
    public Cliente(){}

    public Cliente(String nombre, String apellido, String email, String calle, String ciudad, int codPostal, String telefono, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.calle = calle;
        this.ciudad = ciudad;
        this.codPostal = codPostal;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }

    //GETTERS
    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public String getApellido() {return apellido;}
    public String getEmail() {return email;}
    public String getCalle() {return calle;}
    public String getCiudad() {return ciudad;}
    public int getCodPostal() {return codPostal;}
    public String getTelefono() {return telefono;}
    public String getContrasena(){return  contrasena;}
    public Set<Orden> getOrdenes() {return ordenes;}

    //SETTERS
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setApellido(String apellido) {this.apellido = apellido;}
    public void setEmail(String email) {this.email = email;}
    public void setCalle(String calle) {this.calle = calle;}
    public void setCiudad(String ciudad) {this.ciudad = ciudad;}
    public void setCodPostal(int codPostal) {this.codPostal = codPostal;}
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public void setOrdenes(Set<Orden> ordenes) {this.ordenes = ordenes;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}

    //ADDERS
    public void addOrden(Orden orden) {
        orden.setClient(this);
        ordenes.add(orden);
    }
}