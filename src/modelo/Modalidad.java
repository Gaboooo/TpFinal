package modelo;

public class Modalidad {
    private int id;
    private String nombre;

    public void setId(int unID) {
        this.id = unID; 
    }

    public void setNombre(String unNombre) {
        this.nombre = unNombre; 
    }

    public int getID() {
        return id; 
    }

    public String getNombre() {
        return nombre; 
    }
    
    // Sin ID
    public Modalidad(String unNombre) {
        this.nombre = unNombre;
    }
    
    // Con ID
    public Modalidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre; 
    } 
}