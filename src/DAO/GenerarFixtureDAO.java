package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.*;

public class GenerarFixtureDAO {
    
    public static ArrayList<Integer> getIDParticipantesJugandoRonda(int unIDRonda) {
        ArrayList<Integer> listaIDParticipantes = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getRonda = "SELECT id_participante1, id_participante2 FROM partido WHERE id_ronda = " + unIDRonda;
            ResultSet rs = statement.executeQuery(getRonda);
            // NOTA: El ResultSet tiene un solo resultado
            while (rs.next()) {
                int IDP0 = rs.getInt("id_participante0");
                int IDP1 = rs.getInt("id_participante1");
                listaIDParticipantes.add(IDP0);
                listaIDParticipantes.add(IDP1);
            }
            rs.close(); }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if (conn != null)
                try {
                    conn.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        }
        return listaIDParticipantes;
    }
    

    public static void deletePartidos(int unIDRonda) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String deletePartidos = "DELETE FROM partido WHERE id_ronda = " + unIDRonda;
            statement.executeUpdate(deletePartidos);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    

    public static void deleteRondas(int unIDFixture) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getIDRonda = "SELECT id_ronda FROM ronda WHERE id_fixture = " + unIDFixture;
            ArrayList<Integer> listaIDRondas = new ArrayList<>();
            ResultSet rs = statement.executeQuery(getIDRonda);
            while (rs.next()) {
                listaIDRondas.add(rs.getInt("id_ronda")); }
            for (Integer IDRonda:listaIDRondas) {
                deletePartidos(IDRonda); }
            String deleteRondas = "DELETE FROM ronda WHERE id_fixture = " + unIDFixture;
            statement.executeUpdate(deleteRondas);
            rs.close();}
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    

    public static void deleteFixture(int unaIDCD) {
        Connection conn = null; 
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getIDFixture = "SELECT id_fixture FROM fixture WHERE id_competencia = " + unaIDCD;
            ResultSet rs = statement.executeQuery(getIDFixture);
            // NOTA: El ResultSet solo contiene un resultado
            while (rs.next()) {
                int IDFixture = rs.getInt("id_fixture");
                deleteRondas(IDFixture);
                String deleteFixture = "DELETE FROM fixture WHERE id_fixture = " + IDFixture;
                statement.executeUpdate(deleteFixture); }
            rs.close(); }
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    

    public static void persistirPartido(Partido unPartido, int IDRonda) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            // Busqueda de IDs necesarias
            Participante P0 = unPartido.getP0();
            Participante P1 = unPartido.getP1();
            LugarRealizacion LR = unPartido.getLR();
            int IDP0 = P0.getID();
            int IDP1 = P1.getID();
            int IDLR = LR.getID();
            // Persistencia partido
            String persistirPartido = "INSERT INTO partido VALUES (default, " + IDRonda + ", " + IDP0 + ", " + IDP1 + ", " + IDLR + ", null, null, null, null)";
            statement.executeUpdate(persistirPartido); }
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    

    public static void persistirRonda(Ronda unaRonda, int IDFixture) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            // Persistencia ronda
            int IDRonda = 0;
            String persistirRonda = "INSERT INTO ronda VALUES (default, " + IDFixture + ", " + unaRonda.getFecha() + ", " + unaRonda.getNumero() + ")";
            statement.executeUpdate(persistirRonda, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            while(rs.next()) {
                IDRonda = rs.getInt(1); // Notese el 1
            }
            // Persistencia partidos
            for (Partido unPartido:(unaRonda.getListaPartidos())) {
                persistirPartido(unPartido, IDRonda); } }
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    

    public static void persistirFixture(Competencia unaCompetencia, Fixture unFixture) {
        Connection conn = null; 
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDCD = unaCompetencia.getID(); int IDFixture = 0;
            String persistirFixture = "INSERT INTO fixture VALUES (default, " + IDCD + ")";
            statement.executeUpdate(persistirFixture, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            while(rs.next()) {
                IDFixture = rs.getInt(1); // Notese el 1
            }
            // Persistencia rondas
            for (Ronda unaRonda:(unFixture.getListaRondas())) {
                persistirRonda(unaRonda, IDFixture); } }   
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } } }
    

    public static ArrayList<HistorialParticipante> getHistorialParticipante(int unIDParticipante) {
        ArrayList<HistorialParticipante> historial = new ArrayList();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            HistorialParticipante unCambio;
            int IDHP; String nombre, correo, fecha, hora;
            String getHistorial = "SELECT * FROM historial_participante WHERE id_participante = " + unIDParticipante;
            ResultSet rs = statement.executeQuery(getHistorial);
            while (rs.next()) {
                IDHP = rs.getInt("IDHP");
                nombre = rs.getString("nombre");
                correo = rs.getString("correo");
                fecha = rs.getString("fecha");
                hora = rs.getString("hora");
                unCambio = new HistorialParticipante(IDHP, nombre, correo, fecha, hora);
                historial.add(unCambio); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return historial; }    
    

    public static ArrayList<Participante> getParticipantes(int unIDCompetencia) {
        ArrayList<Participante> listaParticipantes = new ArrayList();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getParticipantes = "SELECT * FROM participante WHERE id_competencia = " + unIDCompetencia;
            ResultSet rs = statement.executeQuery(getParticipantes);
            while (rs.next()) {
                int IDParticipante = rs.getInt("id_participante");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo_electronico");
                ArrayList<HistorialParticipante> historial = getHistorialParticipante(IDParticipante);
                Participante unParticipante = new Participante(IDParticipante, nombre, correo, historial);
                listaParticipantes.add(unParticipante); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return listaParticipantes; }
    

    public static ArrayList<Deporte> getDeportes(int unIDLR) {
        ArrayList<Deporte> listaDeportes = new ArrayList();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ArrayList<Integer> listaIDDeportes = new ArrayList(); int IDDeporte; Deporte unDeporte;
            String getDeportes = "SELECT id_deporte FROM lugar_realiza_deporte WHERE id_lugar = " + unIDLR;
            ResultSet rs = statement.executeQuery(getDeportes);
            while (rs.next()) {
                IDDeporte = rs.getInt("id_deporte");
                listaIDDeportes.add(IDDeporte); }
            // Busqueda deportes por ID
            for (Integer unIDDeporte:listaIDDeportes) {
                unDeporte = getDeporte(unIDDeporte);
                listaDeportes.add(unDeporte); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return listaDeportes; }
    

    public static LugarRealizacion getLR(int unID) {
        LugarRealizacion unLR = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDLR = 0; String nombreLR = ""; String descripcionLR = "";
            String getLR = "SELECT * FROM lugar WHERE id_lugar = " + unID;
            ResultSet rs = statement.executeQuery(getLR);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDLR = rs.getInt("id_lugar");
                nombreLR = rs.getString("nombre");
                descripcionLR = rs.getString("descripcion"); }
            // Busqueda de deportes
            ArrayList<Deporte> listaDeportes = getDeportes(IDLR);
            unLR = new LugarRealizacion(IDLR, nombreLR, descripcionLR, listaDeportes);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unLR; }
    

    public static LugarRealizacion getLR(String unNombre) {
        LugarRealizacion unLR = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDLR = 0; String nombreLR = ""; String descripcionLR = "";
            String getLR = "SELECT * FROM lugar WHERE nombre = '" + unNombre +"'";
            ResultSet rs = statement.executeQuery(getLR);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDLR = rs.getInt("id_lugar");
                nombreLR = rs.getString("nombre");
                descripcionLR = rs.getString("descripcion"); }
            // Busqueda de deportes
            ArrayList<Deporte> listaDeportes = getDeportes(IDLR);
            unLR = new LugarRealizacion(IDLR, nombreLR, descripcionLR, listaDeportes);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unLR; } 
    

    public static ArrayList<Disponibilidad> getDisponibilidades(int unIDCompetencia) {
        ArrayList<Disponibilidad> listaDisponibilidades = new ArrayList();
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            String getDisponibilidades = "SELECT * FROM disponibilidad WHERE id_competencia = " + unIDCompetencia;
            ResultSet rs = statement.executeQuery(getDisponibilidades);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                int IDDisponibilidad = rs.getInt("id_disponibilidad");
                int IDLugar = rs.getInt("id_lugar");
                LugarRealizacion unLugarRealizacion = getLR(IDLugar);
                int cantPartidos = rs.getInt("cantidad");
                Disponibilidad unaDisponibilidad = new Disponibilidad(IDDisponibilidad, cantPartidos, unLugarRealizacion);
                listaDisponibilidades.add(unaDisponibilidad); }
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return listaDisponibilidades; }
    

    public static Deporte getDeporte(int unID) {
        Deporte unDeporte = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDDeporte = 0; String nombreDeporte = "";
            String getDeporte = "SELECT * FROM deporte WHERE id_deporte = " + unID;
            ResultSet rs = statement.executeQuery(getDeporte);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDDeporte = rs.getInt("id_deporte");
                nombreDeporte = rs.getString("nombre"); }
            unDeporte = new Deporte(IDDeporte, nombreDeporte);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unDeporte; }
    

    public static Modalidad getModalidad(int unID) {
        Modalidad unaModalidad = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDModalidad = 0; String nombreModalidad = "";
            String getModalidad = "SELECT * FROM modalidad WHERE id_modalidad = " + unID;
            ResultSet rs = statement.executeQuery(getModalidad);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDModalidad = rs.getInt("id_modalidad");
                nombreModalidad = rs.getString("nombre"); }
            unaModalidad = new Modalidad(IDModalidad, nombreModalidad);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unaModalidad; }
    

    public static Estado getEstado(int unID) {
        Estado unEstado = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDEstado = 0; String nombreEstado = "";
            String getIDEstado = "SELECT * FROM estado WHERE id_estado = " + unID;
            ResultSet rs = statement.executeQuery(getIDEstado);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDEstado = rs.getInt("id_estado");
                nombreEstado = rs.getString("nombre"); }
            unEstado = new Estado(IDEstado, nombreEstado);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return unEstado; }
    

    public static Estado getEstado(String unNombre) {
        Estado unEstado = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDEstado = 0; String nombreEstado = "";
            String getIDEstado = "SELECT * FROM estado WHERE nombre = '" + unNombre + "'";
            ResultSet rs = statement.executeQuery(getIDEstado);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDEstado = rs.getInt("id_estado");
                nombreEstado = rs.getString("nombre");
            }
            unEstado = new Estado(IDEstado, nombreEstado);
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if (conn != null)
                try {
                    conn.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        }
        return unEstado;
    }
    

    public static FormaPuntuacion getFormaPuntuacion(int unID) {
        FormaPuntuacion unaFormaPuntuacion = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDFormaPuntuacion = 0; String nombreFormaPuntuacion = "";
            String getIDFormaPuntuacion = "SELECT * FROM forma_puntuacion WHERE id_forma_puntuacion = " + unID;
            ResultSet rs = statement.executeQuery(getIDFormaPuntuacion);
            // El ResultSet tiene un solo resultado
            while (rs.next()) {
                IDFormaPuntuacion = rs.getInt("id_forma_puntuacion");
                nombreFormaPuntuacion = rs.getString("nombre");
            }
            unaFormaPuntuacion = new FormaPuntuacion(IDFormaPuntuacion, nombreFormaPuntuacion);
            rs.close();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if (conn != null)
                try {
                    conn.close();
                }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return unaFormaPuntuacion;
    }        
    

    public static Competencia getCompetencia(CompetenciaAux unaCDAUX) {
        Competencia unaCompetencia = null;
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            // Variables auxiliares
            String unNombre = ""; String unReglamento = "";
            int IDCompetencia = 0; int IDUsuario = 0; int IDEstado = 0; int IDFormaPuntuacion = 0; int IDModalidad = 0; int IDDeporte = 0;
            Deporte unDeporte; Modalidad unaModalidad; Estado unEstado; FormaPuntuacion unaFormaPuntuacion;
            ArrayList<Disponibilidad> listaDisponibilidades; ArrayList<Participante> listaParticipantes; ArrayList<Posicion> tablaPosiciones = null;
            int cantMaxSets = 0; int tantosAusenciaRival = 0; int ptosPresentacion = 0; int ptosVictoria = 0; int ptosEmpate = 0;
            Boolean permisoEmpate = false;
            // Busqueda de la competencia
            String getCD = "SELECT * FROM competencia WHERE nombre = '" + unaCDAUX.getNombre() + "'";
            ResultSet rs = statement.executeQuery(getCD);
            while(rs.next()) {
                IDCompetencia = rs.getInt("id_competencia"); IDUsuario = rs.getInt("id_usuario");
                IDEstado = rs.getInt("id_estado"); IDFormaPuntuacion = rs.getInt("id_forma_puntuacion");
                IDModalidad = rs.getInt("id_modalidad"); IDDeporte = rs.getInt("id_deporte");
                unNombre = rs.getString("nombre"); unReglamento = rs.getString("reglamento");
                cantMaxSets = rs.getInt("cantidad_maxima_de_sets"); tantosAusenciaRival = rs.getInt("tantos_por_ausencia_rival"); 
                ptosPresentacion = rs.getInt("puntos_por_presentacion"); ptosVictoria = rs.getInt("puntos_por_victoria");
                permisoEmpate = rs.getBoolean("empate_permitido"); ptosEmpate = rs.getInt("puntos_por_empate");
            }
            unDeporte = getDeporte(IDDeporte);
            unaModalidad = getModalidad(IDModalidad);
            unEstado = getEstado(IDEstado);
            unaFormaPuntuacion = getFormaPuntuacion(IDFormaPuntuacion);
            // Busqueda de: listaDisponibilidades, listaParticipantes
            listaDisponibilidades = getDisponibilidades(IDCompetencia);
            listaParticipantes = getParticipantes(IDCompetencia);
            // Creacion del retorno
            unaCompetencia = new Competencia(IDCompetencia, unNombre, unReglamento, unDeporte, unaModalidad, unEstado, unaFormaPuntuacion,
                listaDisponibilidades, listaParticipantes, tablaPosiciones,
                cantMaxSets, tantosAusenciaRival, ptosPresentacion, ptosVictoria, permisoEmpate, ptosEmpate);
            rs.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null) try { conn.close();
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return unaCompetencia;
    }


    public static void setEstado(Competencia unaCompetencia, Estado unEstado) {
        Connection conn = null;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            int IDCD = unaCompetencia.getID();
            int IDEstado = unEstado.getID();
            String setEstado = "UPDATE competencia SET id_estado = " + IDEstado + " WHERE id_competencia = '" + IDCD + "'";
            ResultSet rs = statement.executeQuery(setEstado);
            rs.close(); }
        catch (SQLException ex) { System.out.println(ex.getMessage()); }
        finally {
            if (conn != null)
                try {
                    conn.close();
                }
                catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    }

}
