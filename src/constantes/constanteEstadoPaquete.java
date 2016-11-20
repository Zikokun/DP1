/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constantes;

/**
 *
 * @author gerson
 */
public enum constanteEstadoPaquete {
    SIN_ENVIAR ("Paquete sin Enviar"),
    EN_VUELO ("Paquete en vuelo"), 
    EN_ALMACEN ("Paquete en alamcen"), 
    ENTREGADO ("Paquete entregado"),
    SIN_ENVIAR_SIN_RUTA("Paquete sin Enviar y sin ruta asignada"),
    CON_TRES_DIAS("Paquete para simulación de tres dias"),
    SIMULACION_SIN_TRES_DIAS("Paquete para simulación de tres dias");
    
    private final String detalle;

    public String getDetalle() {
        return detalle;
    }
    
    constanteEstadoPaquete (String descripcion) { 
        this.detalle = descripcion;
    }
}
