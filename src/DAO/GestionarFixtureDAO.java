package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.*;

public class GestionarFixtureDAO {
    public static int getCantSets(CompetenciaAux unaCDAUX) {
        int cantSets = 0;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int unIDCDAUX = unaCDAUX.getId();
            String getCantSets = "SELECT * FROM competencia WHERE id_competencia = " + unIDCDAUX;
            ResultSet rs = statement.executeQuery(getCantSets);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                cantSets = rs.getInt("cantidad_maxima_de_sets"); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return cantSets; }
    
    public static Boolean getEmpatePermitido(CompetenciaAux unaCDAUX) {
        Boolean empatePermitido = false;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int unIDCDAUX = unaCDAUX.getId();
            String getCantSets = "SELECT * FROM competencia WHERE id_competencia = " + unIDCDAUX;
            ResultSet rs = statement.executeQuery(getCantSets);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                empatePermitido = rs.getBoolean("empate_permitido"); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return empatePermitido; }
    
    // DONE!
    public static void persistirPosicion(Competencia unaCompetencia, Posicion unaPosicion) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDCD = unaCompetencia.getID();
            int IDParticipante = unaPosicion.getParticipante().getID();
            int puntaje = unaPosicion.getPuntaje();
            int partidosGanados = unaPosicion.getPartidosGanados();
            int partidosPerdidos = unaPosicion.getPartidosPerdidos();
            int partidosEmpatados = unaPosicion.getPartidosEmpatados();
            int tantosAFavor = unaPosicion.getTantosAFavor();
            int tantosEnContra = unaPosicion.getTantosEnContra();
            String persistirPosicion = "INSERT INTO posicion VALUES (default, " + IDCD + ", " + IDParticipante + ", " + puntaje + ", " 
                + partidosGanados + ", " + partidosPerdidos + ", " + partidosEmpatados + ", " + tantosAFavor + ", " + tantosEnContra + ")";
            statement.executeUpdate(persistirPosicion); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    
    // REWORK! Ahora recibe un PartidoAux
    public static Partido getPartido(PartidoAux unPartidoAux) {
        Partido unPartido = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getPartido = "SELECT * FROM partido WHERE id_partido = " + unPartidoAux.getId();
            ResultSet rs = statement.executeQuery(getPartido);
            // NOTA: El ResultSet tiene un solo resultado
            while (rs.next()) {
                int IDPartido = rs.getInt("id_partido");
                int IDP0 = rs.getInt("id_participante1");
                Participante P0 = getParticipante(IDP0);
                int IDP1 = rs.getInt("id_participante2");
                Participante P1 = getParticipante(IDP1);
                int IDLR = rs.getInt("id_lugar");
                LugarRealizacion LR = GenerarFixtureDAO.getLR(IDLR);
                ArrayList<Resultado> listaResultados = getResultados(IDPartido);
                int IDGanador = rs.getInt("id_ganador_partido");
                if (rs.wasNull()) {
                    Boolean huboEmpate = true;
                    unPartido = new Partido(IDPartido, P0, P1, LR, listaResultados, null, huboEmpate); }
                else {
                    Participante unGanador = getParticipante(IDGanador);
                    Boolean huboEmpate = false;
                    unPartido = new Partido(IDPartido, P0, P1, LR, listaResultados, unGanador, huboEmpate); } }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unPartido; }
    
