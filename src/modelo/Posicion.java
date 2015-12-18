package modelo;

public class Posicion {
    private int ID;
    private Participante participante;
    private int puntaje;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;
    private int tantosEnContra;
    private int tantosAFavor;

    public void setParticipante(Participante unParticipante) {
        this.participante = unParticipante; }

    public void setPuntaje(int unPuntaje) {
        this.puntaje = unPuntaje; }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados; }

    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos; }

    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados; }

    public void setTantosEnContra(int unaCantidad) {
        this.tantosEnContra = unaCantidad; }

    public void setTantosAFavor(int unaCantidad) {
        this.tantosAFavor = unaCantidad; }

    public int getID() {
        return ID; }
    
    public Participante getParticipante() {
        return participante; }
    
    public int getPuntaje() {
        return puntaje; }

    public int getPartidosGanados() {
        return partidosGanados; }

    public int getPartidosPerdidos() {
        return partidosPerdidos; }

    public int getPartidosEmpatados() {
        return partidosEmpatados; }

    public int getTantosEnContra() {
        return tantosEnContra; }

    public int getTantosAFavor() {
        return tantosAFavor; }
    
    // Con ID
    public Posicion(int unID, Participante unParticipante, int unPuntaje,
        int partidosGanados, int partidosPerdidos, int partidosEmpatados, int tantosAFavor, int tantosEnContra) {
        this.ID = unID;
        this.participante = unParticipante;
        this.puntaje = unPuntaje;
        this.partidosGanados = partidosGanados;
        this.partidosPerdidos = partidosPerdidos;
        this.partidosEmpatados = partidosEmpatados;
        this.tantosAFavor = tantosAFavor;
        this.tantosEnContra = tantosEnContra; }
    
    // Sin ID
    public Posicion(Participante unParticipante, int unPuntaje,
        int partidosGanados, int partidosPerdidos, int partidosEmpatados, int tantosAFavor, int tantosEnContra) {
        this.participante = unParticipante;
        this.puntaje = unPuntaje;
        this.partidosGanados = partidosGanados;
        this.partidosPerdidos = partidosPerdidos;
        this.partidosEmpatados = partidosEmpatados;
        this.tantosAFavor = tantosAFavor;
        this.tantosEnContra = tantosEnContra; } }