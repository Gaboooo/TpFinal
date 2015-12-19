package gestor;

import DAO.*;
import static DAO.CompetenciaDaoJDBC.cantidadPartidosCargados;
import static DAO.CompetenciaDaoJDBC.cantidadPartidosPorRonda;
import static DAO.CompetenciaDaoJDBC.getCompetenciaMostrarFixt;
import static DAO.CompetenciaDaoJDBC.getCompetenciaPorId;
import static DAO.CompetenciaDaoJDBC.getProximosEncuentros;
import java.util.ArrayList;
import modelo.*;

public class GestorCD {
    /**
     * @param IDCompetencia
     * @param nombre
     * @param deporte
     * @param modalidad
     * @param estado
     * @return */
    
    // Usado en Ventanas:listarCompetencias.java
    public static int obtenerIDCD (String nombre){
        return CompetenciaDaoJDBC.getIdCompetencia(nombre); }
    
    public static boolean sePuedeCargarRonda(int id, int ronda){
        int partidosCargados = cantidadPartidosCargados(id);
        int partidosPorRonda = cantidadPartidosPorRonda(id);
        if(partidosCargados<=((ronda)*partidosPorRonda) &&
                partidosCargados>= (ronda-1)*partidosPorRonda )
            return true;
        else return false;
    }
    
    public static ArrayList<PosicionAux> getPosicionesAux(int id_competencia){
         
        Competencia c=CompetenciaDaoJDBC.getCompetencia(id_competencia);
        
        ArrayList<PosicionAux> listaTablaAux;
            listaTablaAux = new ArrayList<> ();
        
        if("Finalizada".equals(c.getEstado().getNombre()) || "En disputa".equals(c.getEstado().getNombre()) && "Liga".equals(c.getModalidad().getNombre())){
            
	    //Buscar TablaPosiciones
            ArrayList<Posicion> listaTablaPosiciones = c.getTablaPosiciones();
 
            for(int i=0 ; i< listaTablaPosiciones.size();i++){
            
                PosicionAux tablaAux;
                
                //una tabla de Posicion de la lista listaTablaPosiciones  
                Posicion tpp=listaTablaPosiciones.get(i);
                
                tablaAux= new PosicionAux(tpp.getParticipante().getNombre(), tpp.getPuntaje(),
                        tpp.getPartidosGanados(),tpp.getPartidosPerdidos(),
                        tpp.getPartidosEmpatados(), 0, 0  );
     
                if("Puntuacion".equals(c.getFormaPuntuacion().getNombre())){
                    // Asignarle a TablaPosicionesAUX
                    tablaAux= new PosicionAux(tpp.getParticipante().getNombre(), tpp.getPuntaje(),
                        tpp.getPartidosGanados(),tpp.getPartidosPerdidos(),
                        tpp.getPartidosEmpatados(), tpp.getTantosEnContra(), tpp.getTantosAFavor()  );         
                }
                
                listaTablaAux.add(tablaAux);
            }    
        }
  
        return listaTablaAux;
    }
  
    // LISTAR COMPETENCIAS DEPORTIVAS
    public static ArrayList<CompetenciaAux> listarCD (String nombreCD, String nombreDeporte, String nombreModalidad, String nombreEstado) {
        
        // Se recuperan las competencias que coincidan con los filtros
        ArrayList<Competencia> competencias = CompetenciaDaoJDBC.getCompetencias(nombreCD,
                nombreDeporte, nombreModalidad, nombreEstado);
        
        
        // Se pasan los datos de las competencias a la estructura auxiliar para la interfaz
        
        // Nueva lista para devolver
        ArrayList<CompetenciaAux> listaAux= new ArrayList<> ();
        
        // Iteracion de la lista de competencias
        for(int i=0; i<competencias.size(); i++){
            
            Competencia compI= competencias.get(i);
            
            // Se crea una nueva Competencia auxiliar y se la agega a la lista
            CompetenciaAux compAux= new CompetenciaAux(compI.getEstado().getNombre(), 
                compI.getDeporte().getNombre(), compI.getModalidad().getNombre(), compI.getNombre(),
                compI.getFormaPuntuacion().getNombre(), compI.getID());
            listaAux.add(compAux);
            
            }
        
        return listaAux;
    }    
    
    // VERIFICAR QUE EL NOMBRE DE LA COMPETENCIA ES UNICO
    public static boolean verificarNombre(String nombreCD) {
        boolean nombreUsado = CompetenciaDaoJDBC.nombreUsado(nombreCD);
        return nombreUsado; }    
    
    
    // LISTAS DE STRINGS PARA LLENAR LOS COMBOBOX EN LISTAR COMPETENCIAS
    
