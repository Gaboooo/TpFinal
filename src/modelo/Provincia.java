package modelo;

public class Provincia {
    
    private int id;
    private String nombre;
    private Pais pais;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Pais getPais() {
        return pais;
    }

    public void setID(int unID) {
        this.id = unID;
    }

    public void setNombre(String unNombre) {
        this.nombre = unNombre;
    }

    public void setPais(Pais unPais) {
        this.pais = unPais;
    }
    
    // Sin ID
    public Provincia(String unNombre, Pais unPais) {
        this.nombre = unNombre;
        this.pais = unPais;
    }
    
    // Con ID
    public Provincia(int unaID, String unNombre, Pais unPais) {
        this.id = unaID;
        this.nombre = unNombre;
        this.pais = unPais;
    }
}