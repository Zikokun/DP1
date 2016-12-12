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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import mapa.Mapa;
import modelo.Cromosoma;
import vista.PanelSim;
import vista.VentanaPrincipal;
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
        public Cromosoma flagSalida;
        public funcionesHiloEjecSimu(JPanel Vent, int IntervaloTiempo, funcionesDibujoEjecSimu Dib) {
		this.Panel = Vent;
		this.IntervaloTiempo = IntervaloTiempo;
		this.Dib = Dib;
		DebeDetenerse = false;
        }
        public funcionesHiloEjecSimu(JPanel Vent,int IntervaloTiempo, int tipoSimu){
            this.Panel = Vent;
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
                    System.out.println("Boton mostrar Pausa: " + detenerse);
                    SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    
                    String fechaActual = "";
                    if(tipoSimu == 0){
                        fechaActual = "2016-10-04 00:04:00";
                    }else if(tipoSimu == 1){
                        fechaActual = "2016-12-03 16:00:00";
                    }
                    Date fecha = null;
                    try {
                        fecha = formatoDeFecha.parse(fechaActual);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                    Calendar calendarDate = Calendar.getInstance();
                    calendarDate.setTime(fecha);
                    while(true){
                        //if(detenerse == BOTON_PAUSA_NOVISIBLE){
                        
                          
                        
                        System.out.println("en bucle del hilo");
                        if (BOTON_PAUSA_APRETADO != 1) {
                            System.out.println("dentro del if");
                            //funcionesPanelCrearEnvio nuevo = new funcionesPanelCrearEnvio();
                            int estadoFinal;
                            int estadoInicial;

                            if (tipoSimu == 2) {
                                estadoInicial = SIN_ENVIAR.ordinal();
                                estadoFinal = SIN_ENVIAR_CON_RUTA.ordinal();
                                funcR.setFlagTipoSimuTotal(0);
                                funcR.setMensajeCaida("");
                                funcR.setFlagSinPaqRutear(0);
                            } else if (tipoSimu == 0) {
                                estadoFinal = CON_TRES_DIAS.ordinal();
                                estadoInicial = CON_TRES_DIAS_SIN_RUTA.ordinal();
                                funcR.setFlagTipoSimuTotal(0);
                                funcR.setMensajeCaida("");
                                funcR.setFlagSinPaqRutear(0);
                            } else { // tipoSimu==1
                                estadoFinal = SIMULACION_SIN_TRES_DIAS.ordinal();
                                estadoInicial = SIMULACION_SIN_TRES_DIAS_SIN_RUTA.ordinal();
                                funcR.setFlagTipoSimuTotal(1);
                                funcR.setMensajeCaida("");
                                funcR.setFlagSinPaqRutear(0);
                                funcR.setContAux(110);
                            }
                            // funcR.ruteoPedidosManual(estadoInicial,estadoFinal);
                            System.out.println("Ruteo pedido en la fecha = " + calendarDate.getTime());
                            //nuevo.GuardarMensajes("Ruteo en la fecha = " + calendarDate.getTime().toString());
                            if(DebeDetenerse)
                                break;
                            
                            flagSalida=funcR.ruteoPedidosTresDias(estadoInicial, estadoFinal, calendarDate);
                            
                            if(funcR.getFlagSinPaqRutear()==1 && tipoSimu==0){  
                                this.Detener();
                            
                            }
                            if(flagSalida==null){//quiere decir que ya se cayo la simulacion
                                
                                JOptionPane.showMessageDialog(this.Panel ,funcR.getMensajeCaida()+" Fecha Colapso: "+Mapa.getFechaInicial().toString(),"Condicion de caida" , JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            calendarDate.add(Calendar.HOUR_OF_DAY, 1);
                        } else {
                            System.out.println("En pausa no debe rutear");
                        }
                        //Thread.sleep(this.IntervaloTiempo);
                        //}else
                        //break;
                    }
                    //System.out.println("FUERA DEL WHILE");
                //} catch(InterruptedException ex) {
		//} catch (InstantiationException | IllegalAccessException | IOException | SQLException ex) {
                //Logger.getLogger(funcionesHiloEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                Logger.getLogger(funcionesHiloEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(funcionesHiloEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(funcionesHiloEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(funcionesHiloEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(funcionesHiloEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
