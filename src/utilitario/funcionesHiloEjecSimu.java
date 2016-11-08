/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author FranciscoMartin
 */
public class funcionesHiloEjecSimu extends Thread{
    
	private JPanel Panel;
	private int IntervaloTiempo;
	private funcionesDibujoEjecSimu Dib;
        private ArrayList<funcionesDibujoEjecSimu> ADib;
	private boolean DebeDetenerse;

        public funcionesHiloEjecSimu(JPanel Vent, int IntervaloTiempo, funcionesDibujoEjecSimu Dib) {
		this.Panel = Vent;
		this.IntervaloTiempo = IntervaloTiempo;
		this.Dib = Dib;
		DebeDetenerse = false;
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
		Graphics g = Panel.getGraphics();
		Color colorFondo = new Color(205, 205, 205);
                
		try {
			while(!DebeDetenerse) {
				// Borro el anterior
				
                                g.setColor(colorFondo);
                                //Panel.repaint();
				for(int i=0;i<(ADib.size());i++){
                                    ADib.get(i).Dibujar(g);
                                }
				// Dibujo en la nueva posicion
				for(int i=0;i<(ADib.size());i++){
                                    ADib.get(i).Mover();
                                }
                                for(int i=0;i<(ADib.size());i++){
                                    if(ADib.get(i).getCentroX()==ADib.get(i).getLimX() && ADib.get(i).getCentroY()==ADib.get(i).getLimY())
                                       Detener();
                                }
				g.setColor(Color.GREEN);
				for(int i=0;i<(ADib.size());i++){
                                    ADib.get(i).Dibujar(g);
                                }
				// Espero
				Thread.sleep(IntervaloTiempo);
			}
		} catch(InterruptedException ex) {
		}
		g.dispose();
	}
}
