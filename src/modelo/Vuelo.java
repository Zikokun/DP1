/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author a20111055-Vivian
 */
public class Vuelo {
    private int hSalida;
    private int mSalida;
    private int hLlegada;
    private int mLlegada;
    private String origen;
    private String destino;
    private String tipoVuelo;
    private int tiempo;
    private int capacidad=250;
//    private Ciudad aeroOrig;
//    private Ciudad aeroFin;
    private ArrayList<Integer> capTiempo = new ArrayList<>(); // capacidades en el tiempo
            
    Vuelo(){
        
        hSalida = 0;
        mSalida = 0;
        hLlegada = 0;
        mLlegada = 0;
        origen = "";
        destino = "";
        tipoVuelo = "";
        tiempo = 0;
    }
    
    Vuelo(int hSal,int mSal, int hLleg, int mLleg,String orig, String dest){
        hSalida=hSal;
        mSalida=mSal;
        hLlegada=hLleg;
        mLlegada=mLleg;
        origen=orig;
        destino=dest;
        
        //inicializar capacidades en el tiempo
        for (int i=1; i<8; i++){ //los 7 dias de la semana
            capTiempo.add(capacidad);
        }
        
    }
    
    public void print(){
        System.out.println(getOrigen()+"-"+getDestino()+"-"+getTiempo());
    }

    /**
     * @return the hSalida
     */
    public int gethSalida() {
        return hSalida;
    }

    /**
     * @param hSalida the hSalida to set
     */
    public void sethSalida(int hSalida) {
        this.hSalida = hSalida;
    }

    /**
     * @return the mSalida
     */
    public int getmSalida() {
        return mSalida;
    }

    /**
     * @param mSalida the mSalida to set
     */
    public void setmSalida(int mSalida) {
        this.mSalida = mSalida;
    }

    /**
     * @return the hLlegada
     */
    public int gethLlegada() {
        return hLlegada;
    }

    /**
     * @param hLlegada the hLlegada to set
     */
    public void sethLlegada(int hLlegada) {
        this.hLlegada = hLlegada;
    }

    /**
     * @return the mLlegada
     */
    public int getmLlegada() {
        return mLlegada;
    }

    /**
     * @param mLlegada the mLlegada to set
     */
    public void setmLlegada(int mLlegada) {
        this.mLlegada = mLlegada;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @param origen the origen to set
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the tipoVuelo
     */
    public String getTipoVuelo() {
        return tipoVuelo;
    }

    /**
     * @param tipoVuelo the tipoVuelo to set
     */
    public void setTipoVuelo(String tipoVuelo) {
        this.tipoVuelo = tipoVuelo;
    }

    /**
     * @return the tiempo
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     * @param tiempo the tiempo to set
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    
}