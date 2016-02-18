package DAO;

import static DAO.GenerarFixtureDAO.deleteFixture;
import static DAO.GenerarFixtureDAO.getDeporte;
import static DAO.GestionarFixtureDAO.getFixture;
import static DAO.GestionarFixtureDAO.getPartidos;
import static DAO.ParticipanteDao.persistirParticipante;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.*;

public class CompetenciaDaoJDBC {
    
    public static Competencia getCompetencia(int id_competencia){
    
        Competencia unaCompetencia=null;
 
        Connection conn = null; 
          
        try{
        
        conn = DBConnection.get();

        Statement stmt = conn.createStatement();
         
        String SQL_FIND_COMPETENCIA = "SELECT * FROM competencia WHERE id_competencia = '"+ id_competencia +"'";
        
        ResultSet  rs=stmt.executeQuery(SQL_FIND_COMPETENCIA);
        
        rs.next();
        
        ArrayList<Posicion> tablaPosiciones=getTablaPosiciones(id_competencia);
        
        String nombre= rs.getString("nombre");
        String reglamento= rs.getString("reglamento");
        
         int IdDeporte=rs.getInt("id_deporte");
         int IdModalidad=rs.getInt("id_modalidad");
         int IdEstado=rs.getInt("id_estado");
         int IDFormaPuntuacion=rs.getInt("id_forma_puntuacion");
         
         int PuntosPorEmpate=rs.getInt("puntos_por_empate");
         
         boolean Empatepermitido= rs.getBoolean("empate_permitido");
         
            Deporte unDeporte = getDeportePorId(IdDeporte);
            Modalidad unaModalidad = getModalidadPorId(IdModalidad);
            Estado unEstado = getEstadoPorId(IdEstado);
            FormaPuntuacion unaFormaPuntuacion = getFormaPuntuacionPorId(IDFormaPuntuacion);
            
        
        unaCompetencia = new Competencia(id_competencia,nombre,reglamento,unDeporte,unaModalidad,unEstado,unaFormaPuntuacion,null,null,tablaPosiciones,0,0,0,0,Empatepermitido,PuntosPorEmpate);
        
        /*Competencia(int unaID, String nombre, String reglamento, Deporte deporte, Modalidad modalidad, Estado estado, FormaPuntuacion formaPuntuacion,
                       ArrayList<Disponibilidad> listaDisponibilidades, ArrayList<Participante> listaParticipantes, ArrayList<Posicion> unaTablaPosiciones,
                       int cantidadMaximaDeSets, int tantosPorAusenciaDeRival, int puntosPorPresentacion,
                       int puntosPorVictoria, boolean empatePermitido, int puntosPorEmpate)*/ 
        
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 

        return unaCompetencia;
    } 
    
    
    public static ArrayList<Posicion> getTablaPosiciones (int id_competencia){
        
        ArrayList<Posicion> tps= new ArrayList <> ();
        
        Connection conn = null; 
        
        int id,puntos,partidosGanados,partidosPerdidos,partidosEmpatados,tantoEnContra,tantoAFavor;
        
        try{
            conn = DBConnection.get();
            
            Statement statement = conn.createStatement();
            
            String SQL_FIND_TABLAPOSICIONES = "SELECT * FROM posicion WHERE id_competencia ='"+ id_competencia+"'";
            ResultSet rs = statement.executeQuery(SQL_FIND_TABLAPOSICIONES);
            while(rs.next()){
                
                id=rs.getInt("id_posicion");
                puntos=rs.getInt("puntaje");
                partidosGanados=rs.getInt("partidos_ganados");
                partidosPerdidos=rs.getInt("partidos_perdidos");
                partidosEmpatados=rs.getInt("partidos_empatados");
                tantoAFavor=rs.getInt("tantos_a_favor");
                tantoEnContra=rs.getInt("tantos_en_contra");
                Participante unParticipante = getParticipante(rs.getInt("id_participante"));
                Posicion pp= new Posicion(id,unParticipante,puntos,partidosGanados,partidosPerdidos,partidosEmpatados,tantoAFavor,tantoEnContra);
                
                tps.add(pp);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally {
            if (conn!=null) try {
                conn.close(); }
            catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); } 
        }
        
        return tps;
    }
    
    public static Participante getParticipante(int id_participante){
    
        String SQL_FK_PARTICIPANTE ="SELECT * FROM participante WHERE id_participante = '" + id_participante + "' ";
        
        Connection conn = null; 
        
        Participante unParticipante= null;
        
        try{
        
        conn = DBConnection.get();
        
        ResultSet  rs;
        
        Statement stmt = conn.createStatement();
         
        rs=stmt.executeQuery(SQL_FK_PARTICIPANTE);
        
        rs.next();
        String nomb=rs.getString("nombre");
        
        unParticipante = new Participante(id_participante,nomb,null,null );
        
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
     
        return unParticipante;
    }
    
    
    
    public static ArrayList<Partido> getProximosEncuentros(CompetenciaAux compAux){
        ArrayList<Partido> partidos = new ArrayList<>();
        
        // Todos los partidos que pertenezcan a la ronda actual
        //////////////////////////////////////////////////////
        
        //1  Cantidad de partidos con resultado cargado
        int cantidadPartidosCargados = cantidadPartidosCargados(compAux.getId());
        int cantidadPartidosPorRonda = cantidadPartidosPorRonda(compAux.getId());
        
        //2   Comparar cantidad de partidos cargados, con la posibilidad de la ronda actual
        int numeroRondaActual= (cantidadPartidosCargados + cantidadPartidosPorRonda)/cantidadPartidosPorRonda;
        if(numeroRondaActual>cantRondas(compAux.getId()))
            numeroRondaActual--;
        
        //3 Buscar en la bd todos los partidos de la ronda con numero ACTUAL 'numeroRondaActual'
        int idRonda=getIdRondaPorNumero(numeroRondaActual, compAux.getId());
        
        partidos=getPartidos(idRonda);
        
        return partidos;
    }
    
    public static int cantidadPartidosCargados(int id){
        int cantidad=1;
        Connection conn = null;
        String SQL_CANT_CARGADOS ="SELECT count(DISTINCT p.id_partido) AS total " +
                             "FROM partido AS p " +
                             "JOIN ronda AS r ON r.id_ronda=p.id_ronda " +
                             "JOIN fixture AS f ON f.id_fixture=r.id_fixture " +
                             "JOIN competencia AS c ON c.id_competencia=f.id_competencia " +
                             "JOIN resultado AS res ON res.id_partido=p.id_partido " +
                             "WHERE c.id_competencia="+id;
        
        try{
            conn = DBConnection.get();
            ResultSet  rs;
            Statement stmt = conn.createStatement();
            rs=stmt.executeQuery(SQL_CANT_CARGADOS);
            rs.next();
            cantidad=rs.getInt("total");
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cantidad;
    }
    public static int cantidadPartidosPorRonda(int id){
        int cantidad=1;
        Connection conn = null;
        String SQL_CANT_PART_POR_RONDA ="SELECT count (p.id_ronda) AS total " +
                                        "FROM partido AS p " +
                                        "JOIN ronda AS r ON r.id_ronda=p.id_ronda " +
                                        "JOIN fixture AS f ON f.id_fixture=r.id_fixture " +
                                        "JOIN competencia AS c ON c.id_competencia=f.id_competencia " +
                                        "WHERE r.numero_ronda=0 AND c.id_competencia="+id; 
        
        try{
            conn = DBConnection.get();
            ResultSet  rs;
            Statement stmt = conn.createStatement();
            rs=stmt.executeQuery(SQL_CANT_PART_POR_RONDA);
            rs.next();
            cantidad=rs.getInt("total");
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cantidad;
    }
    public static int getIdRondaPorNumero(int numero, int idComp){
        
        Connection conn = null;
        String SQL_ID_RONDA ="SELECT r.id_ronda AS result " +
                             "FROM ronda AS r " +
                             "JOIN fixture AS f ON f.id_fixture=r.id_fixture " +
                             "JOIN competencia AS c ON c.id_competencia=f.id_competencia " +
                             "WHERE r.numero_ronda="+ (numero-1) +" AND c.id_competencia="+ idComp;
        
        int idRonda=0;
        
        try{
            conn = DBConnection.get();
            
            Statement stmt = conn.createStatement();
            ResultSet  rs=stmt.executeQuery(SQL_ID_RONDA);
           
            while(rs.next()){
                idRonda=rs.getInt("result");
            }
            
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return idRonda;
    }
    public static int cantRondas(int id){
        int cantidad=1;
        Connection conn = null;
        String SQL_CANT_PART_POR_RONDA ="SELECT count (r.id_ronda) AS total " +
                                        "FROM ronda AS r " +
                                        "JOIN fixture AS f ON f.id_fixture=r.id_fixture " +
                                        "JOIN competencia AS c ON c.id_competencia=f.id_competencia " +
                                        "WHERE c.id_competencia="+id; 
        
        try{
            conn = DBConnection.get();
            ResultSet  rs;
            Statement stmt = conn.createStatement();
            rs=stmt.executeQuery(SQL_CANT_PART_POR_RONDA);
            rs.next();
            cantidad=rs.getInt("total");
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return cantidad;
    }
    
    public static ArrayList<PosicionAux> getPosicionesAux(int unIDCD) {
        ArrayList<PosicionAux> listaPosiciones = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getPosiciones = "SELECT * FROM posicion WHERE id_competencia = " + unIDCD;
            ResultSet rs = statement.executeQuery(getPosiciones);
            // NOTA: El ResultSet tiene un solo resultado
            while (rs.next()) {
                int IDPosicion = rs.getInt("id_posicion");
                int IDParticipante = rs.getInt("id_participante");
                Participante unParticipante = GestionarFixtureDAO.getParticipante(IDParticipante);
                String nombreParticipante = unParticipante.getNombre();
                int puntos = rs.getInt("puntaje");
                int partidosGanados = rs.getInt("partidos_ganados");
                int partidosPerdidos = rs.getInt("partidos_perdidos");
                int partidosEmpatados = rs.getInt("partidos_empatados");
                int tantosAFavor = rs.getInt("tantos_a_favor");
                int tantosEnContra = rs.getInt("tantos_en_contra");
                PosicionAux unaPosicion = new PosicionAux(nombreParticipante, puntos, partidosGanados, partidosPerdidos, partidosEmpatados, tantosAFavor, tantosEnContra);
                listaPosiciones.add(unaPosicion); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return listaPosiciones; }
    
    public static Competencia getCompetenciaPorId(int id_competencia){
        
        Competencia c=null;
        Connection conn = null;
        
        String nombre;
        int IdDeporte,IdModalidad,IdEstado, IDFormaPuntuacion;
        
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            
            String SQL_FIND_COMPETENCIA = "SELECT * FROM competencia WHERE id_competencia = '" + id_competencia + "'";
            
            ResultSet rs = statement.executeQuery(SQL_FIND_COMPETENCIA);
            rs.next();
            nombre=rs.getString("nombre");
            IdDeporte=rs.getInt("id_deporte");
            IdModalidad=rs.getInt("id_modalidad");
            IdEstado=rs.getInt("id_estado");
            IDFormaPuntuacion=rs.getInt("id_forma_puntuacion");
            
            Deporte unDeporte = getDeportePorId(IdDeporte);
            Modalidad unaModalidad = getModalidadPorId(IdModalidad);
            Estado unEstado = getEstadoPorId(IdEstado);
            FormaPuntuacion unaFormaPuntuacion = getFormaPuntuacionPorId(IDFormaPuntuacion);
            
            c= new Competencia(id_competencia, nombre, unDeporte, unaModalidad, unEstado, unaFormaPuntuacion);
        }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        
        
        return c;
    }
    
    public static int getIdCompetencia(String nombre){
        
        int IdCompetencia = 0;
        
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            
            /*id_competencia*/
            String SQL_FIND_ID_COMPETENCIA = "SELECT id_competencia FROM competencia WHERE nombre = '"+ nombre +"'";
             
            ResultSet rs = statement.executeQuery(SQL_FIND_ID_COMPETENCIA);
            
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                 
                IdCompetencia = rs.getInt("id_competencia"); }

            rs.close(); 
        }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        
        return IdCompetencia;
    }
    
    // USADO PARA MOSTRAR FIXTURE
    public static Competencia getCompetenciaMostrarFixt(CompetenciaAux unaCDAUX) {
        Competencia unaCompetencia = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            // Variables auxiliares
            String unNombre = ""; String unReglamento = ""; int IDCompetencia = 0;
            int IDEstado = 0; int IDFormaPuntuacion = 0; int IDModalidad = 0; int IDDeporte = 0;
            Deporte unDeporte; Modalidad unaModalidad; Estado unEstado; FormaPuntuacion unaFormaPuntuacion;
            
            // Busqueda de la competencia
            String getCD = "SELECT * FROM competencia WHERE id_competencia = '" + unaCDAUX.getId() + "'";
            ResultSet rs = statement.executeQuery(getCD);
            while(rs.next()) {
                IDCompetencia = rs.getInt("id_competencia");
                IDEstado = rs.getInt("id_estado"); IDFormaPuntuacion = rs.getInt("id_forma_puntuacion");
                IDModalidad = rs.getInt("id_modalidad"); IDDeporte = rs.getInt("id_deporte");
                unNombre = rs.getString("nombre"); }
            unDeporte = getDeportePorId(IDDeporte);
            unaModalidad = getModalidadPorId(IDModalidad);
            unEstado = getEstadoPorId(IDEstado);
            unaFormaPuntuacion = getFormaPuntuacionPorId(IDFormaPuntuacion);
            
            Fixture fixture=getFixture(unaCDAUX.getId());
            
            // Creacion del retorno
            unaCompetencia = new Competencia(IDCompetencia, unNombre, unDeporte, unaModalidad,
                    unEstado, unaFormaPuntuacion, fixture);
            rs.close(); }
        catch (SQLException e) {
            System.out.println(e.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unaCompetencia; }
    
    // USADO PARA MOSTRAR FIXTURE
    public static Competencia getCompetenciaAltaP(int id) {
        Competencia unaCompetencia = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            // Variables auxiliares
            String unNombre = ""; int IDCompetencia = 0;
            int IDEstado = 0; int IDFormaPuntuacion = 0; int IDModalidad = 0; int IDDeporte = 0;
            Deporte unDeporte; Modalidad unaModalidad; Estado unEstado; FormaPuntuacion unaFormaPuntuacion;
            
            // Busqueda de la competencia
            String getCD = "SELECT * FROM competencia WHERE id_competencia = '" + id + "'";
            ResultSet rs = statement.executeQuery(getCD);
            while(rs.next()) {
                IDCompetencia = rs.getInt("id_competencia");
                IDEstado = rs.getInt("id_estado"); IDFormaPuntuacion = rs.getInt("id_forma_puntuacion");
                IDModalidad = rs.getInt("id_modalidad"); IDDeporte = rs.getInt("id_deporte");
                unNombre = rs.getString("nombre"); }
            unDeporte = getDeportePorId(IDDeporte);
            unaModalidad = getModalidadPorId(IDModalidad);
            unEstado = getEstadoPorId(IDEstado);
            unaFormaPuntuacion = getFormaPuntuacionPorId(IDFormaPuntuacion);
            
            ArrayList<Participante> participantes=ParticipanteDao.getParticipantes(id);
            //Boolean fixt = tieneFixture(id);
            
            // Creacion del retorno
            unaCompetencia = new Competencia(IDCompetencia, unNombre, unDeporte, unaModalidad,
                    unEstado, unaFormaPuntuacion, participantes, false);
            rs.close(); }
        catch (SQLException e) {
            System.out.println(e.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unaCompetencia;
    }
    
    public static void persistirCDP(Competencia comp){
        
        // Si tiene fixture, eliminarlo
        // Si el fixture en la BD esta, y aca es null:
        if(comp.getFixture()==null && tieneFixture(comp.getID())){
            // Eliminar fixture, y guardar el estado
            deleteFixture(comp.getID());
            persistirEstado(comp);
        }
        
        //Sacar el ultimo participante ingresado
        ArrayList<Participante> participantes=comp.getListaParticipantes();
        Participante part= participantes.get(participantes.size()-1);
        
        //Persistir el participante
        persistirParticipante(comp.getID(), part);
        
    }
    
    public static boolean tieneFixture(int id){
        
        String _SQL_FIND_NOMBRE_USADO = "SELECT COUNT(id_competencia) AS total " +
                                        "FROM fixture " +
                                        "WHERE id_competencia="+id;
        Connection conn = null;
        Boolean retorno = true;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(_SQL_FIND_NOMBRE_USADO);
            rs.next();
            if(rs.getInt("total")==0){ retorno = false; }
            rs.close(); }
        catch (SQLException e) {
            System.out.println(e.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return retorno;
    }
    
    public static void persistirEstado(Competencia comp){
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDCD = comp.getID();
            String updatePosicion = "UPDATE competencia "
                + " SET id_estado = " + comp.getEstado().getID()
                + " WHERE id_competencia = " + IDCD;
            statement.executeUpdate(updatePosicion); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage());}}
    }
    
    public static Estado getEstadoPorNombre(String unNombre) {
        Estado unEstado = null;
        int IDEstado = 0;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getEstado = "SELECT * FROM estado WHERE nombre = '" + unNombre + "'";
            ResultSet rs = statement.executeQuery(getEstado);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDEstado = rs.getInt("id_estado"); }
            unEstado = new Estado(IDEstado, unNombre);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unEstado; }
    
    public static Modalidad getModalidadPorNombre(String unNombre) {
        Modalidad unaModalidad = null;
        int IDModalidad = 0;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getModalidad = "SELECT * FROM modalidad WHERE nombre = '" + unNombre + "'";
            ResultSet rs = statement.executeQuery(getModalidad);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDModalidad = rs.getInt("id_modalidad"); }
            unaModalidad = new Modalidad(IDModalidad, unNombre);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unaModalidad; }  
    
    public static FormaPuntuacion getFormaPuntuacionPorNombre(String unNombre) {
        FormaPuntuacion unaFormaPuntuacion = null;
        int IDFormaPuntuacion = 0;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            
            String getFormaPuntuacion = "SELECT * FROM forma_puntuacion WHERE nombre = '" + unNombre + "'";
            ResultSet rs = statement.executeQuery(getFormaPuntuacion);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDFormaPuntuacion = rs.getInt("id_forma_puntuacion"); }
            unaFormaPuntuacion = new FormaPuntuacion(IDFormaPuntuacion, unNombre);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unaFormaPuntuacion; }    
    
    public static Deporte getDeportePorNombre(String unNombre) {
        Deporte unDeporte = null;
        int IDDeporte = 0;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getIDDeporte = "SELECT id_deporte FROM deporte WHERE nombre = '" + unNombre + "'";
            ResultSet rs = statement.executeQuery(getIDDeporte);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDDeporte = rs.getInt("id_deporte"); }
            unDeporte = new Deporte(IDDeporte, unNombre);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unDeporte; }
    
