package gestor;

import DAO.*;
import modelo.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenerarFixtureGestor {
    // Llamado desde Ventanas:ListarParticipantes.java
    public static void deleteFixture(CompetenciaAux unaCompetenciaAux) {
        GenerarFixtureDAO.deleteFixture(unaCompetenciaAux.getId()); }
    
    public static int randomInt(int min, int max) {
        // Error ??????? java.lang.IllegalArgumentException: bound must be greater than origin
        int rand = ThreadLocalRandom.current().nextInt(min, max+1);
        return rand; }
    
    // Crea todos los pares de P1 vs P2, desordenadamente, para que sean asignados a partidos
    public static ArrayList<ArrayList<Participante>> armarMatcheoParticipantes(
            ArrayList<Participante> listaParticipantes) {
        // Lista auxiliar de listas de participantes
        ArrayList<ArrayList<Participante>> lista = new ArrayList();
        ArrayList<Participante> listaAux=  (ArrayList<Participante>)listaParticipantes.clone();
        
        // De 1 hasta N (1 vs todos menos él mismo)
        while(listaAux.size()>1){
            
            Collections.shuffle(listaAux);
            Participante partAux= listaAux.remove(0);
            
            // 1 vs los demas
            for (int i=0; i<listaAux.size(); i++) {
                
                // Lista auxiliar de 2 participantes (un partido) se agrega a la lista de retorno
                ArrayList<Participante> parDeParticipantes = new ArrayList();
                parDeParticipantes.add(partAux); 
                parDeParticipantes.add(listaAux.get(i));
                lista.add(parDeParticipantes);
            }
        }
        Collections.shuffle(lista);
        return lista;
    }
    
    // Solo modalidad LIGA
    public static void generarFixture(CompetenciaAux unaCompetenciaAux) {
        
        // Objetos existentes
        Competencia unaCompetencia = GenerarFixtureDAO.getCompetencia(unaCompetenciaAux);
        ArrayList<Participante> listaParticipantes = unaCompetencia.getListaParticipantes();
        ArrayList<Disponibilidad> listaDisponibilidades = unaCompetencia.getListaDisponibilidades();
        
        // Rondas
        ArrayList<Ronda> listaRondas = new ArrayList();
        
        // Lista de los pares de P1 vs P2
        ArrayList<ArrayList<Participante>> listaPares = armarMatcheoParticipantes(listaParticipantes);
        
        // En el caso de cantidad impar de participantes, un participante se deja fuera por ronda
        Participante participanteDejadoFuera=null;
        
        Estado unEstado = unaCompetencia.getEstado();
        if ("Creada".equals(unEstado.getNombre()) || "Planificada".equals(unEstado.getNombre())) {
            
            // Cantidad de rondas
            int cantRondas=0;
            if(listaParticipantes.size()%2==0){
                cantRondas=listaParticipantes.size()-1;
            }
            else{
                cantRondas=listaParticipantes.size();
            }
            
            // Cantidad de partidos por ronda
            int cantPartidosPorRonda=listaParticipantes.size()/2;
            
            // Para todas las rondas
            for (int i=0; i<cantRondas; i++){
                ArrayList<Partido> listaPartidos = new ArrayList();
                ArrayList<Partido> listaPartidosAux = new ArrayList(); // Usado para sacar el par de cada partido
                ArrayList<Participante> listaUsados= new ArrayList<>();
                ArrayList<Participante> parAux;
                
                
                //Agregar X cantidad de partidos a cada ronda
                
                for(int j=0; j<cantPartidosPorRonda; j++){
                    
                    // Verificar que si un participante no se uso en la ronda anterior, se usa en esta
                    if(participanteDejadoFuera!=null){
                        parAux=getParObligatorio(listaPares, participanteDejadoFuera);
                        listaUsados.add(parAux.get(0));
                        listaUsados.add(parAux.get(1));
                        participanteDejadoFuera=null;
                    }
                    else{
                        // Verificar que no se repitan los participantes
                        parAux=getPar(listaPares, listaUsados);
                        listaUsados.add(parAux.get(0));
                        listaUsados.add(parAux.get(1));
                    }
                    
                    // Se obtiene un lugar de realizacion
                    Random rn = new Random();
                    int indice = rn.nextInt(listaDisponibilidades.size());
                    LugarRealizacion lugarRealizacion = listaDisponibilidades.get(indice).getLR();
                    
                    // Creacion del partido (sacando participante 1, participante 2, y el lugar obtenido
                    Partido unPartido = new Partido(parAux.get(0), parAux.get(1), lugarRealizacion);
                    listaPartidos.add(unPartido);
                }
                
                // Verificar cual es el participante dejado fuera
                ArrayList<Participante> listAux = new ArrayList<>(listaParticipantes);
                listAux.removeAll(listaUsados);
                if(!listAux.isEmpty()){
                    participanteDejadoFuera=listAux.get(0);
                }
                else participanteDejadoFuera=null;
                
                // Creacion de la ronda
                Ronda unaRonda = new Ronda(i, false, null, listaPartidos);
                listaRondas.add(unaRonda);
            }
            
            // Creacion del fixture
            Fixture unFixture = new Fixture(listaRondas);
            unaCompetencia.setFixture(unFixture);
            // Persistencia
            if ("Creada".equals(unEstado.getNombre())) {
                Estado nuevoEstado = GenerarFixtureDAO.getEstado("Planificada");
                unaCompetencia.setEstado(nuevoEstado);
                GenerarFixtureDAO.setEstado(unaCompetencia, nuevoEstado);
            }
            else {
                GenerarFixtureDAO.deleteFixture(unaCompetencia.getID());
            }
            unaCompetencia.setFixture(unFixture);
            GenerarFixtureDAO.persistirFixture(unaCompetencia, unFixture);
        }
    }
    
    // Saca un par de P1 vs P2 en el que se encuentra el participante pedido
    private static ArrayList<Participante> getParObligatorio(ArrayList<ArrayList<Participante>> listaPares,
            Participante participante){
        
        ArrayList<Participante> par= new ArrayList<>();
        ArrayList<Participante> parAux;
        
        for(int i=0; i<listaPares.size(); i++){
            parAux=listaPares.get(i);
            // Se encuentra en la primera o segunda posicion
            if(parAux.get(0)==participante || parAux.get(1)==participante){
                par=parAux;
                listaPares.remove(parAux);
                break;
            }
        }
        return par;
    }
    
    // Saca un par fijandose que no este utilizado
    private static ArrayList<Participante> getPar(ArrayList<ArrayList<Participante>> listaPares,
            ArrayList<Participante> listaUsados){
        
        ArrayList<Participante> par= new ArrayList<>();
        ArrayList<Participante> parAux;
        
        for(int i=0; i<listaPares.size(); i++){
            parAux=listaPares.get(i);
            // Se encuentra en la primera o segunda posicion
            if(!listaUsados.contains(parAux.get(0)) && !listaUsados.contains(parAux.get(1))){
                par=parAux;
                listaPares.remove(parAux);
                break;
            }
        }
        return par;
    }
    
}
