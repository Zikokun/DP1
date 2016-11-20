/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

public class Vuelo {
    private int idVuelo;
    private int hSalida;
    private int mSalida;
    private int hLlegada;
    private int mLlegada;
    private String origen;
    private String destino;
    private String tipoVuelo;
    private int tiempo;
    private int capacidad=200;
    private Ciudad aeroOrig;
    private Ciudad aeroFin;
    private ArrayList<Integer> capTiempo = new ArrayList<>(); // capacidades en el tiempo
    private ArrayList<Integer> capTiempoAux = new ArrayList<>(); //copia de capacidades en el tiempo
            
    public Vuelo(){
        hSalida = 0;
        mSalida = 0;
        hLlegada = 0;
        mLlegada = 0;
        origen = "";
        destino = "";
        tipoVuelo = "";
        tiempo = 0;
    }
    
    public Vuelo(int hSal,int mSal, int hLleg, int mLleg,String orig, String dest){
        hSalida=hSal;
        mSalida=mSal;
        hLlegada=hLleg;
        mLlegada=mLleg;
        origen=orig;
        destino=dest;
        
        //inicializar capacidades en el tiempo
        for (int i=1; i<8; i++){ //los 7 dias de la semana
            capTiempo.add(capacidad);
            capTiempoAux.add(capacidad);
        }
    }
    
    public void copiarACapAux(){
        for(int i=0;i<getCapTiempo().size();i++){
            getCapTiempoAux().set(i, getCapTiempo().get(i));
        }
    }
    
    public void copiarDesdeCapAux(){
        for(int i=0;i<getCapTiempo().size();i++){
            getCapTiempo().set(i, getCapTiempoAux().get(i));
        }
    }
    
    public void setearCaps(){
        //inicializar capacidades en el tiempo
        for (int i=1; i<8; i++){ //los 7 dias de la semana
            capTiempo.add(capacidad);
            capTiempoAux.add(capacidad);
        }
    }
    
    public void print(){
        System.out.println(getOrigen()+"-"+getDestino()+"-"+getTiempo());
    }

    public int gethSalida() {
        return hSalida;
    }

    public void sethSalida(int hSalida) {
        this.hSalida = hSalida;
    }

    public int getmSalida() {
        return mSalida;
    }

    public void setmSalida(int mSalida) {
        this.mSalida = mSalida;
    }

    public int gethLlegada() {
        return hLlegada;
    }

    public void sethLlegada(int hLlegada) {
        this.hLlegada = hLlegada;
    }

    public int getmLlegada() {
        return mLlegada;
    }

    public void setmLlegada(int mLlegada) {
        this.mLlegada = mLlegada;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getTipoVuelo() {
        return tipoVuelo;
    }

    public void setTipoVuelo(String tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Ciudad getAeroOrig() {
        return aeroOrig;
    }

    public void setAeroOrig(Ciudad aeroOrig) {
        this.aeroOrig = aeroOrig;
    }

    public Ciudad getAeroFin() {
        return aeroFin;
    }

    public void setAeroFin(Ciudad aeroFin) {
        this.aeroFin = aeroFin;
    }

    public ArrayList<Integer> getCapTiempo() {
        return capTiempo;
    }

    public void setCapTiempo(ArrayList<Integer> capTiempo) {
        this.capTiempo = capTiempo;
    }

    public ArrayList<Integer> getCapTiempoAux() {
        return capTiempoAux;
    }

    public void setCapTiempoAux(ArrayList<Integer> capTiempoAux) {
        this.capTiempoAux = capTiempoAux;
    }

    /**
     * @return the idVuelo
     */
    public int getIdVuelo() {
        return idVuelo;
    }

    /**
     * @param idVuelo the idVuelo to set
     */
    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }
}