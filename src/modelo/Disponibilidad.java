package modelo;

public class Disponibilidad {
   
   private int id;
   private int cantidad;
   private LugarRealizacion lr;

    public void setID(int unID) {
        this.id = unID; 
    }
   
    public void setCantidad(int unaCantidad) {
        this.cantidad = unaCantidad; 
    }

    public void setLR(LugarRealizacion lr) {
        this.lr = lr; 
    }

    public int getID() {
        return id; 
    }
    
    public int getCantidad() {
        return cantidad; 
    }

    public LugarRealizacion getLR() {
        return lr; 
    }
    
    // Con id
    public Disponibilidad(int unID, int unaCantidad, LugarRealizacion lr) {
        this.id = unID;
        this.cantidad = unaCantidad;
        this.lr = lr; 
    }
    
    // Sin id
    public Disponibilidad(int unaCantidad, LugarRealizacion lr) {
        this.cantidad = unaCantidad;
        this.lr = lr; 
    } 
}
