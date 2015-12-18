
package modelo;


public class TablaPosicionesAux {
    
    private String nombreParticipante;
    private int puntos;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;
    private int tantoEnContra;
    private int tantoAFavor;
   
    public TablaPosicionesAux(String NombreParticipante, int puntos, int partidosGanados, int partidosPerdidos, int partidosEmpatados, int tantoEnContra, int tantoAFavor  ){
        
        this.nombreParticipante=NombreParticipante;
        this.puntos=puntos;
        this.partidosGanados=partidosGanados;
        this.partidosPerdidos=partidosPerdidos;
        this.partidosEmpatados=partidosEmpatados;
        this.tantoEnContra=tantoEnContra;
        this.tantoAFavor=tantoAFavor;
    }
    
     public String getNombre() {
        return nombreParticipante;
    }
    
    public int getPuntos() {
        return puntos;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public int getTantoEnContra() {
        return tantoEnContra;
    }

    public int getTantoAFavor() {
        return tantoAFavor;
    }
}
