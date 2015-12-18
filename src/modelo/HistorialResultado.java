package modelo;

import java.util.ArrayList;

public class HistorialResultado {
    // Si la competencia es por SETS, la lista tiene tamaño unaCompetencia.getCantidadMaximaDeSets()
    // Si la competencia es por PUNTUACION/RESULTADOFINAL, la lista tiene tamaño 1
    private int ID;
    private final ArrayList<Resultado> listaResultados;
    private final Participante ganador;
    private final Boolean empate;
    private final String fechaModificacion;
    private final String horaModificacion;
    
    public int getID() {
        return ID; }
    
    public ArrayList<Resultado> getListaResultados() {
        return listaResultados; }
    
    public Participante getGanador() {
        return ganador; }
    
    public Boolean getEmpate() {
        return empate; }

    public String getFechaModificacion() {
        return fechaModificacion; }

    public String getHoraModificacion() {
        return horaModificacion; }
    
    // Con ID
    public HistorialResultado(int unID, ArrayList<Resultado> unaListaResultados, Participante unGanador, Boolean empate, String unaFecha, String unaHora) {
        this.ID = unID;
        this.listaResultados = unaListaResultados;
        this.ganador = unGanador;
        this.empate = empate;
        this.fechaModificacion = unaFecha;
        this.horaModificacion = unaHora; }
    
    // Sin ID
    public HistorialResultado(ArrayList<Resultado> unaListaResultados, Participante unGanador, Boolean empate, String unaFecha, String unaHora) {
        this.listaResultados = unaListaResultados;
        this.ganador = unGanador;
        this.empate = empate;
        this.fechaModificacion = unaFecha;
        this.horaModificacion = unaHora; } }