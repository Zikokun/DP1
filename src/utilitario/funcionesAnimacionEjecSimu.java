/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;
import static constantes.constantesGenerales.BOTON_PAUSA_VISIBLE;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import mapa.Mapa;
import static mapa.Mapa.mostrarBotonPausa;
import vista.VentanaPrincipal;

/**
 *
 * @author FranciscoMartin
 */
public class funcionesAnimacionEjecSimu {
    
	private JPanel Vent;
	private int IntervaloTiempo;
        private int tipoSimu;
	private funcionesDibujoEjecSimu Dib;
        private ArrayList<funcionesDibujoEjecSimu> ADib;
	private funcionesHiloEjecSimu Hilo;
        
        public funcionesAnimacionEjecSimu(JPanel Vent, int IntervaloTiempo, ArrayList<funcionesDibujoEjecSimu> Dib) {
		this.Vent = Vent;
		this.IntervaloTiempo = IntervaloTiempo;
		this.ADib = Dib;
		Hilo = null;
	}
        public funcionesAnimacionEjecSimu(JPanel Vent,int IntervaloTiempo, int tipoSimu) {
		this.Vent = Vent;
                this.IntervaloTiempo = IntervaloTiempo;
		Hilo = null;
                this.tipoSimu=tipoSimu;
	}
	public funcionesAnimacionEjecSimu(JPanel Vent, int IntervaloTiempo, funcionesDibujoEjecSimu Dib) {
		this.Vent = Vent;
		this.IntervaloTiempo = IntervaloTiempo;
		this.Dib = Dib;
		Hilo = null;
	}
	public void finalize() {
		Hilo.Detener();
	}
 	public boolean EstaCorriendo() {
 		return (Hilo != null) ;
 	}
	public void Iniciar() {
		if(!EstaCorriendo()) {
			Hilo = new funcionesHiloEjecSimu(Vent,IntervaloTiempo,tipoSimu);
                         System.out.println("antes del start del hilo");
			Hilo.start();
		}
	}
	public void Detener() {
		if(EstaCorriendo()) {
			Hilo.Detener();
			try { Hilo.join(); } catch(InterruptedException ex) {}
			Hilo = null;
		}
  	}
}
