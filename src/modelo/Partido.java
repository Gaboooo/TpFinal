package modelo;

import java.util.*;

public class Partido {
    
    int id;
    private final Participante P0;
    private final Participante P1;
    private final LugarRealizacion lugarRealizacion;
    private ArrayList<Resultado> listaResultados;
    ArrayList<HistorialResultado> historialRes;
    private Participante ganador;
    private Boolean empate;
    // Solo para modalidad de eliminacion
    // private String anterior1;
    // private String anterior2;
    
    public String getResultadoString(String formaP){
        String retorno=" ";
        Resultado res;
        if(!listaResultados.isEmpty()){
            switch (formaP) {
                case "Sets":
                    for(int i=0; i<listaResultados.size(); i++){
                        res= listaResultados.get(i);
                        retorno+="("+res.getResultadoString()+") ";
                    }
                    break;
                case "Resultado Final":
                    if(listaResultados.get(0).getGanador()==null){
                        retorno+= "Empate";
                    }
                    else{
                        retorno+="Ganó "+ listaResultados.get(0).getGanadorString();
                    }
                    break;
                case "Puntuacion":
                    retorno+=listaResultados.get(0).getResultadoString();
                    break;
                default:
                    break;
            }
        }
        return retorno;
    }
    
    public Participante getParticipantePorIndice(int indiceParticipante){
        switch (indiceParticipante) {
            case 0:
                return P0;
            case 1:
                return P1;
            default:
                return null;
        }
    }
    
    public int getID() {
        return id; 
    }
    
    public Participante getP0() {
        return P0; 
    }
    
    public Participante getP1() {
        return P1; 
    }
    
    public LugarRealizacion getLR() {
        return lugarRealizacion; 
    }
    
    public ArrayList<Resultado> getListaResultados() {
        return listaResultados; 
    }
    
    public ArrayList<HistorialResultado> getHistorialResultado() {
        return historialRes; 
    }
    
    public Participante getGanador() {
        return ganador; 
    }
    
    public Boolean getEmpate() {
        return empate; 
    }
    
    public void setID(int unaID) {
        this.id = unaID; 
    }
    
    public void setGanador(Participante unParticipante) {
        this.ganador = unParticipante; 
    }
    
    public void setEmpate(Boolean unBooleano){
        this.empate = unBooleano; 
    }
    
    public void setListaResultados(ArrayList<Resultado> unaLista) {
        this.listaResultados = unaLista; 
    }
    
    public void setHistorialResultado(ArrayList<HistorialResultado> unaLista) {
        this.historialRes = unaLista; 
    }
    
    // Con ID
    public Partido(int unaID, Participante P0, Participante P1, LugarRealizacion LR, ArrayList<Resultado> unaLista, Participante unGanador, Boolean huboEmpate) {
        this.id = unaID;
        this.P0 = P0;
        this.P1 = P1; 
        this.lugarRealizacion = LR;
        this.listaResultados = unaLista;
        this.historialRes = new ArrayList<>();
        this.ganador = unGanador;
        this.empate = huboEmpate; 
    }
        
    // Sin ID - REVISAR!
    public Partido(Participante P0, Participante P1, LugarRealizacion LR) {
        this.P0 = P0;
        this.P1 = P1; 
        this.lugarRealizacion = LR;
        this.listaResultados = new ArrayList<>();
        this.historialRes = new ArrayList<>();
        this.ganador = null;
        this.empate = null; 
    } 
}
