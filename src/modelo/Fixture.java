package modelo;

import java.util.*;

public class Fixture {
    private int ID;
    ArrayList<Ronda> listaRondas;
    
    public int getID() {
        return ID; }
    
    public ArrayList<Ronda> getListaRondas() {
        return listaRondas; }
    
    public void setID(int unID) {
        this.ID = unID; }

    public void setRonda(ArrayList<Ronda> unaListaRondas) {
        this.listaRondas = unaListaRondas; }

    // Con ID
    public Fixture(int unID, ArrayList<Ronda> unaListaRondas) {
        this.ID = unID;
        this.listaRondas = unaListaRondas; }
    
    // Sin ID
    public Fixture(ArrayList<Ronda> unaListaRondas) {
        this.listaRondas = unaListaRondas; } }