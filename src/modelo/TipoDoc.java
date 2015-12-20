package modelo;

public class TipoDoc {
   
    private int id;
    private String nombre;

    public void setID(int unID) {
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
    public TipoDoc(String unNombre) {
        this.nombre = unNombre;
    }
    
    // Con ID
    public TipoDoc(int unID, String unNombre) {
        this.id = unID;
        this.nombre = unNombre;
    }
}