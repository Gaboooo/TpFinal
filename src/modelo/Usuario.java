package modelo;

import java.util.*;

public class Usuario {
    private int ID;
    private String correoElectronico;
    private String contraseña;
    private int numDocumento;
    private String nombre;
    private String apellido;
    private String tipoDocumento; 
    private Localidad localidad;
    ArrayList<Ingreso> listaIngresos;
    ArrayList<Competencia> listaCompetencias;

    public void setId(int unID) {
        this.ID = unID; }

    public void setCorreoElectronico(String unCorreo) {
        this.correoElectronico = unCorreo; }

    public void setContraseña(String unaContraseña) {
        this.contraseña = unaContraseña; }

    public void setNumDocumento(int unNumero) {
        this.numDocumento = unNumero; }

    public void setNombre(String unNombre) {
        this.nombre = unNombre; }

    public void setApellido(String unApellido) {
        this.apellido = unApellido; }

    public void setTipoDocumento(String unTipo) {
        this.tipoDocumento = unTipo; }

    public void setLocalidad(Localidad unaLocalidad) {
        this.localidad = unaLocalidad;  }

    public void setIngresos(ArrayList<Ingreso> unaLista) {
        this.listaIngresos = unaLista; }

    public void setCompetencias(ArrayList<Competencia> unaLista) {
        this.listaCompetencias = unaLista; }

    public int getID() {
        return ID; }

    public String getCorreoElectronico() {
        return correoElectronico; }

    public String getContraseña() {
        return contraseña; }

    public int getNumoDocumento() {
        return numDocumento; }

    public String getNombre() {
        return nombre; }

    public String getApellido() {
        return apellido; }

    public String getTipoDocumento() {
        return tipoDocumento; }

    public Localidad getLocalidad() {
        return localidad; }

    public ArrayList<Ingreso> getIngresos() {
        return listaIngresos; }

    public ArrayList<Competencia> getCompetencias() {
        return listaCompetencias; }

    // Sin ID
    public Usuario(String unCorreo, String unaContraseña, int unNumero, String unNombre, String unApellido,
        String unTipo, Localidad unaLocalidad, ArrayList<Ingreso> unaLI, ArrayList<Competencia> unaLC) {
        this.correoElectronico = unCorreo;
        this.contraseña = unaContraseña;
        this.numDocumento = unNumero;
        this.nombre = unNombre;
        this.apellido = unApellido;
        this.tipoDocumento = unTipo;
        this.localidad = unaLocalidad;
        this.listaIngresos = unaLI;
        this.listaCompetencias = unaLC; }
    
    // Con ID
    public Usuario(int unID, String unCorreo, String unaContraseña, int unNumero, String unNombre, String unApellido,
        String unTipo, Localidad unaLocalidad, ArrayList<Ingreso> unaLI, ArrayList<Competencia> unaLC) {
        this.ID = unID;
        this.correoElectronico = unCorreo;
        this.contraseña = unaContraseña;
        this.numDocumento = unNumero;
        this.nombre = unNombre;
        this.apellido = unApellido;
        this.tipoDocumento = unTipo;
        this.localidad = unaLocalidad;
        this.listaIngresos = unaLI;
        this.listaCompetencias = unaLC; } }