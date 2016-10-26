/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author a20111055 - Vivian
 */
public class Ciudad {
    private int id;
    private String codCiudad;
    private String pais;
    private String ciudad;
    private String abreviado;
    private String continente;
    public int huso;
    private int capacidadTotal=600;
    private String []dias={"Lun","Mar","Mie","Jue","Vie","Sab","Dom"};
    public ArrayList<String> vecinos = new ArrayList<>(); //Vecinos son las ciudades a las que hay un vuelo desde esta ciudad
    public ArrayList<Vuelo> vuelos = new ArrayList<>(); //Lista de vuelos que salen de esta ciudad
    public HashMap<String,ArrayList<Ruta>> rutas= new HashMap<>(); // llave es el codigo de la ciudad destino, valor es las rutas posibles
    public HashMap<String, Integer> capTiempo= new HashMap<>(); // capacidades en el tiempo, key es : dia-hora:00 / dia-hora:01 
    
    Ciudad(){
        id=-1;
        codCiudad="";
        pais="";
        ciudad="";
        abreviado="";
        continente="";
    }
    
    Ciudad(int cod, String codAeropuerto, String ciud,String pai, String abrev, String cont){
        id=cod;
        codCiudad=codAeropuerto;
        ciudad=ciud;
        pais=pai;
        abreviado=abrev;
        continente= cont;
        //inicializar capacidades en tiempo
        for(int i=0; i<7; i++){
            for(int j=0; j<24; j++){
                String llave=dias[i]+"-"+j+":00";
                capTiempo.put(llave,capacidadTotal);
                String llave2=dias[i]+"-"+j+":00";
                capTiempo.put(llave2,capacidadTotal);
            }
        }
    }
    
    public void reiniciarCaps(){
        //inicializar capacidades en el tiempo
        for(int i=0; i<7; i++){
            for(int j=0; j<24; j++){
                String llave=dias[i]+"-"+j+":00";
                capTiempo.put(llave,capacidadTotal);
                String llave2=dias[i]+"-"+j+":00";
                capTiempo.put(llave2,capacidadTotal);
            }
        }
    }
    
    public void print(){
        System.out.println(codCiudad + " ,"+this.getCiudad()+", "+pais+", "+continente+"-"+huso);
    }
    
    /**
     * @return the codCiudad
     */
    public String getCodAeropuerto() {
        return codCiudad;
    }

    /**
     * @param codAeropuerto the codCiudad to set
     */
    public void setCodAeropuerto(String codAeropuerto) {
        this.codCiudad = codAeropuerto;
    }

    /**
     * @return the continente
     */
    public String getContinente() {
        return continente;
    }

    /**
     * @param continente the continente to set
     */
    public void setContinente(String continente) {
        this.continente = continente;
    }


    /**
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the abreviado
     */
    public String getAbreviado() {
        return abreviado;
    }

    /**
     * @param abreviado the abreviado to set
     */
    public void setAbreviado(String abreviado) {
        this.abreviado = abreviado;
    }

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidadTotal;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidadTotal = capacidad;
    }

}
