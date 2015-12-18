package modelo;

public class Pais {
    private int ID;
    private String nombre;

    public void setID(int unID) {
        this.ID = unID; }

    public void setNombre(String unNombre) {
        this.nombre = unNombre; }

    public int getID() {
        return ID; }

    public String getNombre() {
        return nombre; }
   
    // Sin ID
    public Pais(String unNombre) {
        this.nombre = unNombre; }    
    
    // Con ID
    public Pais(int unID, String unNombre) {
        this.ID = unID;
        this.nombre = unNombre; } }