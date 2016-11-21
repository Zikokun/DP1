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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitario.funcionesMapa;
import vista.VentanaPrincipal;

/**
 *
 * @author gerson
 */
public class Mapa extends PApplet{
    UnfoldingMap mapDay;
    
    Integrator blendIntegrator = new Integrator(255);
    private List<Object[]> listaPaquetesRutas;
    
    int contador = 99;
    int horaInicial = 0;
    int minutoIncial = 0;
    
    Date fechaInicial = null;
    
    public void setup() {
        try {
            size(800, 600);
            funcionesMapa fMapa = new funcionesMapa();
            horaInicial = fMapa.devolverHoraInicial();
            
            fechaInicial = fMapa.devolverFechaInicio();
            
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
    
    private void inicializacionMarcadores() throws InstantiationException, IllegalAccessException, SQLException{
        contador++;
        if (contador % 100 == 0) {
            blendIntegrator.target(255);
        }
        if (contador % 200 == 0) {
            blendIntegrator.target(120);
        }
        
        funcionesMapa fMapa = new funcionesMapa();
        List<Object[]> lisPaquetesRutas = fMapa.devolverDetallePaquete();
        this.listaPaquetesRutas = lisPaquetesRutas;
        
        mapDay.getMarkers().clear();
        
        for(int i = 0; i < lisPaquetesRutas.size(); i++){
            Object[] ruta = lisPaquetesRutas.get(i);
            float longuitud = (float)ruta[1];
            float latitud = (float)ruta[2];
            boolean esPrimeraVezVuelo = false;
            
            if(longuitud == 0 && latitud == 0){
                longuitud = (float)ruta[3];
                latitud = (float)ruta[4];
                esPrimeraVezVuelo = true;
            }
            ruta[1] = longuitud;
            ruta[2] = latitud;
                
            Location ubicacion = new Location(longuitud,latitud);
            SimplePointMarker ubicacionMarcador = new SimplePointMarker(ubicacion);
            //ubicacionMarcador.setColor(color(255, 0, 0, 100));
            if(esPrimeraVezVuelo) ubicacionMarcador.setColor(obtenerColorAleatorio());
            mapDay.addMarker(ubicacionMarcador);
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
        for(int i = 0; i < lisPaquetesRutas.size(); i++){
            Object[] ruta = lisPaquetesRutas.get(i);
            float longuitudActual = (float)ruta[1];
            float latitudActual = (float)ruta[2];
            
            float diferenciaLonguitud = (float)ruta[5] - (float)ruta[3];
            float diferenciaLatitud = (float)ruta[6] - (float)ruta[4];

            float hipotenusa = (float) Math.sqrt((diferenciaLonguitud*diferenciaLonguitud) + (diferenciaLatitud*diferenciaLatitud));
            
            Marker marker = mapDay.getMarkers().get(i);
            Location actualLocacion = marker.getLocation();
            
            int factorDecremento = 1;
            if(llegoPuntoFinal(diferenciaLonguitud, diferenciaLatitud, ruta)) factorDecremento = 0;
            
            float nuevaLonguitud = longuitudActual + (diferenciaLonguitud/abs(diferenciaLonguitud)*(abs(diferenciaLonguitud)/hipotenusa)) * FACTOR_INCREMENTO * factorDecremento; //tangente
            float nuevaLatitud = latitudActual + (diferenciaLatitud/abs(diferenciaLatitud) * (abs(diferenciaLatitud)/hipotenusa)) * FACTOR_INCREMENTO * factorDecremento; //tangente
            actualLocacion.setLat(nuevaLatitud);
            actualLocacion.setLon(nuevaLonguitud);
            Location nuevaLocacion = actualLocacion;
            mapDay.getMarkers().get(i).setLocation(nuevaLocacion);

            this.listaPaquetesRutas.get(i)[1] = nuevaLonguitud;
            this.listaPaquetesRutas.get(i)[2] = nuevaLatitud;
        } 
    }
    
    private void insertarCoordenadasTablas() throws InstantiationException, IllegalAccessException, SQLException{
        List<Object[]> lisPaquetesRutas = this.listaPaquetesRutas;
        
        funcionesMapa fMapa = new funcionesMapa();
        fMapa.insertarLonguitudYLatitudActualizados(lisPaquetesRutas);
    }
    
    private void cambiarFecha(){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fechaInicial); 
        calendario.set(Calendar.MINUTE, calendario.get(Calendar.MINUTE) + FACTOR_TIEMPO_NORMAL);
        fechaInicial = calendario.getTime();
        
        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //("yyyy-MM-dd HH:mm:ss");
        String sTiempo = fecha.format(fechaInicial);
        VentanaPrincipal.labelMostrarTiempoReal.setText(sTiempo);
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
            inicializacionMarcadores();
            //cambiarReloj();
            cambiarFecha();
            cambiarLonguitudYLatitudActuales();
            insertarCoordenadasTablas();
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
