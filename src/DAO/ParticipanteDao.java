/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import static java.sql.JDBCType.VARCHAR;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.HistorialParticipante;
import modelo.Participante;
import modelo.Competencia;

/**
 *
 * @author Martin
 */
public class ParticipanteDao {
    
    // Verifica el nombre de participante
    public static Boolean correoUsado(int id, String correo) {
        String _SQL_FIND_CORREO_USADO = "SELECT correo_electronico FROM participante "+
                                        "WHERE correo_electronico = '" + correo + "' AND id_competencia="+id;
        Connection conn = null;
        Boolean retorno = true;
        try {
            conn = DBConnection.get();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(_SQL_FIND_CORREO_USADO);
            if (!rs.isBeforeFirst()) { retorno = false; }
            rs.close(); }
        catch (SQLException e) {
            System.out.println(e.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage()); } }
        return retorno;
    }
    
    // Verifica el nombre de participante
    public static Boolean nombreUsado(int id, String nombre) {
        String _SQL_FIND_NOMBRE_USADO = "SELECT nombre FROM participante "+
                                        "WHERE nombre = '" + nombre + "' AND id_competencia= " + id;
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
    
    public static void persistirParticipante(int idComp, Participante participante){
        
        // Query           (id_participante, id_competencia, nombre, imagen, correo)
        //String persistirP = "INSERT INTO participante VALUES (default, ?, ?, ?, ?)";
        
        // Persistencia
        Connection conn = null; 
        try {
            // Persistencia CD
            conn = DBConnection.get();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO participante VALUES (default, ?, ?, null, ?)");
            ps.setInt(1, idComp);
            ps.setString(2, participante.getNombre());
            /* Persistencia de la imagen, teniendo el path o FileInputStream (fis)
            ps.setBinaryStream(2, fis, (int)file.length());*/
            //ps.setNull(null, VARCHAR);
            ps.setString(3, participante.getCorreoElectronico());
            ps.executeUpdate();
            ps.close();
            }
        catch (SQLException ex) {
            System.out.println(ex.getMessage()); }
        finally {
            if (conn != null) try { conn.close(); }
            catch (SQLException ex) { System.out.println(ex.getMessage());
            }
        }
    }
    
    public static ArrayList<Participante> getParticipantes (int id) {
        
        Connection conn = null;
        
        String _SQL_FIND_PARTICIPANTES = "SELECT * FROM participante WHERE id_competencia =" + id;
        ArrayList<Participante> ls= new ArrayList <Participante> ();
        
        try{
            
            conn = DBConnection.get();
            
            Statement statement = conn.createStatement();
            
            ResultSet rs;
            
            rs = statement.executeQuery(_SQL_FIND_PARTICIPANTES);
            
            ArrayList<HistorialParticipante> unHistorial = new ArrayList<>();
            while(rs.next()){
                Participante comp= new Participante(rs.getString("nombre"), rs.getString("correo_electronico"), unHistorial);
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
    
    public static int cantidadParticipantes(int idCD){
        
        Connection conn = null;
        int retorno=0;
        String _SQL_CANT_PARTICIPANTES = "SELECT COUNT (*) FROM participante p "
                                        +"WHERE p.id_competencia=" + idCD;
        
        try{
            
            conn = DBConnection.get();
            
            Statement statement = conn.createStatement();
            
            ResultSet rs;
            
            rs = statement.executeQuery(_SQL_CANT_PARTICIPANTES);
            
            rs.next();
            retorno=rs.getInt(1);
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
        
        return retorno;
    }
    
}
