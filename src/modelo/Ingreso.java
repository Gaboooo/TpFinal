package modelo;

public class Ingreso {
    
    private int id;
    private String hora;
    private String fecha;
    private String ip;

    public void setID(int unID) {
        this.id = unID; 
    }

    public void setHora(String unaHora) {
        this.hora = unaHora; 
    }

    public void setFecha(String unaFecha) {
        this.fecha = unaFecha; 
    }

    public void setIpConexion(String unaIP) {
        this.ip = unaIP; 
    }

    public int getID() {
        return id; 
    }

    public String getHora() {
        return hora; 
    }

    public String getFecha() {
        return fecha; 
    }

    public String getIP() {
        return ip; 
    }
    
    // Sin ID
    public Ingreso(String unaHora, String unaFecha, String unaIP) {
        this.hora = unaHora;
        this.fecha = unaFecha;
        this.ip = unaIP; 
    }
    
    // Con ID
    public Ingreso(int unID, String unaHora, String unaFecha, String unaIP) {
        this.id = unID;
        this.hora = unaHora;
        this.fecha = unaFecha;
        this.ip = unaIP; 
    } 
}