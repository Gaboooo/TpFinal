package modelo;

public class HistorialParticipante {
    private int ID;
    private String nombre;
    private String correo_electronico;
    // private String imagen;
    private String fecha;
    private String hora;
    
    public void setID(int unID) {
        this.ID = unID; }
    
    public void setCorreo(String correo) {
        this.correo_electronico = correo; }

    public void setNombre(String nombre) {
        this.nombre = nombre; }

    /* public void setImagen(String imagen) {
        this.imagen = imagen; } */

    public void setFecha(String fecha) {
        this.fecha = fecha; }

    public void setHora(String hora) {
        this.hora = hora; }

    public int getID() {
        return ID; }
    
    public String getCorreo() {
        return correo_electronico; }

    public String getNombre() {
        return nombre; }

    /* public String getImagen() {
        return imagen; } */

    public String getFecha() {
        return fecha; }

    public String getHora() {
        return hora; }

    /* public HistorialParticipante(int id, String correoElectronico, String nombre, String imagen, Date fecha, Time hora) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.nombre = nombre;
        this.imagen = imagen;
        this.fecha = fecha;
        this.hora = hora; } */
    
    // Con ID
    public HistorialParticipante(int unID, String nombre, String correo, String fecha, String hora) {
        this.ID = unID;
        this.nombre = nombre;
        this.correo_electronico = correo;
        this.fecha = fecha;
        this.hora = hora; }
    
    // Sin ID
    public HistorialParticipante(String nombre, String correo, String fecha, String hora) {
        this.nombre = nombre;
        this.correo_electronico = correo;
        this.fecha = fecha;
        this.hora = hora; } }