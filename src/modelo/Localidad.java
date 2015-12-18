package modelo;

import java.util.*;

public class Localidad {
    private int ID;
    private String nombre;
    private String codigoPostal;
    ArrayList<Provincia> listaProvincias;

    public void setId(int unID) {
        this.ID = unID; }

    public void setNombre(String unNombre) {
        this.nombre = unNombre; }

    public void setCoidgoPostal(String unCP) {
        this.codigoPostal = unCP; }

    public void setProvincia(ArrayList<Provincia> unaLista) {
        this.listaProvincias = unaLista; }

    public int getID() {
        return ID; }

    public String getNombre() {
        return nombre; }

    public String getCP() {
        return codigoPostal; }

    public ArrayList<Provincia> getProvincias() {
        return listaProvincias; }

    // Sin ID
    public Localidad(String unNombre, String unCP, ArrayList<Provincia> unaLista) {
        this.nombre = unNombre;
        this.codigoPostal = unCP;
        this.listaProvincias = unaLista; }    
    
    // Con ID
    public Localidad(int unID, String unNombre, String unCP, ArrayList<Provincia> unaLista) {
        this.ID = unID;
        this.nombre = unNombre;
        this.codigoPostal = unCP;
        this.listaProvincias = unaLista; } }