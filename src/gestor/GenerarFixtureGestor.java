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
    public static void generarFixture2(CompetenciaAux unaCompetenciaAux) {
        
        // Objetos existentes
        Competencia unaCompetencia = GenerarFixtureDAO.getCompetencia(unaCompetenciaAux);
        ArrayList<Participante> listaParticipantes = unaCompetencia.getListaParticipantes();
        ArrayList<Disponibilidad> listaDisponibilidades = unaCompetencia.getListaDisponibilidades();
        
        // Rondas
        ArrayList<Ronda> listaRondas = new ArrayList();
        
        ArrayList<ArrayList<Participante>> listaAux=getMatcheo(listaParticipantes);
        
        // Lista de los pares de P1 vs P2
        ArrayList<ArrayList<Participante>> listaPares = armarMatcheoParticipantes(listaParticipantes);
        
        // En el caso de cantidad impar de participantes, un participante se deja fuera por ronda
        ArrayList<Participante> listaDejadosAfuera= new ArrayList();
        
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
                    
                    // Saca un par de la lista, dentro se verifica que los participantes no esten usados,
                    // y que se usen los participantes que se dejaron afuera en las rondas anteriores
                    parAux=getPar(listaPares, listaUsados, listaDejadosAfuera);
                    listaUsados.add(parAux.get(0));
                    listaUsados.add(parAux.get(1));
                    
                    // Se obtiene un lugar de realizacion aleatorio
                    Random rand = new Random();
                    int indice = rand.nextInt(listaDisponibilidades.size());
                    LugarRealizacion lugarRealizacion = listaDisponibilidades.get(indice).getLR();
                    
                    // Creacion del partido (sacando participante 1, participante 2, y el lugar obtenido
                    Partido unPartido = new Partido(parAux.get(0), parAux.get(1), lugarRealizacion);
                    listaPartidos.add(unPartido);
                }
                
                // Verificar cual es el participante dejado fuera
                ArrayList<Participante> listAux = new ArrayList<>(listaParticipantes);
                listAux.removeAll(listaUsados);
                if(!listAux.isEmpty()){
                    listaDejadosAfuera.add(listAux.get(0));
                }
                
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
    
    // Saca un par fijandose que no este utilizado
    private static ArrayList<Participante> getPar(ArrayList<ArrayList<Participante>> listaPares,
            ArrayList<Participante> listaUsados, ArrayList<Participante> listaDejadosAfuera){
        
        ArrayList<Participante> par= null;
        ArrayList<Participante> parAux;
        
        // Hay participantes que ya se han dejado afuera en rondas anteriores
        // Entonces hay que darles prioridades a estos
        
        // El par tiene dos participantes que se dejaron afuera
        if (listaDejadosAfuera.size()>1){
            for(int i=0; i<listaPares.size(); i++){
                parAux=listaPares.get(i);
                if(listaDejadosAfuera.contains(parAux.get(0)) && listaDejadosAfuera.contains(parAux.get(1))
                        && !listaUsados.contains(parAux.get(0)) && !listaUsados.contains(parAux.get(1))){
                    par=parAux;
                    listaPares.remove(parAux);
                    i+=listaPares.size();
                }
            }
        }
        // El par tiene un solo participante que se dejo afuera
        if (listaDejadosAfuera.size()>0 && par==null){
            for(int i=0; i<listaPares.size(); i++){
                parAux=listaPares.get(i);
                if((listaDejadosAfuera.contains(parAux.get(0)) || listaDejadosAfuera.contains(parAux.get(1)))
                        && !listaUsados.contains(parAux.get(0)) && !listaUsados.contains(parAux.get(1))){
                    par=parAux;
                    listaPares.remove(parAux);
                    i+=listaPares.size();
                }
            }
        }
        if (par==null){
            for(int i=0; i<listaPares.size(); i++){
                parAux=listaPares.get(i);
                if(!listaUsados.contains(parAux.get(0)) && !listaUsados.contains(parAux.get(1))){
                    par=parAux;
                    listaPares.remove(parAux);
                    i+=listaPares.size();
                }
            }
        }
        if (par==null){
            par=null;
        }
        return par;
    }
    
    
    public static void generarFixture(CompetenciaAux unaCompetenciaAux) {
        
        // Objetos existentes
        Competencia unaCompetencia = GenerarFixtureDAO.getCompetencia(unaCompetenciaAux);
        ArrayList<Participante> listaParticipantes = unaCompetencia.getListaParticipantes();
        ArrayList<Disponibilidad> listaDisponibilidades = unaCompetencia.getListaDisponibilidades();
        
        // Rondas
        ArrayList<Ronda> listaRondas = new ArrayList();
        
        // Lista de los pares de P1 vs P2
        ArrayList<ArrayList<Participante>> listaPares;
        
        
        Estado unEstado = unaCompetencia.getEstado();
        if ("Creada".equals(unEstado.getNombre()) || "Planificada".equals(unEstado.getNombre())) {
            
            
            // Obtener todos los pares de P1 vs P2
            listaPares = getMatcheo(listaParticipantes);
            
            // Cantidad de rondas
            int cantRondas;
            if(listaParticipantes.size()%2==0){
                cantRondas=listaParticipantes.size()-1;
            }
            else{
                cantRondas=listaParticipantes.size();
            }
            
            // Cantidad de partidos por ronda
            int cantPartidosPorRonda=((listaParticipantes.size()+1)/2);
            
            // Lista auxiliar para guardar la lista de partidos
            // (para hacer que las rondas se ordenen aleatoriamente)
            ArrayList<ArrayList<Partido>> listaAux=new ArrayList<>();
            
            // Creacion de los objetos, a partir de la lista de P1 vs P2
            
            // Para todas las rondas
            for (int i=0; i<cantRondas; i++){
                ArrayList<Partido> listaPartidos = new ArrayList();
                ArrayList<Participante> parAux;
                
                //Agregar X cantidad de partidos a cada ronda
                for(int j=0; j<cantPartidosPorRonda; j++){
                    
                    parAux=listaPares.remove(0);
                    if(!parAux.isEmpty()){
                        
                        // Se obtiene un lugar de realizacion aleatorio
                        Random rand = new Random();
                        int indice = rand.nextInt(listaDisponibilidades.size());
                        LugarRealizacion lugarRealizacion = listaDisponibilidades.get(indice).getLR();
                        
                        // Creacion del partido (sacando participante 1, participante 2, y el lugar obtenido
                        Partido unPartido = new Partido(parAux.get(0), parAux.get(1), lugarRealizacion);
                        listaPartidos.add(unPartido);
                    }
                }
                listaAux.add(listaPartidos);
            }
            
            // Creacion de las rondas (se hace aparte para ordenarlas aleatoriamente)
            // (los partidos se ordenan aleatoriamente al buscar los pares)
            Collections.shuffle(listaAux);
            for (int i=0; i<cantRondas; i++){
                // Creacion de cada ronda
                Ronda unaRonda = new Ronda(i, false, null, listaAux.get(i));
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
    
    private static ArrayList<ArrayList<Participante>> getMatcheo(ArrayList<Participante> participantes){
        
        //ArrayList<ArrayList<Participante>> rondas= new ArrayList<>();
        Collections.shuffle(participantes);
        
        int cantParticipantes=participantes.size();
       
        boolean impar=cantParticipantes%2!=0;
        if(impar){
            cantParticipantes++;
        }
        int cantPartidos=(cantParticipantes*(cantParticipantes-1))/2;//total de partidos de una ronda
        
        int modIF=(cantParticipantes/2);//para hacer mod cada inicio de fecha
        int indiceInverso=cantParticipantes-2;
        
        ArrayList<ArrayList<Participante>> listaPares = new ArrayList<>();
        
        
        // Todos los pares de Participante1 y Participante2 en orden
        for(int i=0;i<cantPartidos;i++){
            ArrayList<Participante> aux= new ArrayList<>();
            if (i%modIF==0){//seria el partido inicial de cada ronda
                //si es impar el numero de participantes
                if (impar){
                }
                else{
                    if(i%2==0){
                        aux.add(participantes.get(i%(cantParticipantes-1)));
                        aux.add(participantes.get(cantParticipantes-1));
                    }
                    else{
                        aux.add(participantes.get(i%(cantParticipantes-1)));
                        aux.add(participantes.get(cantParticipantes-1));
                    }
                }
            }
            else{
                aux.add(participantes.get(i%(cantParticipantes-1)));
                aux.add(participantes.get(indiceInverso));
                --indiceInverso;
                if (indiceInverso<0){
                    indiceInverso=cantParticipantes-2;
                }
            }
            listaPares.add(aux);
        }
        return listaPares;
    }
    
    
}
