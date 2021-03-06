/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapa;

import static constantes.constantesGenerales.*;
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MarkerManager;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.utils.Integrator;
import de.looksgood.ani.Ani;
import java.nio.file.Files;
import org.apache.logging.log4j.core.jackson.SimpleMessageDeserializer;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.providers.*;
import java.awt.Color;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitario.funcionesMapa;
import utilitario.funcionesPanelCrearEnvio;
import vista.PanelSim;
import vista.VentanaPrincipal;
import vista.panelEjecSimu;
import utilitario.funcionesHiloEjecSimu;
/**
 *
 * @author gerson
 */
public class Mapa extends PApplet{
    public String mensajeAnt="";
    /**
     * @return the fechaInicial
     */
    public static Date getFechaInicial() {
        return fechaInicial;
    }

    /**
     * @param aFechaInicial the fechaInicial to set
     */
    public static void setFechaInicial(Date aFechaInicial) {
        fechaInicial = aFechaInicial;
    }
    UnfoldingMap mapDay;
    
    Integrator blendIntegrator = new Integrator(255);
    private List<Object[]> listaPaquetesRutas;
    private List<Object[]> listaVuelos;
    
    int contador = 99;
    int horaInicial = 0;
    int minutoIncial = 0;
    
    private static Date fechaInicial = null;
    public static int mostrarBotonPausa;
    public static int fueApretado;
    
