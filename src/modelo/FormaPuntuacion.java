package modelo;

public class FormaPuntuacion {
    private int ID;
    private String nombre;

    public void setId(int unID) {
        this.ID = unID; } 
    
    public void setNombre(String unNombre) {
        this.nombre = unNombre; }   
   
     public int getId() {
        return ID; }
     
    public String getNombre() {
        return nombre; }

    public FormaPuntuacion(int unID, String unNombre) {
        this.ID = unID;
        this.nombre = unNombre; } }