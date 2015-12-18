package modelo;

public class Modalidad {
    private int ID;
    private String nombre;

    public void setId(int unID) {
        this.ID = unID; }

    public void setNombre(String unNombre) {
        this.nombre = unNombre; }

    public int getID() {
        return ID; }

    public String getNombre() {
        return nombre; }
    
    // Sin ID
    public Modalidad(String unNombre) {
        this.nombre = unNombre; }
    
    // Con ID
    public Modalidad(int id, String nombre) {
        this.ID = id;
        this.nombre = nombre; } }