    public void setup() {
        //System.out.println("Entro al seteado del mapa");
        try {
            mostrarBotonPausa = BOTON_PAUSA_VISIBLE;
            fueApretado = BOTON_PAUSA_NO_APRETADO;
            
            if(fueApretado == BOTON_PAUSA_NO_APRETADO) VentanaPrincipal.botonPausa.setText(TEXTO_PAUSAR);
            if(mostrarBotonPausa == BOTON_PAUSA_VISIBLE) VentanaPrincipal.botonPausa.setVisible(true);
            
            size(800, 600);
            Thread.sleep(1200);
            funcionesMapa fMapa = new funcionesMapa();
            horaInicial = fMapa.devolverHoraInicial();
            
            setFechaInicial(fMapa.devolverFechaInicio());
            
            VentanaPrincipal.labelMostrarTiempoReal.setVisible(true);
            smooth();
            
            Ani.init(this);
            
            mapDay = new UnfoldingMap(this, new Microsoft.AerialProvider());
            mapDay.setZoomRange(2, 2);
            mapDay.zoomTo(5);
            
            mapDay.setZoomRange(1, 3);
            mapDay.zoomToLevel(3);
            
            mapDay.panTo(new Location(49.6f, 9.4f));
            
            MapUtils.createDefaultEventDispatcher(this, mapDay);
            
        } catch (InstantiationException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
       
    private int obtenerColorAleatorio(){
        Random rand = new Random();
        float r = (float) (rand.nextFloat() / 2f + 0.5);
        float g = (float) (rand.nextFloat() / 2f + 0.5);
        float b = (float) (rand.nextFloat() / 2f + 0.5);

        int randomColor = Color.HSBtoRGB(r, g, b);
        return randomColor;
    }
    
    private int colorCapacidad(int capacidadActual){
        if (capacidadActual > 0 && capacidadActual < 200) {
            return color(0, 255, 0, 100);
        } else if (capacidadActual >= 200 && capacidadActual < 400) {
            return color(0, 0, 255, 100);
        } else if (capacidadActual >= 400 && capacidadActual <= 600) {
            return color(255, 0, 0, 100);
        }
        return color(255, 0, 0, 100);
    }
    
    private void inicializacionMarcadores() throws InstantiationException, IllegalAccessException, SQLException{
        contador++;
        if (contador % 100 == 0) {
            blendIntegrator.target(255);
        }
        if (contador % 200 == 0) {
            blendIntegrator.target(120);
        }
        
        funcionesMapa fMapa = new funcionesMapa();
        List<Object[]> lisPaquetesRutas = fMapa.devolverDetallePaquete(PanelSim.getTipoSim());
        this.listaVuelos = new ArrayList<Object[]>();
        this.listaPaquetesRutas = lisPaquetesRutas;
        
        mapDay.getMarkers().clear();
        int idVueloAnterior = -1;
        int capacidadActual = 0;
        
        for (int i = 0; i < lisPaquetesRutas.size(); i++) {
            Object[] ruta = lisPaquetesRutas.get(i);
            
                float longuitud = (float) ruta[1];
                float latitud = (float) ruta[2];

                boolean esPrimeraVezVuelo = false;

                if (longuitud == 0 && latitud == 0) {
                    longuitud = (float) ruta[3];
                    latitud = (float) ruta[4];
                    esPrimeraVezVuelo = true;
                }

                lisPaquetesRutas.get(i)[1] = longuitud;
                lisPaquetesRutas.get(i)[2] = latitud;   
                
            if (idVueloAnterior != (int) ruta[7]) { //Ruta 7 siempre es idVuelo
                
                if (idVueloAnterior != -1) {
                    int ultimoIndice = mapDay.getMarkers().size() - 1;
                    Marker markerAnterior = mapDay.getMarkers().get(ultimoIndice);
                    markerAnterior.setColor(colorCapacidad(capacidadActual));
                    
                    //Aqui imprimir capacidadActual y idVueloAnterior
                    //------------------>>>>
                    capacidadActual = 0;
                }
                Location ubicacion = new Location(latitud, longuitud);
                SimplePointMarker ubicacionMarcador = new SimplePointMarker(ubicacion);
                ubicacionMarcador.setColor(color(255, 0, 0, 100));
                //if(esPrimeraVezVuelo) ubicacionMarcador.setColor(obtenerColorAleatorio());
                mapDay.addMarker(ubicacionMarcador);
                idVueloAnterior = (int) ruta[7];
                listaVuelos.add(ruta);
            }else{
                capacidadActual++;
            }
        }
    }
    
    private boolean llegoPuntoFinal(float diferenciaLonguitud, float diferenciaLatitud, Object[] ruta){
        boolean llegoDestino = false;
        boolean llegoDestinoLonguitud = false;
        boolean llegoDestinoLatitud = false;
        
        if(diferenciaLonguitud > 0){
            if((float)ruta[1] > (float)ruta[5]){
                llegoDestinoLonguitud = true;
            }
        }else{
            if((float)ruta[1] < (float)ruta[5]){
                llegoDestinoLonguitud = true;
            }
        }
        
        if(diferenciaLatitud > 0){
            if((float)ruta[2] > (float)ruta[6]){
                llegoDestinoLatitud = true;
            }
        }else{
            if((float)ruta[2] < (float)ruta[6]){
                llegoDestinoLatitud = true;
            }
        }
        
        llegoDestino = llegoDestinoLonguitud || llegoDestinoLatitud;
        
        return llegoDestino;
    }
    
    private void cambiarLonguitudYLatitudActuales(){
        List<Object[]> lisPaquetesRutas = this.listaPaquetesRutas;
        int idVueloAnterior = -1;
        int idContadorVuelo = 0;
        for (int i = 0; i < lisPaquetesRutas.size() && fueApretado == BOTON_PAUSA_NO_APRETADO; i++) {
            Object[] ruta = lisPaquetesRutas.get(i);
            
            int idVuelo = (int) ruta[7];
            
            float longuitudActual = (float) ruta[1];
            float latitudActual = (float) ruta[2];

            float diferenciaLonguitud = (float) ruta[5] - (float) ruta[3];
            float diferenciaLatitud = (float) ruta[6] - (float) ruta[4];

            float hipotenusa = (float) Math.sqrt((diferenciaLonguitud * diferenciaLonguitud) + (diferenciaLatitud * diferenciaLatitud));

            

            int factorDecremento = 1;
            if (llegoPuntoFinal(diferenciaLonguitud, diferenciaLatitud, ruta)) {
                factorDecremento = 0;
            }

            float nuevaLonguitud = longuitudActual + (diferenciaLonguitud / abs(diferenciaLonguitud) * (abs(diferenciaLonguitud) / hipotenusa)) * FACTOR_INCREMENTO * factorDecremento; //tangente
            float nuevaLatitud = latitudActual + (diferenciaLatitud / abs(diferenciaLatitud) * (abs(diferenciaLatitud) / hipotenusa)) * FACTOR_INCREMENTO * factorDecremento; //tangente
            
            if(idVuelo != idVueloAnterior){
                Marker marker = mapDay.getMarkers().get(idContadorVuelo);
                Location actualLocacion = marker.getLocation();
                actualLocacion.setLat(nuevaLatitud);
                actualLocacion.setLon(nuevaLonguitud);
                Location nuevaLocacion = actualLocacion;
                mapDay.getMarkers().get(idContadorVuelo).setLocation(nuevaLocacion);
                idContadorVuelo++;
                idVueloAnterior = idVuelo;
            }
            

            this.listaPaquetesRutas.get(i)[1] = nuevaLonguitud;
            this.listaPaquetesRutas.get(i)[2] = nuevaLatitud;
        }
        //System.out.println("Se tienen hasta el momento " + idContadorVuelo);
    }
    
    private void insertarCoordenadasTablas() throws InstantiationException, IllegalAccessException, SQLException{
        List<Object[]> lisPaquetesRutas = this.listaPaquetesRutas;
        if (lisPaquetesRutas.size() > 0) {
            funcionesMapa fMapa = new funcionesMapa();
            fMapa.insertarLonguitudYLatitudActualizados(lisPaquetesRutas);
        }
    }
    
    private void cambiarFecha(){
        if(fueApretado == BOTON_PAUSA_NO_APRETADO){
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(getFechaInicial());
            calendario.set(Calendar.MINUTE, calendario.get(Calendar.MINUTE) + FACTOR_TIEMPO_NORMAL);
            setFechaInicial(calendario.getTime());

            DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //("yyyy-MM-dd HH:mm:ss");
            String sTiempo = fecha.format(getFechaInicial());
            VentanaPrincipal.labelMostrarTiempoReal.setText(sTiempo);    
        }
    }
    
    private void cambiarReloj(){
        /*if(minutoIncial == 59){
            if (horaInicial == 23) {
                horaInicial = 0;
            } else {
                horaInicial++;
            }
            minutoIncial = 0;
        }else{
            minutoIncial++;
        }*/
        
        if(minutoIncial + FACTOR_TIEMPO_NORMAL > 59){
            int minAgregar = minutoIncial + FACTOR_TIEMPO_NORMAL - UN_MINUTO;
            int horasAgregar = minAgregar / UN_MINUTO;
            int minSobrantesAgregar = minAgregar % UN_MINUTO;
            
            minutoIncial = minSobrantesAgregar;
            
            if(horasAgregar + horaInicial > 24){
                int horaAdicional = horasAgregar + horaInicial - UNA_HORA;
                int horaAgregarAdicional = horaAdicional % UNA_HORA;
                horaInicial = horaAgregarAdicional;
            }else horaInicial++;
        }else minutoIncial+= FACTOR_TIEMPO_NORMAL;
        
        String sHor = "";
        String sMin = "";
        
        if(minutoIncial < 10) sMin = "0" + String.valueOf(minutoIncial);
        else sMin = String.valueOf(minutoIncial);
        
        if(horaInicial < 0) sHor = "0" + String.valueOf(horaInicial);
        else sHor = String.valueOf(horaInicial);
        
        String sTiempo = sHor + ":" + sMin;
                
        VentanaPrincipal.labelMostrarTiempoReal.setText(sTiempo);
    }
    
    public void draw() {
        blendIntegrator.update();
        mapDay.draw();
        tint(255, blendIntegrator.value);
        try {
            //System.out.println("Esta en el dibujado");
            inicializacionMarcadores();
            //cambiarReloj();
            cambiarFecha();
            cambiarLonguitudYLatitudActuales();
            insertarCoordenadasTablas();
            funcionesPanelCrearEnvio nuevo = new funcionesPanelCrearEnvio();
            //String mensajes = nuevo.GetMensajes();
            
//            String mensajes =funcionesHiloEjecSimu.mensajeLog;
//            if(this.mensajeAnt.equals("")){
//                this.mensajeAnt=mensajes;
//            }else{
//                if(!this.mensajeAnt.equals(mensajes)){
//                    if(!mensajes.equals(""))panelEjecSimu.logMensajesPanel.append(mensajes+"\n");
//                }
//            }
        } catch (InstantiationException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mouseReleased() {
        // animate the variables x and y in 1.5 sec from mouse click position to the initial values
        Ani.from(this, (float) 1.5, "x", mouseX, Ani.QUINT_IN_OUT);
        Ani.from(this, (float) 1.5, "y", mouseY, Ani.QUINT_IN_OUT);
    }

    public List<Object[]> getListaPaquetesRutas() {
        return listaPaquetesRutas;
    }

    public void setListaPaquetesRutas(List<Object[]> listaPaquetesRutas) {
        this.listaPaquetesRutas = listaPaquetesRutas;
    }

}
