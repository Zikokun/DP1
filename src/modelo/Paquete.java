/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static constantes.constanteEstadoPaquete.*;
import java.util.Date;

/**
 *
 * @author gerson
 */
public class Paquete {
    private Ciudad almacenOrigen;
    private Ciudad alamcenDestino;
    private Date fechaEnvio;
    private Date fechaRecepcion;
    private String descripcion;
    private int estado;
    private Cliente remitente;
    private Persona receptor;
    private String numeroRastreo;
    
    public Paquete(){
        almacenOrigen = null;
        alamcenDestino = null;
        fechaEnvio = null;
        fechaRecepcion = null;
        descripcion = "";
        estado = SIN_ENVIAR.ordinal();
        remitente = null;
        receptor = null;
    }
    
    public Paquete(Ciudad almOrigen, Ciudad almDestino, Date fecEnvio, Date fecRecepcion, String descri, Cliente remitenteCliente, Persona receptorPersona){
        almacenOrigen = almOrigen;
        alamcenDestino = almDestino;
        fechaEnvio = fecEnvio;
        fechaRecepcion = fecRecepcion;
        descripcion = descri;
        estado = SIN_ENVIAR.ordinal();
        remitente = remitenteCliente;
        receptor = receptorPersona;
    }
    
    public Ciudad getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(Ciudad almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public Ciudad getAlamcenDestino() {
        return alamcenDestino;
    }

    public void setAlamcenDestino(Ciudad alamcenDestino) {
        this.alamcenDestino = alamcenDestino;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Cliente getRemitente() {
        return remitente;
    }

    public void setRemitente(Cliente remitente) {
        this.remitente = remitente;
    }

    public Persona getReceptor() {
        return receptor;
    }

    public void setReceptor(Persona receptor) {
        this.receptor = receptor;
    }

    public String getNumeroRastreo() {
        return numeroRastreo;
    }

    public void setNumeroRastreo(String numeroRastreo) {
        this.numeroRastreo = numeroRastreo;
    }
}
