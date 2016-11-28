/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

/**
 *
 * @author a20090245
 */
public class funcionesControlHiloActualizacionPaquetes {
    private funcionesHiloActualizacionPaquetes HiloActualizadorPaquete;
    
    public funcionesControlHiloActualizacionPaquetes(){
        HiloActualizadorPaquete = null;
    }
    
    public void finalize() {
        HiloActualizadorPaquete = null;
    }

    public boolean EstaCorriendo() {
        return (HiloActualizadorPaquete != null);
    }

    public void Iniciar() {
        if (!EstaCorriendo()) {
            HiloActualizadorPaquete = new funcionesHiloActualizacionPaquetes();
            HiloActualizadorPaquete.start();
        }
    }

    public void Detener() {
        if (EstaCorriendo()) {
            //try { Hilo.join(); } catch(InterruptedException ex) {}
            HiloActualizadorPaquete = null;
        }
    }
}
