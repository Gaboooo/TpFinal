package modelo;

public class Estado {
    private int ID;
    private String nombre;

    public int getID() {
        return ID; }
    
    public String getNombre() {
        return nombre; }
    
    public void setNombre(String nombre) {
        this.nombre = nombre; }
    
    public void setID(int unID) {
        this.ID = unID; }
    
    public Estado(int unID, String nombre) {
        this.ID = unID;
        this.nombre = nombre; } }

