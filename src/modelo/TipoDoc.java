package modelo;

public class TipoDoc {
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
    public TipoDoc(String unNombre) {
        this.nombre = unNombre; }
    
    // Con ID
    public TipoDoc(int unID, String unNombre) {
        this.ID = unID;
        this.nombre = unNombre; } }