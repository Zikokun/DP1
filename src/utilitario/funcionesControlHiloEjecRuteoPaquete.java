/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

/**
 *
 * @author FranciscoMartin
 */
public class funcionesControlHiloEjecRuteoPaquete {
    private funcionesHiloEjecRuteoPaquete Hilo;
    private funcionesRuteo enrutador;
    public funcionesControlHiloEjecRuteoPaquete(funcionesRuteo enrutador){
         this.enrutador=enrutador;
         System.out.println("constructor del contro hilo");
         Hilo= null;//new funcionesHiloEjecRuteoPaquete(enrutador);
         if(Hilo == null) System.out.println("rd nulo .... ");
    }

    public funcionesControlHiloEjecRuteoPaquete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void finalize() {
		Hilo=null;
	}
 	public boolean EstaCorriendo() {
 		return (Hilo != null);
 	}
	public void Iniciar() {
            System.out.println(EstaCorriendo());
		if(!EstaCorriendo()) {
			Hilo = new funcionesHiloEjecRuteoPaquete(enrutador);
			Hilo.start();
                        System.out.println("Inicio El Hilo");
		}
	}
	public void Detener() {
		if(EstaCorriendo()) {
			//try { Hilo.join(); } catch(InterruptedException ex) {}
			Hilo = null;
                        System.out.println("murio el hilo :V");
		}
  	}
}
