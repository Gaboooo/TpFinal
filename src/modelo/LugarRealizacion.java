package modelo;

import java.util.*;

public class LugarRealizacion {
    
    private int id;
    private String nombre;
    private String descripción;
    ArrayList<Deporte> listaDeportes;

    public void setID(int unaID) {
        this.id = unaID; 
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción; 
    }

    public void setDeportes(ArrayList<Deporte> unaLista) {
        this.listaDeportes = unaLista; 
    }

    public int getID() {
        return id; 
    }

    public String getNombre() {
        return nombre; 
    }

    public String getDescripción() {
        return descripción; 
    }

    public ArrayList<Deporte> getDeportes() {
        return listaDeportes; 
    }

    public LugarRealizacion(int unaID, String unNombre, String unaDescripción, ArrayList<Deporte> unaLista) {
        this.id = unaID;
        this.nombre = unNombre;
        this.descripción = unaDescripción;
        this.listaDeportes = unaLista; 
    } 
}