package com.ricofiambre.ecomerce.modelos;

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

    @OneToMany(mappedBy="cliente", fetch=FetchType.EAGER)
    private Set<Orden> ordenes = new HashSet<>();

    //CONSTRUCTORES
    public Cliente(){}

    public Cliente(String nombre, String apellido, String email, String calle, String ciudad, int codPostal, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.calle = calle;
        this.ciudad = ciudad;
        this.codPostal = codPostal;
        this.telefono = telefono;
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

    //ADDERS
    public void addOrden(Orden orden) {
        orden.setClient(this);
        ordenes.add(orden);
    }
}

//@Entity
//public class Client {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
//    private long id;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String password;
//
//    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
//    private Set<Account> accounts = new HashSet<>();
//    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
//    private Set<ClientLoan> clientLoans = new HashSet<>();
//
//    @OneToMany(mappedBy="cardHolder", fetch=FetchType.EAGER)
//    private Set<Card> cards = new HashSet<>();
//
//    public Client() { }
//
//    public Client(String first, String last, String mail, String password) {
//        this.firstName = first;
//        this.lastName = last;
//        this.email = mail;
//        this.password = password;
//    }