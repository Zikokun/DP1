/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;
import static constantes.constanteEstadoPaquete.*;
import static constantes.constantesGenerales.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import mapa.Mapa;
/**
 *
 * @author FranciscoMartin
 */
public class funcionesHiloEjecSimu extends Thread{
    
	private JPanel Panel;
	private int IntervaloTiempo;
        private int tipoSimu;
        private int cont;
        private int estadoSimu=1;
	private funcionesDibujoEjecSimu Dib;
        private ArrayList<funcionesDibujoEjecSimu> ADib;
	private boolean DebeDetenerse;
        private funcionesPanelSimulacion func = new funcionesPanelSimulacion();
        private funcionesRuteo funcR = new funcionesRuteo();
        private funcionesPanelSimulacion fps = new funcionesPanelSimulacion();
        private int detenerse;
        
        public funcionesHiloEjecSimu(JPanel Vent, int IntervaloTiempo, funcionesDibujoEjecSimu Dib) {
		this.Panel = Vent;
		this.IntervaloTiempo = IntervaloTiempo;
		this.Dib = Dib;
		DebeDetenerse = false;
        }
        public funcionesHiloEjecSimu(int IntervaloTiempo, int tipoSimu){
            this.DebeDetenerse = false;
            this.tipoSimu=tipoSimu;
            this.IntervaloTiempo = IntervaloTiempo;
            detenerse = Mapa.mostrarBotonPausa;
        }
	public funcionesHiloEjecSimu(JPanel Vent, int IntervaloTiempo, ArrayList<funcionesDibujoEjecSimu> Dib) {
		this.Panel = Vent;
		this.IntervaloTiempo = IntervaloTiempo;
		this.ADib = Dib;
		DebeDetenerse = false;
	}
	public void Detener() {
		DebeDetenerse = true;
	}
	public void run() {
                
                try{
                    System.out.println("Boton:"+BOTON_PAUSA_NOVISIBLE);
                    while(detenerse != BOTON_PAUSA_NOVISIBLE){
                         System.out.println("en bucle del hilo");
                        if(BOTON_PAUSA_APRETADO!=1){
                            System.out.println("dentro del if");
                            //func.lectorPaquetesSimulacion(tipoSimu);
                            int estadoFinal;
                            int estadoInicial;

                            if(tipoSimu == 2){
                                estadoInicial = SIN_ENVIAR.ordinal();
                                estadoFinal = SIN_ENVIAR_CON_RUTA.ordinal();
                            }
                            else if(tipoSimu == 0) {
                                estadoFinal = CON_TRES_DIAS.ordinal();
                                estadoInicial = CON_TRES_DIAS_SIN_RUTA.ordinal();
                            }
                            else {
                                estadoFinal = SIMULACION_SIN_TRES_DIAS.ordinal();
                                estadoInicial = SIMULACION_SIN_TRES_DIAS_SIN_RUTA.ordinal();
                            }

                            //fps.lectorPaquetesSimulacion(0);
                            funcR.ruteoPedidosManual(estadoInicial,estadoFinal);

                        }
                        Thread.sleep(this.IntervaloTiempo);
                    }
                    System.out.println("FUERA DEL WHILE");
                } catch(InterruptedException ex) {
		} catch (InstantiationException | IllegalAccessException | IOException | SQLException ex) {
                Logger.getLogger(funcionesHiloEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
                }
	}
}
