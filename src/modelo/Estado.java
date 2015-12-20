package modelo;

public class Estado {
   
    private int id;
    private String nombre;

    public int getID() {
        return id; 
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setID(int unID) {
        this.id = unID; 
    }
    
    public Estado(int unID, String nombre) {
        this.id = unID;
        this.nombre = nombre; 
    } 
}