    public static String getNombrePorId(int id_comp){
        
        Connection conn = null;
        String SQL_NOMBRE_CD ="SELECT nombre FROM competencia WHERE id_competencia = '" + id_comp + "' ";
        String nomb=null;
        
        try{
            
            conn = DBConnection.get();
            ResultSet  rs;
            Statement stmt = conn.createStatement();
            rs=stmt.executeQuery(SQL_NOMBRE_CD);
            
            rs.next();
            
            nomb=rs.getString("nombre");
            
            
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return nomb;
    }
    
    public static Estado getEstadoPorId(int idEstado){
        
         
        String _SQL_FK_ESTADO ="SELECT nombre FROM estado  WHERE id_estado = '" + idEstado + "' ";
        
        Connection conn = null; 
        Estado unEstado = null;
        
        try{
        
        conn = DBConnection.get();
        
        ResultSet  rs;
        
        Statement stmt = conn.createStatement();
         
        rs=stmt.executeQuery(_SQL_FK_ESTADO);
        
        rs.next();
        String nomb=rs.getString("nombre");
        
        unEstado = new Estado(idEstado, nomb);
        
        
   
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //no olvidar nunca cerrar todo!!!
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return unEstado;
    }
        
    public static Deporte getDeportePorId(int idDeporte){
        
         
        String _SQL_FK_DEPORTE ="SELECT nombre FROM deporte WHERE id_deporte = '" + idDeporte + "' ";
        
        Connection conn = null; 
        Deporte unDeporte = null;
        
        try{
            conn = DBConnection.get();
            ResultSet  rs;
            Statement stmt = conn.createStatement();
            rs=stmt.executeQuery(_SQL_FK_DEPORTE);
            rs.next();
            
            int id_deporte= idDeporte;
            String nomb=rs.getString("nombre");
            
            unDeporte = new Deporte(id_deporte, nomb);
            
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //no olvidar nunca cerrar todo!!!
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return unDeporte;
    }    
        
    public static Modalidad getModalidadPorId(int idModalidad){
        
         
        String _SQL_FK_MODALIDAD ="SELECT nombre FROM modalidad WHERE id_modalidad = '" + idModalidad + "' ";
        
        Connection conn = null; 
        Modalidad unaModalidad = null;
        
        try{
        
        conn = DBConnection.get();
        ResultSet  rs;
        Statement stmt = conn.createStatement();
        rs=stmt.executeQuery(_SQL_FK_MODALIDAD);
        
        rs.next();
        
        String nomb=rs.getString("nombre");
        
        unaModalidad = new Modalidad(idModalidad, nomb);
 
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //no olvidar nunca cerrar todo!!!
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return unaModalidad;
    }    
    
    public static FormaPuntuacion getFormaPuntuacionPorId(int idFormaP){
        
         
        String _SQL_FK_FORMAP ="SELECT nombre FROM forma_puntuacion WHERE id_forma_puntuacion = '" + idFormaP + "' ";
        
        Connection conn = null; 
        FormaPuntuacion unaFormaP = null;
        
        try{
        
        conn = DBConnection.get();
        
        ResultSet  rs;
        
        Statement stmt = conn.createStatement();
         
        rs=stmt.executeQuery(_SQL_FK_FORMAP);
        
        rs.next();
        String nomb=rs.getString("nombre");
        
        unaFormaP = new FormaPuntuacion(idFormaP, nomb);
        
        
   
        }catch (SQLException ex) {
            
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //no olvidar nunca cerrar todo!!!
            if(conn!=null)try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        return unaFormaP;
    }
    
    
    public static ArrayList<String> getListaDeportes() {
        String _SQL_FIND_NOMBRES_DEPORTES = "SELECT nombre FROM deporte";   
        Connection conn = null;
        ArrayList<String> deportes = new ArrayList();
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(_SQL_FIND_NOMBRES_DEPORTES);
            while (rs.next()) {
                deportes.add(rs.getString("nombre")); }
            rs.close();
            return deportes; }
        catch (SQLException ex) {
            /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ }
        finally {
            if (conn!=null) try {
                conn.close(); }
            catch (SQLException ex) {
                /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ } } 
        return deportes; 
    }
    
    public static ArrayList<String> getListaLugares(String deporte) {
        String _SQL_FIND_NOMBRES_LUGARES = "SELECT l.nombre FROM lugar l "+
                                           "JOIN lugar_realiza_deporte ld ON ld.id_lugar = l.id_lugar "+
                                           "JOIN deporte d ON ld.id_deporte = d.id_deporte "+
                                           "WHERE d.nombre= '"+deporte+"'";
        Connection conn = null;
        ArrayList<String> lugares = new ArrayList();
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(_SQL_FIND_NOMBRES_LUGARES);
            while (rs.next()) {
                lugares.add(rs.getString("nombre")); }
            rs.close();
            return lugares; }
        catch (SQLException ex) {
            /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ }
        finally {
            if (conn!=null) try {
                conn.close(); }
            catch (SQLException ex) {
                /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ } } 
        return lugares; 
    }
        
    public static ArrayList<String> getListaModalidades() {
        String _SQL_FIND_NOMBRES_MODALIDADES = "SELECT nombre FROM modalidad";   
        Connection conn = null;
        ArrayList<String> modalidades = new ArrayList();
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(_SQL_FIND_NOMBRES_MODALIDADES);
            while (rs.next()) {
                modalidades.add(rs.getString("nombre")); }
            rs.close();
            return modalidades; }
        catch (SQLException ex) {
            /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ }
        finally {
            if (conn!=null) try {
                conn.close(); }
            catch (SQLException ex) {
                /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ } } 
        return modalidades; 
    }
    
    public static ArrayList<String> getListaPuntuaciones() {
        String _SQL_FIND_NOMBRES_PUNTUACIONES = "SELECT nombre FROM forma_puntuacion";   
        Connection conn = null;
        ArrayList<String> puntuaciones = new ArrayList();
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(_SQL_FIND_NOMBRES_PUNTUACIONES);
            while (rs.next()) {
                puntuaciones.add(rs.getString("nombre")); }
            rs.close();
            return puntuaciones; }
        catch (SQLException ex) {
            /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ }
        finally {
            if (conn!=null) try {
                conn.close(); }
            catch (SQLException ex) {
                /* Logger.getLogger(participanteDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); */ } } 
        return puntuaciones; 
    }
    
    public static Boolean nombreUsado(String nombreCD) {
        String _SQL_FIND_NOMBRE_USADO = "SELECT nombre FROM competencia WHERE nombre = '" + nombreCD + "'";
        Connection conn = null;
        Boolean retorno = true;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(_SQL_FIND_NOMBRE_USADO);
            if (!rs.isBeforeFirst()) { retorno = false; }
            rs.close(); }
        catch (SQLException e) {
            System.out.println(e.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return retorno;
    }
    
    
    public static ArrayList<Competencia> getCompetencias (String nombreCD, String nombreDeporte, String nombreModalidad, String nombreEstado) {
        
        Connection conn = null; 
        
        String _SQL_FIND_COMPETENCIAS = "SELECT * FROM competencia WHERE ";
        String SQL_LISTA_COMPETENCIAS=null; 
        ArrayList<Competencia> ls= new ArrayList <> ();
  
        try {
            
            conn = DBConnection.get();
     
            Statement statement = conn.createStatement();
 
            ResultSet rs;
            
            SQL_LISTA_COMPETENCIAS = _SQL_FIND_COMPETENCIAS;
            
            if (nombreCD != null) {
                String auxNombre = "nombre LIKE '%" + nombreCD + "%' and "; 
                SQL_LISTA_COMPETENCIAS= SQL_LISTA_COMPETENCIAS + auxNombre;
            }
            if (nombreDeporte != null) {
                String _SQL_FIND_ID_DEPORTE = "SELECT id_deporte FROM deporte WHERE nombre = '" + nombreDeporte + "'";
                rs = statement.executeQuery(_SQL_FIND_ID_DEPORTE);
                while(rs.next()){
                    int IDDeporte = rs.getInt("id_deporte");
                    String auxDeporte = "id_deporte = " + IDDeporte +" and " ; 
                    SQL_LISTA_COMPETENCIAS= SQL_LISTA_COMPETENCIAS + auxDeporte;
                }
            }
            if (nombreModalidad != null) {
                String _SQL_FIND_ID_MODALIDAD = "SELECT id_modalidad FROM modalidad WHERE nombre ='" + nombreModalidad + "'";
                rs = statement.executeQuery(_SQL_FIND_ID_MODALIDAD);
                while(rs.next()){
                    int IDModalidad = rs.getInt("id_modalidad");
                    String auxModalidad= "id_modalidad = " + IDModalidad +" and " ;
                    SQL_LISTA_COMPETENCIAS= SQL_LISTA_COMPETENCIAS + auxModalidad;
                }
            }
            if (nombreEstado != null) {
                String _SQL_FIND_ID_ESTADO = "SELECT id_estado FROM estado WHERE nombre LIKE '%" + nombreEstado + "%'";
                rs = statement.executeQuery(_SQL_FIND_ID_ESTADO);
                while(rs.next()){    
                    int IDEstado = rs.getInt("id_estado");
                    String auxEstado ="id_estado = " + IDEstado +" and " ;
                    SQL_LISTA_COMPETENCIAS= SQL_LISTA_COMPETENCIAS + auxEstado;
                }
            }
            
            SQL_LISTA_COMPETENCIAS= SQL_LISTA_COMPETENCIAS.substring(0, SQL_LISTA_COMPETENCIAS.length()-4);
            
            rs = statement.executeQuery(SQL_LISTA_COMPETENCIAS);
              
            while(rs.next()){
                
                // Obtener los objetos Estado, Modalidad, FormaPunt, Deporte, y asignarlos a la competencia
                
                Competencia comp= new Competencia(rs.getInt("id_competencia"), getNombrePorId(rs.getInt("id_competencia")),
                        getDeportePorId(rs.getInt("id_deporte")), getModalidadPorId(rs.getInt("id_modalidad")),
                        getEstadoPorId(rs.getInt("id_estado")), getFormaPuntuacionPorId(rs.getInt("id_forma_puntuacion")));
                
                ls.add(comp);     
            }
            
            
        }
        catch (SQLException ex) {
            Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); 
        }
        finally {
            if (conn!=null) try {
                conn.close(); }
            catch (SQLException ex) {
                Logger.getLogger(CompetenciaDaoJDBC.class.getName()).log(Level.SEVERE, null, ex); } 
        } 
        
            return ls;
    }
    
    
    public static void persistirDisponibilidad(Disponibilidad unaDisponibilidad, int IDCD) {
        int cantidad = unaDisponibilidad.getCantidad();
        LugarRealizacion lugar = unaDisponibilidad.getLR();
        String insertarDisponibilidad = "INSERT INTO disponibilidad VALUES (default, " + IDCD + ", " + lugar.getID() + ", " + cantidad + ")";
        Connection conn = null; 
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            statement.executeUpdate(insertarDisponibilidad); }
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    
    public static void persistirCD(Competencia unaCompetencia){
        int IDCD = 0;
        
        String reglamento="";
        
        if(unaCompetencia.getReglamento()!=null){
            reglamento="'"+unaCompetencia.getReglamento()+"'";
        }
        else{
            reglamento=null;
        }
        // Queries
        String persistirCD = "INSERT INTO competencia VALUES (default, 1, " + unaCompetencia.getEstado().getID() + ", "
            + unaCompetencia.getFormaPuntuacion().getId() + ", " + unaCompetencia.getModalidad().getID() + ", "
            + unaCompetencia.getDeporte().getId() + ", '" + unaCompetencia.getNombre() + "', " + reglamento + ", "
            + unaCompetencia.getCantidadMaximaDeSets() + ", " + unaCompetencia.getTantosPorAusenciaDeRival() + ", "
            + unaCompetencia.getPuntosPorPresentacion() + ", " + unaCompetencia.getPuntosPorVictoria() + ", "
            + unaCompetencia.getEmpatePermitido() + ", " + unaCompetencia.getPuntosPorEmpate() + ")";
        String getIDCompetencia = "SELECT id_competencia FROM competencia WHERE nombre = '" + unaCompetencia.getNombre() + "'";
        
        // Persistencia
        Connection conn = null; 
        try {
            // Persistencia CD
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            statement.executeUpdate(persistirCD);
            // Obtengo IDCD para persistir las disponibilidades
            ResultSet rs = statement.executeQuery(getIDCompetencia);
            // NOTA: El ResultSet solo contiene un resultado
            while (rs.next()) {
                IDCD = rs.getInt("id_competencia"); }
            // Persistencia disponibilidades
            for (Disponibilidad disponibilidad : (unaCompetencia.getListaDisponibilidades())) {
                persistirDisponibilidad(disponibilidad, IDCD);
            }
            rs.close(); }
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } } }
