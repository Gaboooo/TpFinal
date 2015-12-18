package modelo;

 import java.util.*;

public class Participante {
    private int ID;
    private String nombre;
    private String correoElectronico;
    // private String imagen;
    private ArrayList<HistorialParticipante> historial;
    
    public void setID(int unID) {
        this.ID = unID; }
    
    public void setCorreoElectronico(String unCorreo) {
        this.correoElectronico = unCorreo; }

    public void setNombre(String unNombre) {
        this.nombre = unNombre; }

    /* public void setImagen(String imagen) {
        this.imagen = imagen; } */

    public void setHistorial(ArrayList<HistorialParticipante> HistP) {
        this.historial = HistP; }

    public int getID() {
        return ID; }
    
    public String getCorreoElectronico() {
        return correoElectronico; }

    public String getNombre() {
        return nombre; }

    /* public String getImagen() {
        return imagen; } */

    public ArrayList<HistorialParticipante> getHistorial() {
        return historial; } 

    // Con ID
    public Participante(int unID, String unNombre, String unCorreo, /* FKIN IMAGEN */ ArrayList<HistorialParticipante> histP) {
        this.ID = unID;
        this.nombre = unNombre;
        this.correoElectronico = unCorreo;
        this.historial = histP; }

    // Sin ID
    public Participante(String unNombre, String unCorreo, /* FKIN IMAGEN */ ArrayList<HistorialParticipante> histP) {
        this.nombre = unNombre;
        this.correoElectronico = unCorreo;
        this.historial = histP; } }