/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author a20111055 - Vivian
 */
public class Ruta {
    
    private ArrayList<Vuelo> vuelos=new ArrayList<>();
    private int tiempo =0;
    
    Ruta(){
    }
    
    public Ruta(Vuelo vuel, int tiemp){
        vuelos.add(vuel);
        tiempo=tiemp;
    }
    
    public Ruta(Vuelo vuel1, Vuelo vuel2, int tiemp){
        vuelos.add(vuel1);
        vuelos.add(vuel2);
        tiempo=tiemp;
    }
    
    public void print(){
        for(int i=0; i<getVuelos().size(); i++){
            getVuelos().get(i).print();
//           System.out.print(vuelos.get(i).getAeroOrig().getCodAeropuerto()+"-"+
//                            vuelos.get(i).getAeroFin().getCodAeropuerto()+"/");
        }
        System.out.println("Tiempo ruta: "+getTiempo());
    }
    public String printS(){
        String salida="";
        for(int i=0; i<getVuelos().size(); i++){
            salida.concat(getVuelos().get(i).printS());
//           System.out.print(vuelos.get(i).getAeroOrig().getCodAeropuerto()+"-"+
//                            vuelos.get(i).getAeroFin().getCodAeropuerto()+"/");
        }
        salida.concat(" Tiempo ruta: "+getTiempo());
        return salida;
    }
    /**
     * @return the vuelos
     */
    public ArrayList<Vuelo> getVuelos() {
        return vuelos;
    }

    /**
     * @param vuelos the vuelos to set
     */
    public void setVuelos(ArrayList<Vuelo> vuelos) {
        this.vuelos = vuelos;
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
    
    
}
