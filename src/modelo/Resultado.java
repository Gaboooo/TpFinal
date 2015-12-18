package modelo;

import java.util.ArrayList;

public class Resultado {
    private int ID;
    private int numResultado;
    private int puntajeP0;
    private int puntajeP1;
    private Boolean asistenciaP0;
    private Boolean asistenciaP1;
    private Participante ganador;
    
    public int getID(){
        return ID; }   
    
    public int getNumero() {
        return numResultado; }
        
    public int getPuntajeP0() {
        return puntajeP0; }

    public int getPuntajeP1() {
        return puntajeP1; }

    public Boolean getAsistenciaP0() {
        return asistenciaP0; }

    public Boolean getAsistenciaP1() {
        return asistenciaP1; }

    public Participante getGanador() {
        return ganador; }
    
    // Con ID
    public Resultado(int unID, int unNumero, int PP0, int PP1, Boolean AP0, Boolean AP1, Participante unGanador) {
        this.ID = unID;
        this.numResultado = unNumero;
        this.puntajeP0 = PP0;
        this.puntajeP1 = PP1;
        this.asistenciaP0 = AP0;
        this.asistenciaP1 = AP1;
        this.ganador = unGanador; }
    
    // Sin ID
    public Resultado(int unNumero, int PP0, int PP1, Boolean AP0, Boolean AP1, Participante unGanador) {
        this.numResultado = unNumero;
        this.puntajeP0 = PP0;
        this.puntajeP1 = PP1;
        this.asistenciaP0 = AP0;
        this.asistenciaP1 = AP1;
        this.ganador = unGanador; } }