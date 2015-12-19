/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestor;

import DAO.CompetenciaDaoJDBC;
import static DAO.CompetenciaDaoJDBC.getEstadoPorNombre;
import DAO.ParticipanteDao;
import static gestor.GestorCD.obtenerIDCD;
import java.io.FileInputStream;
import java.util.ArrayList;
import modelo.Competencia;
import modelo.CompetenciaAux;
import modelo.HistorialParticipante;
import modelo.Participante;
import modelo.ParticipanteAux;

/**
 *
 * @author Martin
 */
public class GestorParticipante {
    
    public static boolean verificarNombre (int id, String nombre) {
        boolean retorno =ParticipanteDao.nombreUsado(id, nombre);
        return retorno;
    }
    
    public static boolean verificarCorreo (int id, String correo) {
        boolean retorno= ParticipanteDao.correoUsado(id, correo);
        return retorno;
    }
    
    
    public static void altaParticipante(int idCD, String nombre, String correo, FileInputStream fis){
        // Nuevo participante
        ArrayList<HistorialParticipante> unHistorial = new ArrayList<>(); // * No implementado
        Participante participante= new Participante(nombre, correo, unHistorial);
        
        // Buscar competencia de la BD CON los participantes
        Competencia comp = DAO.CompetenciaDaoJDBC.getCompetenciaAltaP(idCD);
        
        // Agregar el participante a la competencia
        comp.addParticipante(participante);
        
        // Si esta planificada se pasa a creada y se elimina el fixture
        if("Planificada".equals(comp.getEstado().getNombre())){
            comp.setEstado(getEstadoPorNombre("Creada"));
            comp.setFixture(null);
        }
        
        // Persistir participante/competencia
        CompetenciaDaoJDBC.persistirCDP(comp);
    }
    
    public static ArrayList<ParticipanteAux> listarParticipantes (int idCD) {
        
        // Se obtiene la competencia CON los participantes
        
        // Se obtienen los participantes desde la competencia
        ArrayList<Participante> participantes;
        participantes = ParticipanteDao.getParticipantes(idCD);
        
        
        ArrayList<ParticipanteAux> participantesAux= new ArrayList <ParticipanteAux> ();
        ParticipanteAux aux;
        
        for(int i=0; i < participantes.size(); i++){
            
            aux= new ParticipanteAux(participantes.get(i).getNombre(),
                    participantes.get(i).getCorreoElectronico());
            participantesAux.add(aux);
            
        }
        
        return participantesAux;
    }
    
    public static int cantidadParticipantes(int idCD){
        return DAO.ParticipanteDao.cantidadParticipantes(idCD);
    }
    
}
