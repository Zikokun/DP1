/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author a20111055 - Vivian
 */
public class Pedido {
    private String origen;
    private String destino;
    private int cant; //cantidad de paquetes en un pedido
    private int hora;
    private int min;
    private int dia;
    private int mes;
    private int año;
    private int diaSemana;
    
    Pedido(){
        origen="";
        destino="";
        cant=0;
        hora=0;
        min=0;
        dia=0;
        mes=0;
        año=0;
    }
    
     public Pedido(String ori, String dest, int cantidad, int horaL, int minL,int diaL,int mesL,int añoL){
        origen=ori;
        destino=dest;
        cant=cantidad;
        hora=horaL;
        min=minL;
        dia=diaL;
        mes=mesL;
        año=añoL;                
    }
    
    public void print(){
        System.out.println(getOrigen()+"-"+getDestino()+"-"+getCant()+"-"+getHora()+":"+getMin());
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
     * @return the cant
     */
    public int getCant() {
        return cant;
    }

    /**
     * @param cant the cant to set
     */
    public void setCant(int cant) {
        this.cant = cant;
    }

    /**
     * @return the hora
     */
    public int getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(int hora) {
        this.hora = hora;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the mes
     */
    public int getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(int mes) {
        this.mes = mes;
    }

    /**
     * @return the año
     */
    public int getAño() {
        return año;
    }

    /**
     * @param año the año to set
     */
    public void setAño(int año) {
        this.año = año;
    }

    /**
     * @return the diaSemana
     */
    public int getDiaSemana() {
        return diaSemana;
    }

    /**
     * @param diaSemana the diaSemana to set
     */
    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }
    
    
}
