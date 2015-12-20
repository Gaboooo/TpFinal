package modelo;

public class ParticipanteAux {
    
    private int id;
    private String nombre;
    private String correo;
    
    public ParticipanteAux(int unID, String nombre, String correo) {
        this.id = unID;
        this.correo = correo;
        this.nombre = nombre;
    }
    
    public ParticipanteAux(String nombre, String correo) {
        this.correo = correo;
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo; 
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }

    public String getCorreo() {
        return correo; 
    }
    
    public String getNombre() {
        return nombre; 
    } 
}