    public static String[] getListaDeportes() {
        ArrayList<String> deportes = CompetenciaDaoJDBC.getListaDeportes();
        String[] vectorNombreDeportes = new String[deportes.size()];
        vectorNombreDeportes = deportes.toArray(vectorNombreDeportes);
        return vectorNombreDeportes; }
    
    public static String[] getListaLugares(String deporte) {
        ArrayList<String> lugares = CompetenciaDaoJDBC.getListaLugares(deporte);
        String[] vectorNombreLugares = new String[lugares.size()];
        vectorNombreLugares = lugares.toArray(vectorNombreLugares);
        return vectorNombreLugares; }
    
    public static String[] getListaModalidades () {
        ArrayList<String> modalidades = CompetenciaDaoJDBC.getListaModalidades();
        String[] vectorNombreModalidades = new String[modalidades.size()];
        vectorNombreModalidades = modalidades.toArray(vectorNombreModalidades);
        return vectorNombreModalidades; }
        
    public static String[] getListaPuntuaciones () {
        ArrayList<String> puntuaciones = CompetenciaDaoJDBC.getListaPuntuaciones();
        String[] vectorNombrePuntuaciones = new String[puntuaciones.size()];
        vectorNombrePuntuaciones = puntuaciones.toArray(vectorNombrePuntuaciones);
        return vectorNombrePuntuaciones; }
    
    // DAR DE ALTA COMPETENCIA DEPORTIVA

    /**
     *
     * @param nombre
     * @param reglamento
     * @param nombreDeporte
     * @param nombreModalidad
     * @param matrizLugares
     * @param nombreFormaPuntuacion
     * @param tantosPorAusenciaDeRival
     * @param cantMaximaDeSets
     * @param puntosPorVictoria
     * @param puntosPorPresentacion
     * @param empatePermitido
     * @param puntosPorEmpate
     */
    public static void darDeAltaCD(String nombre, String reglamento, String nombreDeporte, String nombreModalidad,
        String matrizLugares[][], String nombreFormaPuntuacion, int tantosPorAusenciaDeRival, int cantMaximaDeSets,
        int puntosPorVictoria, int puntosPorPresentacion, boolean empatePermitido, int puntosPorEmpate) {
        
        Estado unEstado = GenerarFixtureDAO.getEstado("Creada");
        Modalidad unaModalidad = CompetenciaDaoJDBC.getModalidadPorNombre(nombreModalidad);
        FormaPuntuacion unaFormaPuntuacion = CompetenciaDaoJDBC.getFormaPuntuacionPorNombre(nombreFormaPuntuacion);
        Deporte unDeporte = CompetenciaDaoJDBC.getDeportePorNombre(nombreDeporte);
        
        // Disponibilidades
        ArrayList<Disponibilidad> listaDisponibilidades = new ArrayList();
        for (int i=0; i<matrizLugares.length; i++) {
            String unNombreLR = (String)matrizLugares[i][0];
            LugarRealizacion unLR = GenerarFixtureDAO.getLR(unNombreLR);
            Disponibilidad unaDisponibilidad = new Disponibilidad(Integer.parseInt(matrizLugares[i][1]), unLR);
            listaDisponibilidades.add(unaDisponibilidad); }
        
        // CD
        Competencia nuevaCD = null;
        if ("Puntuacion".equals(nombreFormaPuntuacion)) {
            if ("Liga".equals(nombreModalidad)) {
                nuevaCD = new Competencia(nombre, reglamento, unDeporte, unaModalidad, unEstado, listaDisponibilidades,
                    unaFormaPuntuacion, 0, tantosPorAusenciaDeRival, puntosPorPresentacion,
                    puntosPorVictoria, empatePermitido, puntosPorEmpate); }
            else if ("Eliminatoria Simple".equals(nombreModalidad) || "Eliminatoria Doble".equals(nombreModalidad)) {
                nuevaCD = new Competencia(nombre, reglamento, unDeporte, unaModalidad, unEstado, listaDisponibilidades,
                    unaFormaPuntuacion, 0, tantosPorAusenciaDeRival, 0, 0, false, 0); } }
        else if ("Sets".equals(nombreFormaPuntuacion)) {
            if ("Liga".equals(nombreModalidad)) {
                nuevaCD = new Competencia(nombre, reglamento, unDeporte, unaModalidad, unEstado, listaDisponibilidades,
                    unaFormaPuntuacion, cantMaximaDeSets, 0, puntosPorPresentacion,
                    puntosPorVictoria, empatePermitido, puntosPorEmpate); }
            else if ("Eliminatoria Simple".equals(nombreModalidad) || "Eliminatoria Doble".equals(nombreModalidad)) {
                nuevaCD = new Competencia(nombre, reglamento, unDeporte, unaModalidad, unEstado, listaDisponibilidades,
                    unaFormaPuntuacion, cantMaximaDeSets, 0, 0, 0, false, 0); } }
        else if ("Resultado Final".equals(nombreFormaPuntuacion)) {
            if ("Liga".equals(nombreModalidad)) {
                nuevaCD = new Competencia(nombre, reglamento, unDeporte, unaModalidad, unEstado, listaDisponibilidades,
                    unaFormaPuntuacion, 0, 0, puntosPorPresentacion,
                    puntosPorVictoria, empatePermitido, puntosPorEmpate); }
            else if ("Eliminatoria Simple".equals(nombreModalidad) || "Eliminatoria Doble".equals(nombreModalidad)) {
                nuevaCD = new Competencia(nombre, reglamento, unDeporte, unaModalidad, unEstado, listaDisponibilidades,
                    unaFormaPuntuacion, 0, 0, 0, 0, false, 0); } }
        
        // Persistencia
        CompetenciaDaoJDBC.persistirCD(nuevaCD); }
    
    
    
