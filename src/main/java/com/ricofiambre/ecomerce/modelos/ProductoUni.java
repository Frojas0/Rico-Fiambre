package com.ricofiambre.ecomerce.modelos;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ProductoUni {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre;
    private TipoProducto tipo;
    private String descripcion;
    private int stock;
    private double precio;
    private PaisProducto origen;
    @ElementCollection
    private List<Double> puntuaciones = new ArrayList<>();
    @OneToMany(mappedBy="productoUni", fetch = FetchType.EAGER)
    private Set<OrdenProductoUni> ordenProductoUnis = new HashSet<>();

    //CONSTRUCTORES
    public ProductoUni(){}

    public ProductoUni(String nombre, TipoProducto tipo, String descripcion, int stock, double precio, PaisProducto origen, double puntuacion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.origen = origen;
        this.puntuaciones.add(puntuacion);
    }

    //GETTERS
    public long getId() {return id;}
    public String getNombre() {return nombre;}
    public TipoProducto getTipo() {return tipo;}
    public String getDescripcion() {return descripcion;}
    public int getStock() {return stock;}
    public double getPrecio() {return precio;}
    public PaisProducto getOrigen() {return origen;}
    public Set<OrdenProductoUni> getOrdenProductoUnis() {return ordenProductoUnis;}
    public List<Double> getPuntuaciones() {return puntuaciones;}

    //SETTERS
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipo(TipoProducto tipo) {this.tipo = tipo;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setStock(int stock) {this.stock = stock;}
    public void setPrecio(double precio) {this.precio = precio;}
    public void setOrigen(PaisProducto origen) {this.origen = origen;}
    public void setOrdenProductoUnis(Set<OrdenProductoUni> ordenProductoUnis) {this.ordenProductoUnis = ordenProductoUnis;}
    public void setPuntuaciones(List<Double> puntuaciones) {this.puntuaciones = puntuaciones;}

    //ADDERS
    public void addOrdenProductoUni(OrdenProductoUni ordenProductoUni) {
        ordenProductoUni.setProductoUni(this);
        ordenProductoUnis.add(ordenProductoUni);
    }
    public void addPuntuacion (double valor){
        puntuaciones.add(valor);
    }

}
