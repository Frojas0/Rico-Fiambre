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
    private String url;
    private double descuento;
    private boolean estaActivo;
    private boolean esPorPeso;
    @ElementCollection
    private List<Double> puntuaciones = new ArrayList<>();
    @OneToMany(mappedBy="productoUni", fetch = FetchType.EAGER)
    private Set<OrdenProductoUni> ordenProductoUnis = new HashSet<>();

    //CONSTRUCTORES
    public ProductoUni(){}

    public ProductoUni(String nombre, TipoProducto tipo, String descripcion, int stock, double precio, PaisProducto origen, double puntuacion,String url) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.stock = stock;
        this.precio = precio;
        this.origen = origen;
        this.puntuaciones.add(puntuacion);
        this.url = url;
        this.descuento = 1;
        this.estaActivo = true;
        this.esPorPeso = false;
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
    public String getUrl() {return url;}
    public double getDescuento() {return descuento;}
    public boolean getEstaActivo() {return estaActivo;}
    public boolean esPorPeso() {return esPorPeso;}
    //SETTERS
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTipo(TipoProducto tipo) {this.tipo = tipo;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setStock(int stock) {this.stock = stock;}
    public void setPrecio(double precio) {this.precio = precio;}
    public void setOrigen(PaisProducto origen) {this.origen = origen;}
    public void setOrdenProductoUnis(Set<OrdenProductoUni> ordenProductoUnis) {this.ordenProductoUnis = ordenProductoUnis;}
    public void setPuntuaciones(List<Double> puntuaciones) {this.puntuaciones = puntuaciones;}
    public void setUrl(String url) {this.url = url;}
    public void setDescuento(double descuento) {this.descuento = descuento;}
    public void setEstaActivo(boolean estaActivo) {this.estaActivo = estaActivo;}

    //ADDERS
    public void addOrdenProductoUni(OrdenProductoUni ordenProductoUni) {
        ordenProductoUni.setProductoUni(this);
        ordenProductoUnis.add(ordenProductoUni);
    }
    public void addPuntuacion (double valor){
        puntuaciones.add(valor);
    }

}
