package modelo;

import java.util.*;

public class Ronda {
    private int ID;
    private int numRonda;
    private Boolean rondaPerdedores; 
    private String fecha;
    ArrayList<Partido> listaPartidos;  
    
    public void setID(int unID) {
        this.ID = unID; }
    
    public void setNumeroRonda(int unNumero) {
        this.numRonda = unNumero; }
    
    public void setRondaPerdedores(Boolean esRP) {
        this.rondaPerdedores = esRP; }

    public void setFecha(String unaFecha) {
        this.fecha = unaFecha; }

    public void setPartido(ArrayList<Partido> unaLista) {
        this.listaPartidos = unaLista; }

    public int getID() {
        return ID; }
    
    public int getNumero() {
        return numRonda; }    
    
    public Boolean getRondaPerdedores() {
        return rondaPerdedores; }

    public String getFecha() {
        return fecha; }

    public ArrayList<Partido> getListaPartidos() {
        return listaPartidos; }

    // Sin ID
    public Ronda(int unNumero, Boolean esRP, String unaFecha, ArrayList<Partido> unaLista) { 
        this.numRonda = unNumero;
        this.rondaPerdedores = esRP;
        this.fecha = unaFecha;
        this.listaPartidos = unaLista; }
    
    // Con ID
    public Ronda(int unaID, int unNumero, Boolean esRP, String unaFecha, ArrayList<Partido> unaLista) {
        this.ID = unaID; 
        this.numRonda = unNumero;
        this.rondaPerdedores = esRP;
        this.fecha = unaFecha;
        this.listaPartidos = unaLista; } }