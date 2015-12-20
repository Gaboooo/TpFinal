package gestor;

import DAO.*;
import static DAO.CompetenciaDaoJDBC.cantRondas;
import static DAO.CompetenciaDaoJDBC.cantidadPartidosCargados;
import static DAO.CompetenciaDaoJDBC.cantidadPartidosPorRonda;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import modelo.*;
import java.util.ArrayList;
import java.util.Date;

public class GestionarFixtureGestor {
    public static int getCantSets(CompetenciaAux unaCDAUX) {
        int cantSets = GestionarFixtureDAO.getCantSets(unaCDAUX);
        return cantSets; }

    public static Boolean getEmpatePermitido(CompetenciaAux unaCDAUX) {
        Boolean empatePermitido = GestionarFixtureDAO.getEmpatePermitido(unaCDAUX);
        return empatePermitido; }
    
    public static Posicion getPosicion(ArrayList<Posicion> tablaPosiciones, Participante unParticipante) {
        
        Posicion unaPosicion1=null; 
        
        for (Posicion unaPosicion:tablaPosiciones) {
            // NOTA: Lo va a encontrar SI O SI
            if (unaPosicion.getParticipante().getID() == unParticipante.getID()) {
                unaPosicion1= unaPosicion;
            }
        }
            return unaPosicion1;
        }    
    
    /* public static void deleteListaResultadosAnteriores(ArrayList<Resultado> listaResultados) {
        for (Resultado unResultado/:listaResultados) {
            System.out.println("CHE PERO PENSAS BORRAR LOS RESULTADOS O NO?");
            GestionarFixtureDAO.deleteResultado(unResultado); } } */
    