    // REWORK! Ahora recibe una RondaAux
    public static Ronda getRonda(RondaAux unaRondaAux) {
        Ronda unaRonda = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getRonda = "SELECT * FROM ronda WHERE id_ronda = " + unaRondaAux.getId();
            ResultSet rs = statement.executeQuery(getRonda);
            // NOTA: El ResultSet tiene un solo resultado
            while (rs.next()) {
                int unaID = rs.getInt("id_ronda");
                int unNumero = rs.getInt("numero_ronda");
                Boolean rondaPerdedores = rs.getBoolean("ronda_perdedores");
                String unaFecha = rs.getString("fecha");
                ArrayList<Partido> unaListaPartidos = GestionarFixtureDAO.getPartidos(unaID);
                unaRonda = new Ronda(unaID, unNumero, rondaPerdedores, unaFecha, unaListaPartidos); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unaRonda; }
    
    // DONE!
    public static void persistirHistorialResultado(Partido unPartido, HistorialResultado unHR) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String persistirHR;
            int IDPartido = unPartido.getID();
            String fechaModificacion = unHR.getFechaModificacion();
            String horaModificacion = unHR.getHoraModificacion();
            Participante ganador = unHR.getGanador();
            Boolean huboEmpate = unHR.getEmpate();
            if (huboEmpate) {
                ArrayList<Resultado> listaResultados = unHR.getListaResultados();
                for (Resultado unResultado:listaResultados) {
                    int IDResultado = unResultado.getID();
                    persistirHR = "INSERT INTO historial_resultado VALUES (default, " + IDPartido + ", " + IDResultado + ", null, " + huboEmpate + ", '" + fechaModificacion + "', '" + horaModificacion + "')";
                    statement.executeUpdate(persistirHR); } }
            else {
                Object IDGanador = ganador.getID();
                ArrayList<Resultado> listaResultados = unHR.getListaResultados(); 
                for (Resultado unResultado:listaResultados) {
                    int IDResultado = unResultado.getID();
                    persistirHR = "INSERT INTO historial_resultado VALUES (default, " + IDPartido + ", " + IDResultado + ", " + IDGanador + ", " + huboEmpate + ", '" + fechaModificacion + "', '" + horaModificacion + "')";
                    statement.executeUpdate(persistirHR); } } }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    
    // DONE!
    public static void updatePosicion(Competencia unaCompetencia, Participante unParticipante, Posicion unaPosicion) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDCD = unaCompetencia.getID();
            int IDParticipante = unParticipante.getID();
            int puntaje = unaPosicion.getPuntaje();
            int partidosGanados = unaPosicion.getPartidosGanados();
            int partidosPerdidos = unaPosicion.getPartidosPerdidos();
            int partidosEmpatados = unaPosicion.getPartidosEmpatados();
            int tantosAFavor = unaPosicion.getTantosAFavor();
            int tantosEnContra = unaPosicion.getTantosEnContra();
            String updatePosicion = "UPDATE posicion "
                + "SET puntaje = " + puntaje + ", partidos_ganados = " + partidosGanados + ", partidos_perdidos = " + partidosPerdidos 
                + ", partidos_empatados = " + partidosEmpatados + ", tantos_a_favor = " + tantosAFavor + ", tantos_en_contra = " + tantosEnContra
                + " WHERE id_competencia = " + IDCD + " AND id_participante = " + IDParticipante;
            statement.executeQuery(updatePosicion); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } } 
    
    // DONE!
    public static void setGanadorAndEmpate(Partido unPartido) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String updateGanador;
            int IDPartido = unPartido.getID();
            Boolean huboEmpate = unPartido.getEmpate();
            if (huboEmpate) { 
                updateGanador = "UPDATE partido SET id_ganador_partido = null, empate = " + huboEmpate + " WHERE id_partido = '" + IDPartido + "'"; }
            else {
                int IDGanador = unPartido.getGanador().getID();
                updateGanador = "UPDATE partido SET id_ganador_partido = " + IDGanador + ", empate = " + huboEmpate + " WHERE id_partido = '" + IDPartido + "'"; }
            ResultSet rs = statement.executeQuery(updateGanador);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    
    // DONE!
    public static void persistirResultado(Partido unPartido, Resultado unResultado) {
        int IDPartido = unPartido.getID();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int numResultado = unResultado.getNumero();
            int PP0 = unResultado.getPuntajeP0();
            int PP1 = unResultado.getPuntajeP1();
            Boolean AP0 = unResultado.getAsistenciaP0();
            Boolean AP1 = unResultado.getAsistenciaP1();
            Participante unGanador = unResultado.getGanador();
            String persistirResultado = "INSERT INTO resultado VALUES (default, " + IDPartido + ", " + numResultado + ", " + PP0 + ", " + PP1 + ", " + AP0 + ", " + AP1 + ", ";
            if (unGanador == null) {
                persistirResultado = persistirResultado + "null)"; }
            else {
                int IDGanador = unGanador.getID();
                persistirResultado = persistirResultado + IDGanador + ")"; }
            statement.executeUpdate(persistirResultado); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    
    // DONE!
    public static ArrayList<Resultado> getResultados(int unIDPartido) {
        ArrayList<Resultado> listaResultados = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getResultado = "SELECT * FROM resultado WHERE id_resultado IN " +
                "((SELECT id_resultado FROM resultado WHERE id_partido = " + unIDPartido + ") EXCEPT " + 
                "(SELECT id_resultado FROM historial_resultado))";
            ResultSet rs = statement.executeQuery(getResultado);
            while (rs.next()) {
                int IDResultado = rs.getInt("id_resultado");
                int numero = rs.getInt("numero");
                int RP0 = rs.getInt("resultadoP1");
                int RP1 = rs.getInt("resultadoP2");
                Boolean PP0 = rs.getBoolean("asistenciaP1");
                Boolean PP1 = rs.getBoolean("asistenciaP2");
                // NOTA: id_ganador es NULL si la CD NO es por sets
                int IDGanador = rs.getInt("id_ganador");
                Resultado unResultado = null;
                if (rs.wasNull()) {
                    unResultado = new Resultado(IDResultado, numero, RP0, RP1, PP0, PP1, null); }
                else {
                    Participante unGanador = getParticipante(IDGanador);
                    unResultado = new Resultado(IDResultado, numero, RP0, RP1, PP0, PP1, unGanador); }
                listaResultados.add(unResultado); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return listaResultados; }
    
    // DONE!
    public static Participante getParticipante(int unID) {
        Participante unParticipante = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int unIDParticipante = 0; String unNombre = ""; String unCorreoElectronico = "";
            String getParticipante = "SELECT * FROM participante WHERE id_participante = " + unID;
            ResultSet rs = statement.executeQuery(getParticipante);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                unIDParticipante = rs.getInt("id_participante");
                unNombre = rs.getString("nombre");
                unCorreoElectronico = rs.getString("correo_electronico"); }
            rs.close();
            unParticipante = new Participante(unIDParticipante, unNombre, unCorreoElectronico, null); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unParticipante; }
    
    // DONE!
    public static ArrayList<Partido> getPartidos(int unIDRonda) {
        ArrayList<Partido> listaPartidos = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getPartidos = "SELECT * FROM partido WHERE id_ronda = " + unIDRonda;
            ResultSet rs = statement.executeQuery(getPartidos);
            while (rs.next()) {
                Partido unPartido;
                int IDPartido = rs.getInt("id_partido");
                int IDP0 = rs.getInt("id_participante1");
                Participante P0 = getParticipante(IDP0);
                int IDP1 = rs.getInt("id_participante2");
                Participante P1 = getParticipante(IDP1);
                int IDLR = rs.getInt("id_lugar");
                LugarRealizacion LR = GenerarFixtureDAO.getLR(IDLR);
                ArrayList<Resultado> listaResultados = getResultados(IDPartido);
                int IDGanador = rs.getInt("id_ganador_partido");
                if (rs.wasNull()) {
                    Boolean huboEmpate = true;
                    unPartido = new Partido(IDPartido, P0, P1, LR, listaResultados, null, huboEmpate); }
                else {
                    Participante unGanador = getParticipante(IDGanador);
                    Boolean huboEmpate = false;
                    unPartido = new Partido(IDPartido, P0, P1, LR, listaResultados, unGanador, huboEmpate); }
                listaPartidos.add(unPartido); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return listaPartidos;
    }
    
    // DONE!
    public static ArrayList<Ronda> getRondas(int idFixture) {
        ArrayList<Ronda> listaRondas = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getPartidos = "SELECT * FROM ronda WHERE id_fixture = " + idFixture;
            ResultSet rs = statement.executeQuery(getPartidos);
            while (rs.next()) {
                
                int idRonda = rs.getInt("id_ronda");
                int numero = rs.getInt("numero_ronda");
                
                //Buscar los partidos
                ArrayList<Partido> listaPartidos=getPartidos(idRonda);
                
                //Crear la ronda
                Ronda rondaAux= new Ronda(idRonda, numero, false, "", listaPartidos);
                
                listaRondas.add(rondaAux);
            }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return listaRondas;
    }
    
    public static Fixture getFixture(int idCD) {
        Fixture unFixture = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int idFixture = 0;
            String getFixture = "SELECT * FROM fixture WHERE id_competencia = " + idCD;
            ResultSet rs = statement.executeQuery(getFixture);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                idFixture = rs.getInt("id_fixture");}
            rs.close();
            ArrayList<Ronda> rondas=getRondas(idFixture);
            unFixture = new Fixture(idFixture, rondas); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unFixture;
    }
}
