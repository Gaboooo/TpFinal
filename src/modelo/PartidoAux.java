/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class PartidoAux {
    public int id;
    public String fecha="";
    public String participante1;
    public String participante2;
    //public String lugarRealizacion;
    public String resultado;

    public PartidoAux(int IDPartido, String participante1, String participante2, String resultado) {
        this.id = IDPartido;
        this.participante1 = participante1;
        this.participante2 = participante2;
        this.resultado = resultado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setParticipante1(String participante1) {
        this.participante1 = participante1;
    }

    public void setParticipante2(String participante2) {
        this.participante2 = participante2;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getParticipante1() {
        return participante1;
    }

    public String getParticipante2() {
        return participante2;
    }

    public String getResultado() {
        return resultado;
    }
    
}