    public static ArrayList<RondaAux> mostrarFixture(CompetenciaAux compAux){
        
        // Lista que se devuelve
        ArrayList<RondaAux> rondasAux= new ArrayList<>();
        
        // Se busca la competencia con datos de fixture
        Competencia comp = getCompetenciaMostrarFixt(compAux);
        
        // Se obtiene el fixture
        Fixture fixt=comp.getFixture();
        
        // Se obtiene la lista de rondas
        ArrayList<Ronda> rondas= fixt.getListaRondas();
        
        // Recorrer las rondas
        for(int i=0; i<rondas.size(); i++){
            
            //Partidos de la ronda actual
            ArrayList<Partido> partidos = rondas.get(i).getListaPartidos();
            //Partidos auxiliares
            ArrayList<PartidoAux> partidosAux = new ArrayList<>();
            
            // Recorrer los partidos
            for(int j=0; j<partidos.size(); j++){
                // Sacar los datos y asignarlos a un partido auxiliar
                Partido partidoActual=partidos.get(j); // Solo para evitar el partidos.get(j)
                int id=partidoActual.getID();
                String p1 = partidoActual.getP0().getNombre();
                String p2 = partidoActual.getP1().getNombre();
                String res= partidoActual.getResultadoString();
                PartidoAux partAux= new PartidoAux(id, p1, p2, res);
                partidosAux.add(partAux);
            }
            
            // Crear la ronda auxiliar y agregarla a la lista 
            RondaAux rondaAux= new RondaAux(rondas.get(i).getID(), rondas.get(i).getNumero(), "", partidosAux);
            rondasAux.add(rondaAux);
        }
        
        return rondasAux;
    }
    
    public static int getRondaActual(CompetenciaAux compAux){
        //1  Cantidad de partidos con resultado cargado
        int cantidadPartidosCargados = cantidadPartidosCargados(compAux.getId());
        int cantidadPartidosPorRonda = cantidadPartidosPorRonda(compAux.getId());
        
        //2   Comparar cantidad de partidos cargados, con la posibilidad de la ronda actual
        int numeroRondaActual= (cantidadPartidosCargados + cantidadPartidosPorRonda)/cantidadPartidosPorRonda;
        
        return numeroRondaActual;
    }
    public static ArrayList<PartidoAuxProxEncuentro> proximosEncuentros (CompetenciaAux compAux){
        ArrayList<PartidoAuxProxEncuentro> proximosEncuentros= new ArrayList<>();
        
        //Buscar la competencia con el fixture y resultados
        
        
        //Metodo para devolver los proximos encuentros
        ArrayList<Partido> partidos= getProximosEncuentros(compAux);
        
        // Recorrer los partidos y asignarlos al DTO
        for (int i=0; i<partidos.size(); i++){
            PartidoAuxProxEncuentro aux = new PartidoAuxProxEncuentro(partidos.get(i).getLR().getNombre(),
                    partidos.get(i).getP0().getNombre(), partidos.get(i).getP1().getNombre());
            proximosEncuentros.add(aux);
        }
        
        return proximosEncuentros;
    }
    
    public static CompetenciaAux getCompAuxMostrar(int id){
        CompetenciaAux compAux;
        
        Competencia comp= getCompetenciaPorId(id);
        
        String estadoAux= comp.getEstado().getNombre();
        String deporteAux= comp.getDeporte().getNombre();
        String modalidadAux= comp.getModalidad().getNombre();
        String nombreAux= comp.getNombre();
        String formaPAux= comp.getFormaPuntuacion().getNombre();
        
        compAux= new CompetenciaAux(estadoAux, deporteAux, modalidadAux, nombreAux, formaPAux, id);
        
        return compAux;
    }
    
}
