/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Martin
 */
public class PartidoAuxProxEncuentro {
    public String lugar;
    public String participante1;
    public String participante2;

    public PartidoAuxProxEncuentro(String numeroRonda, String participante1, String participante2) {
        this.lugar = numeroRonda;
        this.participante1 = participante1;
        this.participante2 = participante2;
    }

    public String getLugar() {
        return lugar;
    }

    public String getParticipante1() {
        return participante1;
    }

    public String getParticipante2() {
        return participante2;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setParticipante1(String participante1) {
        this.participante1 = participante1;
    }

    public void setParticipante2(String participante2) {
        this.participante2 = participante2;
    }
    
    
}
