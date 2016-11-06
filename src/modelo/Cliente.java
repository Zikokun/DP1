/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static constantes.constantesEstadoCliente.*;
import java.util.Date;

/**
 *
 * @author gerson
 */
public class Cliente {
    private Date fechaInscripcion;
    private int estado;
    private Persona persona;

    public Cliente(){
        fechaInscripcion = null;
        estado = NO_ACTIVO_CLIENTE.ordinal();
        persona = null;
    }
    
    public Cliente(Date fecInscripcion, Persona persona){
        fechaInscripcion = fecInscripcion;
        estado = ACTIVO_CLIENTE.ordinal();
        this.persona = persona;
    }
    
    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    /**
     * @param fechaInscripcion the fechaInscripcion to set
     */
    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
