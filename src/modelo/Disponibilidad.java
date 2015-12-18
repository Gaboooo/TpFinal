package modelo;

public class Disponibilidad {
   private int ID;
   private int cantidad;
   private LugarRealizacion LR;

    public void setID(int unID) {
        this.ID = unID; }
   
    public void setCantidad(int unaCantidad) {
        this.cantidad = unaCantidad; }

    public void setLR(LugarRealizacion LR) {
        this.LR = LR; }

    public int getID() {
        return ID; }
    
    public int getCantidad() {
        return cantidad; }

    public LugarRealizacion getLR() {
        return LR; }
    
    // Con ID
    public Disponibilidad(int unID, int unaCantidad, LugarRealizacion LR) {
        this.ID = unID;
        this.cantidad = unaCantidad;
        this.LR = LR; }
    
    // Sin ID
    public Disponibilidad(int unaCantidad, LugarRealizacion LR) {
        this.cantidad = unaCantidad;
        this.LR = LR; } }
