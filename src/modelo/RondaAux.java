
package modelo;

import java.util.ArrayList;


public class RondaAux {
    public int id;
    public int numRonda;
    public String fecha;
    public ArrayList<PartidoAux> partidos;

    public RondaAux(int id, int numRonda, String fecha, ArrayList<PartidoAux> partidos) {
        this.id = id;
        this.numRonda = numRonda;
        this.fecha = fecha;
        this.partidos = partidos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumRonda(int numRonda) {
        this.numRonda = numRonda;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPartidos(ArrayList<PartidoAux> partidos) {
        this.partidos = partidos;
    }
    
    public int getId() {
        return id;
    }

    public int getNumRonda() {
        return numRonda;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<PartidoAux> getPartidos() {
        return partidos;
    }
    
}
