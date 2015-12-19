package modelo;

public class Deporte {
    
    int id; 
    String nombre;
    
    public void setNombre(String unNombre) {
        this.nombre = unNombre; 
    }

    public void setId(int unID) {
        this.id = unID; 
    }
    
    public String getNombre() {
        return nombre; 
    }
    
    public int getId() {
        return id;
    }
    
    public Deporte(int unID, String unNombre) {
        this.id = unID;
        this.nombre = unNombre; 
    } 
}
