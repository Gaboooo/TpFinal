package modelo;

public class FormaPuntuacion {
   
    private int id;
    private String nombre;

    public void setId(int unID) {
        this.id = unID; 
    } 
    
    public void setNombre(String unNombre) {
        this.nombre = unNombre;
    }   
   
     public int getId() {
        return id;
     }
     
    public String getNombre() {
        return nombre;
    }

    public FormaPuntuacion(int unID, String unNombre) {
        this.id = unID;
        this.nombre = unNombre; 
    } 
}