    public static void deshacerCambiosEnTablaPosiciones(Competencia unaCompetencia, Partido unPartido) {
        ArrayList<HistorialResultado> listaHistorialResultados = unPartido.getHistorialResultado();
        HistorialResultado unHR = listaHistorialResultados.get(listaHistorialResultados.size()-1);
        ArrayList<Resultado> listaResultadosAnteriores = unHR.getListaResultados();
        System.out.println("Tamaño de la lista de resultados anteriores: " + listaResultadosAnteriores.size());
        ArrayList<Posicion> tablaPosiciones = unaCompetencia.getTablaPosiciones();
        Participante P0 = unPartido.getP0(); Participante P1 = unPartido.getP1();
        Participante unGanador = unPartido.getGanador();
        Posicion posicionP0 = getPosicion(tablaPosiciones, P0); Posicion posicionP1 = getPosicion(tablaPosiciones, P1);
        Boolean AP0 = listaResultadosAnteriores.get(0).getAsistenciaP0(); Boolean AP1 = listaResultadosAnteriores.get(0).getAsistenciaP1();
        
        // Asistencia: P0 ^ P1
        if (AP0 && AP1) {
            // Empate
            if (unGanador == null) {
                posicionP0.setPuntaje(posicionP0.getPuntaje() - unaCompetencia.getPuntosPorEmpate());
                posicionP1.setPuntaje(posicionP1.getPuntaje() - unaCompetencia.getPuntosPorEmpate());
                posicionP0.setPartidosEmpatados(posicionP0.getPartidosEmpatados() - 1);
                posicionP1.setPartidosEmpatados(posicionP1.getPartidosEmpatados() - 1);
                // formaPuntuacion: SETS - No puede haber empate
                if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                    Resultado unResultado = listaResultadosAnteriores.get(0);
                    System.out.println("tantosAFavorP0: " + unResultado.getPuntajeP0());
                    System.out.println("tantosAFavorP1: " + unResultado.getPuntajeP1());
                    posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - unResultado.getPuntajeP0());
                    posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - unResultado.getPuntajeP1());
                    posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - unResultado.getPuntajeP1());
                    posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - unResultado.getPuntajeP0()); }
                /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */ }
            else {
                // Ganador: P0
                if (unGanador.getID() == P0.getID()) {
                    posicionP0.setPuntaje(posicionP0.getPuntaje() - unaCompetencia.getPuntosPorVictoria());
                    posicionP0.setPartidosGanados(posicionP0.getPartidosGanados() - 1);
                    posicionP1.setPartidosPerdidos(posicionP1.getPartidosPerdidos() - 1);
                    // formaPuntuacion: SETS
                    if ("Sets".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                        for (Resultado unResultado:listaResultadosAnteriores) {
                            Participante ganadorSet = unResultado.getGanador();
                            if (ganadorSet.getID() == P0.getID()) {
                                posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - 1);
                                posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - 1); }
                            else {
                                posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - 1);
                                posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - 1); } } }
                    // formaPuntuacion: PUNTOS
                    else if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                        Resultado unResultado = listaResultadosAnteriores.get(0);
                        System.out.println("tantosAFavorP0: " + unResultado.getPuntajeP0());
                        System.out.println("tantosAFavorP1: " + unResultado.getPuntajeP1());
                        posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - unResultado.getPuntajeP0());
                        posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - unResultado.getPuntajeP1());
                        posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - unResultado.getPuntajeP1());
                        posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - unResultado.getPuntajeP0());
                    }
                    /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */ }
                // Ganador: P1
                else if (unGanador.getID() == P1.getID()) {
                    System.out.println("Che pero entro aca o no? III");
                    posicionP1.setPuntaje(posicionP1.getPuntaje() - unaCompetencia.getPuntosPorVictoria());
                    posicionP1.setPartidosGanados(posicionP1.getPartidosGanados() - 1);
                    posicionP0.setPartidosPerdidos(posicionP0.getPartidosPerdidos() - 1);
                    // formaPuntuacion: SETS
                    if ("Sets".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                        System.out.println("Che pero entro aca o no? IV");
                        for (Resultado unResultado:listaResultadosAnteriores) {
                            Participante ganadorSet = unResultado.getGanador();
                            if (ganadorSet.getID() == P0.getID()) {
                                posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - 1);
                                posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - 1); }
                            else {
                                posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - 1);
                                posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - 1); } } }
                    // formaPuntuacion: PUNTOS
                    else if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                        System.out.println("Che pero entro aca o no? IV");
                        Resultado unResultado = listaResultadosAnteriores.get(0);
                        System.out.println("tantosAFavorP0: " + unResultado.getPuntajeP0());
                        System.out.println("tantosAFavorP1: " + unResultado.getPuntajeP1());
                        posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - unResultado.getPuntajeP0());
                        posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - unResultado.getPuntajeP1());
                        posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - unResultado.getPuntajeP1());
                        posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - unResultado.getPuntajeP0()); }
                    /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */ } } }
        // Asistencia: P0
        else if (AP0 && !AP1) {
            System.out.println("Che pero entro aca o no? II");
            posicionP0.setPuntaje(posicionP0.getPuntaje() - unaCompetencia.getPuntosPorPresentacion());
            posicionP0.setPartidosGanados(posicionP0.getPartidosGanados() - 1);
            posicionP1.setPartidosPerdidos(posicionP1.getPartidosPerdidos() - 1);
            // formaPuntuacion: SETS - No tiene tantos
            /* Comento como BACKUP
            if ("Sets".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                System.out.println("Che pero entro aca o no? IV");
                for (Resultado unResultado:listaResultadosAnteriores) {
                    Participante ganadorSet = unResultado.getGanador();
                    if (ganadorSet == P0) {
                        posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - 1);
                        posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - 1); }
                    else {
                        posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - 1);
                        posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - 1); } } } */
            // formaPuntuacion: PUNTOS
            if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                System.out.println("Che pero entro aca o no? IV");
                System.out.println("tantosAFavorP0: " + unaCompetencia.getTantosPorAusenciaDeRival());
                posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - unaCompetencia.getTantosPorAusenciaDeRival());
                posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - unaCompetencia.getTantosPorAusenciaDeRival()); }
            /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */ }
        // Asistencia: P1
        else if (!AP0 && AP1) {
            System.out.println("Che pero entro aca o no? II");
            posicionP1.setPuntaje(posicionP1.getPuntaje() - unaCompetencia.getPuntosPorPresentacion());
            posicionP1.setPartidosGanados(posicionP1.getPartidosGanados() - 1);
            posicionP0.setPartidosPerdidos(posicionP0.getPartidosPerdidos() - 1);
            // formaPuntuacion: SETS - No tiene tantos
            /* Comento como BACKUP
            if ("Sets".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                System.out.println("Che pero entro aca o no? IV");
                for (Resultado unResultado:listaResultadosAnteriores) {
                    Participante ganadorSet = unResultado.getGanador();
                    if (ganadorSet == P0) {
                        posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() - 1);
                        posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() - 1); }
                    else {
                        posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - 1);
                        posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - 1); } } } */
                    // formaPuntuacion: PUNTOS
            if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                System.out.println("Che pero entro aca o no? IV");
                System.out.println("tantosAFavorP1: " + unaCompetencia.getTantosPorAusenciaDeRival());
                posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() - unaCompetencia.getTantosPorAusenciaDeRival());
                posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() - unaCompetencia.getTantosPorAusenciaDeRival()); }
            /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */ }
        /* NO existe un caso donde no asista ningun participante */
        // DAO: Update de posiciones en DB
        GestionarFixtureDAO.updatePosicion(unaCompetencia, P0, posicionP0);
        GestionarFixtureDAO.updatePosicion(unaCompetencia, P1, posicionP1); }
    
    
    public static String obtenerFechaActual() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    
    public static String obtenerHoraActual() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    
    public static void addResultadoToHistorial(Partido unPartido, ArrayList<Resultado> listaResultadosAnteriores) {
        ArrayList<HistorialResultado> listaHRs = unPartido.getHistorialResultado();
        Participante ganadorAnterior = unPartido.getGanador();
        Boolean empateAnterior = unPartido.getEmpate();
        String fechaGuardado = obtenerFechaActual();
        String horaGuardado = obtenerHoraActual();
        // JAVA: Añado un unHr a listaHRs
        HistorialResultado unHR = new HistorialResultado(listaResultadosAnteriores, ganadorAnterior, empateAnterior, fechaGuardado, horaGuardado);
        listaHRs.add(unHR);
        unPartido.setHistorialResultado(listaHRs);
        // DAO: Persisto unHR;
        GestionarFixtureDAO.persistirHistorialResultado(unPartido, unHR); }
    
    
    public static int getCantResultadosCargados(Ronda unaRonda) {
        int cant = 0;
        ArrayList<Partido> listaPartidos = unaRonda.getListaPartidos();
        for (Partido unPartido:listaPartidos) {
            if (!unPartido.getListaResultados().isEmpty()) {
                cant++;
            }
        }
        return cant;
    }
    
    
    public static void crearPosiciones(Competencia unaCompetencia) {
        ArrayList<Posicion> tablaPosiciones = new ArrayList<>();
        ArrayList<Participante> listaParticipantes = unaCompetencia.getListaParticipantes();
        for (Participante unParticipante:listaParticipantes) {
            Posicion unaPosicion = new Posicion(unParticipante, 0, 0, 0, 0, 0, 0);
            tablaPosiciones.add(unaPosicion);
            GestionarFixtureDAO.persistirPosicion(unaCompetencia, unaPosicion);
        } 
        unaCompetencia.setTablaPosiciones(tablaPosiciones);
    }
    
    
    public static void aplicarCambiosATablaPosiciones(Competencia unaCompetencia, Partido unPartido, ArrayList<Resultado> listaNuevosResultados) {
        ArrayList<Posicion> tablaPosiciones = unaCompetencia.getTablaPosiciones();
        Participante P0 = unPartido.getP0();
        Participante P1 = unPartido.getP1();
        Participante unGanador = unPartido.getGanador();
        Posicion posicionP0 = getPosicion(tablaPosiciones, P0);
        Posicion posicionP1 = getPosicion(tablaPosiciones, P1);
        Boolean asistenciaP0 = listaNuevosResultados.get(0).getAsistenciaP0();
        Boolean asistenciaP1 = listaNuevosResultados.get(0).getAsistenciaP1();
        
        // Asistencia: P0 ^ P1
        if (asistenciaP0 && asistenciaP1) {
            // Ganador: P0
            if (unGanador == P0) {
                posicionP0.setPuntaje(posicionP0.getPuntaje() + unaCompetencia.getPuntosPorVictoria());
                posicionP0.setPartidosGanados(posicionP0.getPartidosGanados() + 1);
                posicionP1.setPartidosPerdidos(posicionP1.getPartidosPerdidos() + 1);
                // formaPuntuacion: SETS
                if ("Sets".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                    for (Resultado unResultado:listaNuevosResultados) {
                        Participante ganadorSet = unResultado.getGanador();
                        if (ganadorSet == P0) {
                            posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() + 1);
                            posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() + 1);
                        }
                        else {
                            posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() + 1);
                            posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() + 1);
                        }
                    }
                }
                // formaPuntuacion: PUNTOS
                else if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                    Resultado unResultado = listaNuevosResultados.get(0); 
                    posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() + unResultado.getPuntajeP0());
                    posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() + unResultado.getPuntajeP1());
                    posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() + unResultado.getPuntajeP1());
                    posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() + unResultado.getPuntajeP0());
                }
                /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */
            }
            // Ganador: P1
            else if (unGanador == P1) {
                posicionP1.setPuntaje(posicionP1.getPuntaje() + unaCompetencia.getPuntosPorVictoria());
                posicionP1.setPartidosGanados(posicionP1.getPartidosGanados() + 1);
                posicionP0.setPartidosPerdidos(posicionP0.getPartidosPerdidos() + 1);
                // formaPuntuacion: SETS
                if ("Sets".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                    for (Resultado unResultado:listaNuevosResultados) {
                        Participante ganadorSet = unResultado.getGanador();
                        if (ganadorSet == P0) {
                            posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() + 1);
                            posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() + 1);
                        }
                        else {
                            posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() + 1);
                            posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() + 1);
                        }
                    }
                }
                // formaPuntuacion: PUNTOS
                else if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                    Resultado unResultado = listaNuevosResultados.get(0);
                    posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() + unResultado.getPuntajeP0());
                    posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() + unResultado.getPuntajeP1());
                    posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() + unResultado.getPuntajeP1());
                    posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() + unResultado.getPuntajeP0());
                }
                /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */
            }
            // Empate
            else if (unGanador == null) {
                posicionP0.setPuntaje(posicionP0.getPuntaje() + unaCompetencia.getPuntosPorEmpate());
                posicionP1.setPuntaje(posicionP1.getPuntaje() + unaCompetencia.getPuntosPorEmpate());
                posicionP0.setPartidosEmpatados(posicionP0.getPartidosEmpatados() + 1);
                posicionP1.setPartidosEmpatados(posicionP1.getPartidosEmpatados() + 1);
                // formaPuntuacion: SETS - No puede haber empate
                if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                    Resultado unResultado = listaNuevosResultados.get(0);
                    posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() + unResultado.getPuntajeP0());
                    posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() + unResultado.getPuntajeP1());
                    posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() + unResultado.getPuntajeP1());
                    posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() + unResultado.getPuntajeP0());
                }
                /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */
            }
        }
        // Asistencia: P0
        else if (asistenciaP0 && !asistenciaP1) {
            posicionP0.setPuntaje(posicionP0.getPuntaje() + unaCompetencia.getPuntosPorPresentacion());
            posicionP0.setPartidosGanados(posicionP0.getPartidosGanados() + 1);
            posicionP1.setPartidosPerdidos(posicionP1.getPartidosPerdidos() + 1);
            // formaPuntuacion: SETS - No tiene tantos
            // formaPuntuacion: PUNTOS
            if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                posicionP0.setTantosAFavor(posicionP0.getTantosAFavor() + unaCompetencia.getTantosPorAusenciaDeRival());
                posicionP1.setTantosEnContra(posicionP1.getTantosEnContra() + unaCompetencia.getTantosPorAusenciaDeRival());
            }
            /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */
        }    
        // Asistencia: P1
        else if (!asistenciaP0 && asistenciaP1) {
            posicionP1.setPuntaje(posicionP1.getPuntaje() + unaCompetencia.getPuntosPorPresentacion());
            posicionP1.setPartidosGanados(posicionP1.getPartidosGanados() + 1);
            posicionP0.setPartidosPerdidos(posicionP0.getPartidosPerdidos() + 1);
            // formaPuntuacion: SETS - No tiene tantos
            // formaPuntuacion: PUNTOS
            if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
                posicionP1.setTantosAFavor(posicionP1.getTantosAFavor() + unaCompetencia.getTantosPorAusenciaDeRival());
                posicionP0.setTantosEnContra(posicionP0.getTantosEnContra() + unaCompetencia.getTantosPorAusenciaDeRival()); }
            /* formaPuntuacion: RESULTADOFINAL - No tiene tantos */ }
        // NO existe un caso donde no asista ningun participante
        // DAO: Update de posiciones en DB
        GestionarFixtureDAO.updatePosicion(unaCompetencia, P0, posicionP0);
        GestionarFixtureDAO.updatePosicion(unaCompetencia, P1, posicionP1);
    }
    
    
    public static void addResultadoToPartido(Competencia unaCompetencia, Ronda unaRonda,
            Partido unPartido, ArrayList<Resultado> listaNuevosResultados) {
        Participante P0 = unPartido.getP0(); Participante P1 = unPartido.getP1();
        Boolean AP0 = listaNuevosResultados.get(0).getAsistenciaP0(); Boolean AP1 = listaNuevosResultados.get(0).getAsistenciaP1();
        
        if ("Sets".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
            if (AP0 && AP1) {
                int setsGanadosP0 = 0; int setsGanadosP1 = 0;
                int cantMaxSets = unaCompetencia.getCantidadMaximaDeSets();
                for (int i=0; i<cantMaxSets; i++) {
                    Resultado unResultado = listaNuevosResultados.get(i);
                    Participante ganadorSet = unResultado.getGanador();
                    if (ganadorSet.getID() == P0.getID()) { setsGanadosP0++; }
                    else { setsGanadosP1++; } }
                if (setsGanadosP0 > setsGanadosP1) {
                    unPartido.setGanador(P0);
                    unPartido.setEmpate(Boolean.FALSE); }
                else {
                    unPartido.setGanador(P1);
                    unPartido.setEmpate(Boolean.FALSE); } }
            else if (AP0 && !AP1) { unPartido.setGanador(P0); }
            else if (!AP0 && AP1) { unPartido.setGanador(P1); } }
        // Control de empatePermitido se realiza en INTERFAZ
        if ("Puntuacion".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
            Resultado unResultado = listaNuevosResultados.get(0);
            int puntajeP0 = unResultado.getPuntajeP0(); int puntajeP1 = unResultado.getPuntajeP1();
            if (AP0 && AP1) {
                if (puntajeP0 > puntajeP1) { 
                    unPartido.setGanador(P0);
                    unPartido.setEmpate(Boolean.FALSE); }
                else if (puntajeP1 > puntajeP0) { 
                    unPartido.setGanador(P1);
                    unPartido.setEmpate(Boolean.FALSE); }
                else if (puntajeP0 == puntajeP1) { 
                    unPartido.setGanador(null);
                    unPartido.setEmpate(Boolean.TRUE); } }
            else if (AP0 && !AP1) { 
                unPartido.setGanador(P0);
                unPartido.setEmpate(Boolean.FALSE); }
            else if (!AP0 && AP1) { 
                unPartido.setGanador(P1);
                unPartido.setEmpate(Boolean.FALSE); } }
        // Control de empatePermitido se realiza en INTERFAZ
        if ("Resultado Final".equals(unaCompetencia.getFormaPuntuacion().getNombre())) {
            Resultado unResultado = listaNuevosResultados.get(0);
            Participante ganador = unResultado.getGanador();
            if (AP0 && AP1) {
                if (ganador == null) { 
                    unPartido.setGanador(null);
                    unPartido.setEmpate(Boolean.TRUE); }
                else { 
                    unPartido.setGanador(ganador);
                    unPartido.setEmpate(Boolean.FALSE); } }
            else if (AP0 && !AP1) { 
                unPartido.setGanador(P0);
                unPartido.setEmpate(Boolean.FALSE); }
            else if (!AP0 && AP1) { 
                unPartido.setGanador(P1);
                unPartido.setEmpate(Boolean.FALSE); } }
        // JAVA: Asigno listaNuevosResultados como nuevo resultado del partido
        unPartido.setListaResultados(listaNuevosResultados);
        // DAO: Persisto listaNuevosResultados
        for (Resultado unResultado:listaNuevosResultados) {
            GestionarFixtureDAO.persistirResultado(unPartido, unResultado); } 
        // DAO: Update de Partido. Cambio el ganador/empate
        GestionarFixtureDAO.setGanadorAndEmpate(unPartido); }
    
    // Modalidad: LIGA
    public static void gestionarFixture(CompetenciaAux unaCompetenciaAux,
            RondaAux unaRondaAux, PartidoAux unPartidoAux,
            ArrayList<ResultadoAux> listaNuevosResultadosAux) {
        //Se obtiene la competencia y su tabla de posiciones
        Competencia unaCompetencia = GenerarFixtureDAO.getCompetencia(unaCompetenciaAux);
        ArrayList<Posicion> posiciones=DAO.CompetenciaDaoJDBC.getTablaPosiciones(unaCompetencia.getID());
        unaCompetencia.setTablaPosiciones(posiciones);
        Ronda unaRonda = GestionarFixtureDAO.getRonda(unaRondaAux);
        Partido unPartido = GestionarFixtureDAO.getPartido(unPartidoAux);
        ArrayList<Resultado> listaResultadosAnteriores = unPartido.getListaResultados();
        ArrayList<Resultado> listaNuevosResultados = new ArrayList<>();
        // Creo la lista de nuevos resultados (NO auxiliares)
        for (ResultadoAux unResultadoAux:listaNuevosResultadosAux) {
            int unNumero = unResultadoAux.getNumero();
            int PP0 = unResultadoAux.getPuntajeP0();
            int PP1 = unResultadoAux.getPuntajeP1();
            Boolean AP0 = unResultadoAux.getAsistenciaP0();
            Boolean AP1 = unResultadoAux.getAsistenciaP1();
            int indiceGanador = unResultadoAux.getIndiceParticipante();
            Resultado unResultado;
            if(indiceGanador == 2){
                unResultado = new Resultado(unNumero, PP0, PP1, AP0, AP1, null); 
            }
            else{
                Participante ganador = unaCompetencia.getListaParticipantes().get(indiceGanador);
                unResultado = new Resultado(unNumero, PP0, PP1, AP0, AP1, ganador);
            }
            
            listaNuevosResultados.add(unResultado);
        }
        // Si ya tengo resultados cargados
        if (!listaResultadosAnteriores.isEmpty()) {
            System.out.println("listaResultadosAnteriores != empty");
            // Agregar viejoResultado a historial
            addResultadoToHistorial(unPartido, listaResultadosAnteriores);
            // Deshacer cambios de viejoResultado en tablaPosiciones
            deshacerCambiosEnTablaPosiciones(unaCompetencia, unPartido);
        }
        // Agregar nuevoResultado a partido
        addResultadoToPartido(unaCompetencia, unaRonda, unPartido, listaNuevosResultados);
        // Si es el PRIMER resultado, el estado pasa a enDisputa...
        if ("Planificada".equals(unaCompetencia.getEstado().getNombre())) {
            Estado nuevoEstado = GenerarFixtureDAO.getEstado("En disputa");
            unaCompetencia.setEstado(nuevoEstado);
            GenerarFixtureDAO.setEstado(unaCompetencia, nuevoEstado);
            // ... Y creo las posiciones de tablaPosiciones
            crearPosiciones(unaCompetencia);
        }
        
        // Si es el ULTIMO resultado, el estado pasa a finalizada
        int cantPartidosCargados = cantidadPartidosCargados(unaCompetencia.getID());
        int cantPartidosTotal=cantidadPartidosPorRonda(unaCompetencia.getID())*
                cantRondas(unaCompetenciaAux.getId());
        /*numRonda == cantParticipantes-1 && cantResultadosCargados == cantParticipantes/2*/
        if (cantPartidosCargados == cantPartidosTotal) {
            Estado nuevoEstado = GenerarFixtureDAO.getEstado("Finalizada");
            unaCompetencia.setEstado(nuevoEstado);
            GenerarFixtureDAO.setEstado(unaCompetencia, nuevoEstado);
        }
        
        // Aplicar cambios de nuevoResultado a tablaPosiciones
        aplicarCambiosATablaPosiciones(unaCompetencia, unPartido, listaNuevosResultados);
    }
    
    
    public static ArrayList<ResultadoAux> getResultados(PartidoAux partidoAux){
        ArrayList<ResultadoAux> retorno= new ArrayList<>();
        
        ArrayList<Resultado> resultados=DAO.GestionarFixtureDAO.getResultados(partidoAux.getId());
        Resultado resI;
        ResultadoAux aux;
        for(int i=0; i<resultados.size(); i++){
            resI=resultados.get(i);
            
            //Verificacion del ganador (en caso de resultado final)
            int indiceGanador;
            if(partidoAux.getParticipante1().equals(resI.getGanador().getNombre())){
                indiceGanador=0;
            }
            else if(partidoAux.getParticipante2().equals(resI.getGanador().getNombre())){
                indiceGanador=1;
            }
            else indiceGanador=2;
            
            //(int unID, int unNumero, int PP0, int PP1, Boolean AP0, Boolean AP1, int unIndice)
            aux= new ResultadoAux(resI.getId(), resI.getNumero(), resI.getPuntajeP0(),
                    resI.getPuntajeP1(), resI.getAsistenciaP0(), resI.getAsistenciaP1(), indiceGanador);
            
            retorno.add(aux);
        }
        
        return retorno;
        
        
    }
    
}
