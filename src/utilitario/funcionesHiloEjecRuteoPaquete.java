/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constantesGenerales.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapa.Mapa;

/**
 *
 * @author FranciscoMartin
 */
public class funcionesHiloEjecRuteoPaquete extends Thread {
    
    private funcionesRuteo enrutador;
    private int detenerse;
    public funcionesHiloEjecRuteoPaquete(funcionesRuteo enrutador){
        this.enrutador=enrutador;
        detenerse = Mapa.mostrarBotonPausa;
    }
   
    public void run(){
        try{
            while(true){
                if(detenerse == BOTON_PAUSA_NOVISIBLE){
                    enrutador.ruteoPedidosManual(0);
                }
                
                Thread.sleep(TIEMPO_ENTRE_RUTEO);
                
                detenerse = Mapa.mostrarBotonPausa; 
            }
        } catch(InterruptedException ex) {
        
        } catch (InstantiationException | IllegalAccessException | IOException | SQLException ex) {
            Logger.getLogger(funcionesHiloEjecRuteoPaquete.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
