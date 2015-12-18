package modelo;

public class PosicionAux {
    private String nombreParticipante;
    private int puntos;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;
    private int tantosEnContra;
    private int tantosAFavor;
   
    public PosicionAux(String NombreParticipante, int puntos, int partidosGanados, int partidosPerdidos, int partidosEmpatados, int tantosEnContra, int tantosAFavor) {
        this.nombreParticipante = NombreParticipante;
        this.puntos = puntos;
        this.partidosGanados = partidosGanados;
        this.partidosPerdidos = partidosPerdidos;
        this.partidosEmpatados = partidosEmpatados;
        this.tantosEnContra = tantosEnContra;
        this.tantosAFavor = tantosAFavor; }
    
     public String getNombre() {
        return nombreParticipante; }
    
    public int getPuntos() {
        return puntos; }

    public int getPartidosGanados() {
        return partidosGanados; }

    public int getPartidosPerdidos() {
        return partidosPerdidos; }

    public int getPartidosEmpatados() {
        return partidosEmpatados; }

    public int getTantosEnContra() {
        return tantosEnContra; }

    public int getTantosAFavor() {
        return tantosAFavor; } }
