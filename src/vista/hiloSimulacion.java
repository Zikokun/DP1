/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
/**
 *
 * @author FranciscoMartin
 */
public class hiloSimulacion extends Thread {
    
    private final JTextArea txta;
    private final panelEjecSimu simu;
    public final Object monitor = new Object();
    public  int numero;
    
    public hiloSimulacion(JTextArea txta, panelEjecSimu simu){
        this.txta = txta;
        this.simu = simu;
        this.numero=100;
    }
    
    @Override
    public void run() {
        simu.hiloSimu();
    }
    
    public void escribeTexto(String str){
        txta.setText(txta.getText() + str + "\n");
        simu.actualizaDisplay();
        try {
            synchronized(monitor){
                monitor.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(hiloSimulacion.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
}
