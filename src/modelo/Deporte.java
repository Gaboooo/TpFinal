package modelo;

public class Deporte {
    int ID; 
    String nombre;
    
    public void setNombre(String unNombre) {
        this.nombre = unNombre; }

    public void setId(int unID) {
        this.ID = unID; }
    
    public String getNombre() {
        return nombre; }
    
    public int getId() {
        return ID; }
    
    public Deporte(int unID, String unNombre) {
        this.ID = unID;
        this.nombre = unNombre; } }
