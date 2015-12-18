package modelo;

public class CompetenciaAux {
    
    private int id_competencia;
    private String estado;
    private String deporte;
    private String modalidad;
    private String nombre;
    private String formaPuntuacion;
    
    /**
     *
     * @param estado
     * @param deporte
     * @param modalidad
     * @param nombre
     */
    public CompetenciaAux(String estado, String deporte, String modalidad, String nombre) {
        this.estado=estado;
        this.deporte=deporte;
        this.modalidad=modalidad;
        this.nombre=nombre;
    }
    
    /**
     *
     * @param estado
     * @param deporte
     * @param modalidad
     * @param nombre
     * @param formaP
     * @param id
     */
    public CompetenciaAux(String estado, String deporte, String modalidad, String nombre, String formaP, int id) {
        this(estado,deporte,modalidad,nombre);
        this.id_competencia=id;
        this.formaPuntuacion=formaP;
    }
    
    /**
     *
     * @param estado
     * @param deporte
     * @param modalidad
     * @param nombre
     * @param id
     */
    public CompetenciaAux(String estado, String deporte, String modalidad, String nombre, int id){
        this(estado,deporte,modalidad,nombre);
        this.id_competencia=id;
    }
    
    /**
     *
     * @param estado
     * @param deporte
     * @param modalidad
     * @param nombre
     * @param formaP
     */
    public CompetenciaAux(String estado, String deporte, String modalidad, String nombre, String formaP) {
        this(estado,deporte,modalidad,nombre);
        this.formaPuntuacion=formaP;
    }
    
    public int getId_competencia() {
        return id_competencia;
    }

    public String getFormaPuntuacion() {
        return formaPuntuacion;
    }

    public void setId_competencia(int id_competencia) {
        this.id_competencia = id_competencia;
    }

    public void setFormaPuntuacion(String formaPuntuacion) {
        this.formaPuntuacion = formaPuntuacion;
    }
    
    public int getId(){
        return id_competencia;
    }
    public String getNombre() {
        return nombre;
    }
    public String getEstado() {
        return estado;
    }
    public String getModalidad() {
        return modalidad;
    }
    public String getDeporte() {
        return deporte;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
