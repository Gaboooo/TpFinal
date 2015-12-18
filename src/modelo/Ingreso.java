package modelo;

public class Ingreso {
    private int ID;
    private String hora;
    private String fecha;
    private String IP;

    public void setID(int unID) {
        this.ID = unID; }

    public void setHora(String unaHora) {
        this.hora = unaHora; }

    public void setFecha(String unaFecha) {
        this.fecha = unaFecha; }

    public void setIpConexion(String unaIP) {
        this.IP = unaIP; }

    public int getID() {
        return ID; }

    public String getHora() {
        return hora; }

    public String getFecha() {
        return fecha; }

    public String getIP() {
        return IP; }
    
    // Sin ID
    public Ingreso(String unaHora, String unaFecha, String unaIP) {
        this.hora = unaHora;
        this.fecha = unaFecha;
        this.IP = unaIP; }
    
    // Con ID
    public Ingreso(int unID, String unaHora, String unaFecha, String unaIP) {
        this.ID = unID;
        this.hora = unaHora;
        this.fecha = unaFecha;
        this.IP = unaIP; } }