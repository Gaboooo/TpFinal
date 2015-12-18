package gestor;

import DAO.*;
import modelo.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GenerarFixtureGestor {
    // Llamado desde Ventanas:ListarParticipantes.java
    public static void deleteFixture(CompetenciaAux unaCompetenciaAux) {
        GenerarFixtureDAO.deleteFixture(unaCompetenciaAux.getId()); }
    
    public static int randomInt(int min, int max) {
        // Error ??????? java.lang.IllegalArgumentException: bound must be greater than origin
        int rand = ThreadLocalRandom.current().nextInt(min, max+1);
        return rand; }
    
    public static ArrayList<ArrayList<Participante>> crearLLPA(ArrayList<Participante> unaLP) {
        // Lista auxiliar de listas de participantes
        ArrayList<ArrayList<Participante>> LLPA = new ArrayList();
        for (int i=0; i<unaLP.size(); i++) {
            for (int j=i+1; j<unaLP.size(); j++) {
                // Lista auxiliar de participantes
                ArrayList<Participante> LPA = new ArrayList();
                LPA.add(unaLP.get(i));
                LPA.add(unaLP.get(j));
                LLPA.add(LPA); } }
        return LLPA; }
    
    // Solo modalidad LIGA
    public static void generarFixture(CompetenciaAux unaCompetenciaAux) {
        // Objetos existentes
        Competencia unaCompetencia = GenerarFixtureDAO.getCompetencia(unaCompetenciaAux);
        ArrayList<Participante> listaParticipantes = unaCompetencia.getListaParticipantes();
        ArrayList<Disponibilidad> listaDisponibilidades = unaCompetencia.getListaDisponibilidades();
        // Nuevos objetos
        ArrayList<Ronda> listaRondas = new ArrayList();
        ArrayList<ArrayList<Participante>> LLPA = crearLLPA(listaParticipantes);
        Estado unEstado = unaCompetencia.getEstado();
        if ("Creada".equals(unEstado.getNombre()) || "Planificada".equals(unEstado.getNombre())) {
            for (int i=0; i<listaParticipantes.size()-1; i++) {
                ArrayList<Partido> listaPartidos = new ArrayList();
                ArrayList<Integer> listaIDParticipantesJugandoRonda = GenerarFixtureDAO.getIDParticipantesJugandoRonda(i);
                for (int j=0; j<listaParticipantes.size()/2; j++) {
                    // Participantes
                    ArrayList<Participante> LPA;
                    int PIndex;
                    do {
                        PIndex = randomInt(0, LLPA.size()-1);
                        LPA = LLPA.get(PIndex); } while (listaIDParticipantesJugandoRonda.contains(LPA.get(0).getID()) ||
                                                      listaIDParticipantesJugandoRonda.contains(LPA.get(1).getID()));
                    int LPAIndex = randomInt(0, LPA.size()-1);
                    Participante P0 = LPA.get(LPAIndex);
                    Participante P1;
                    if (LPAIndex == 0) { P1 = LPA.get(1); }
                    else { P1 = LPA.get(0); }
                    LLPA.remove(PIndex);
                    // Lugar de realizacion
                    int LRIndex = randomInt(0, listaDisponibilidades.size()-1);
                    LugarRealizacion unLugar = listaDisponibilidades.get(LRIndex).getLR();
                    // Creacion del partido
                    Partido unPartido = new Partido(P0, P1, unLugar);
                    listaPartidos.add(unPartido); }
                // Creacion de la ronda
                Ronda unaRonda = new Ronda(i, false, null, listaPartidos);
                listaRondas.add(unaRonda); }
            // Creacion del fixture
            Fixture unFixture = new Fixture(listaRondas);
            unaCompetencia.setFixture(unFixture);
            // Persistencia
            if ("Creada".equals(unEstado.getNombre())) {
                Estado nuevoEstado = GenerarFixtureDAO.getEstado("Planificada");
                unaCompetencia.setEstado(nuevoEstado);
                GenerarFixtureDAO.setEstado(unaCompetencia, nuevoEstado); }
            else {
                GenerarFixtureDAO.deleteFixture(unaCompetencia.getID()); }
            unaCompetencia.setFixture(unFixture);
            GenerarFixtureDAO.persistirFixture(unaCompetencia, unFixture); } } }
