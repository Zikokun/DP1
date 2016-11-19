/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapa;

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
import java.util.List;

/**
 *
 * @author gerson
 */
public class Mapa extends PApplet{
    
    UnfoldingMap mapDay;
    //UnfoldingMap mapNight;
    Integrator blendIntegrator = new Integrator(255);
    
    UnfoldingMap map;
    Location berlinLocation = new Location(52.5, 13.4);
    Location dublinLocation = new Location(53.35, -6.26);
    Location casa = new Location(-12.11493,-77.01182);
    Location saopaulo = new Location( -23.5475000,-46.6361100);
    
    int contador = 99;
    
    float x = (float) 52.5;
    float y = (float) 13.4;
    
    public void setup() {
        size(800, 600);        
        
        smooth();

        Ani.init(this);
        
        //mapDay = new UnfoldingMap(this,new OpenStreetMap.OpenStreetMapProvider());
        mapDay = new UnfoldingMap(this, new Microsoft.AerialProvider());
        mapDay.setZoomRange(2, 2);
        mapDay.zoomTo(5);
        
        mapDay.setZoomRange(1, 3);
        mapDay.zoomToLevel(3);
        
        mapDay.panTo(new Location(49.6f, 9.4f));

        //Agregar marcadores
        SimplePointMarker casaMarcador = new SimplePointMarker(casa);
        casaMarcador.setColor(color(255, 0, 0, 100));
        mapDay.addMarker(casaMarcador);
        
        SimplePointMarker casaSaoPaulo = new SimplePointMarker(saopaulo);
        casaMarcador.setColor(color(0, 255, 0, 100));
        mapDay.addMarker(casaSaoPaulo);
        
        MapUtils.createDefaultEventDispatcher(this, mapDay/*, mapNight*/);
    }
    
    public void pasoDeDias() {
        contador++;
        if (contador % 100 == 0) {
            blendIntegrator.target(255);
        }
        if (contador % 200 == 0) {
            blendIntegrator.target(120);
        }
        
        Marker marker = mapDay.getMarkers().get(0);
        Location actualLocacion = marker.getLocation();
        float latitudActual = actualLocacion.getLat();
        float nuevaLat =  (latitudActual + 0.5f);
        actualLocacion.setLat(nuevaLat);
        Location nuevaLocacion = actualLocacion;
        marker.setLocation(nuevaLocacion);
        
        marker = mapDay.getMarkers().get(1);
        actualLocacion = marker.getLocation();
        float longActual = actualLocacion.getLon();
        float nuevaLong = longActual + 0.5f;
        actualLocacion.setLon(nuevaLong);
        nuevaLocacion = actualLocacion;
        marker.setLocation(nuevaLocacion);
    }
    
    public void draw() {
        blendIntegrator.update();
        mapDay.draw();
        tint(255, blendIntegrator.value);
        //mapNight.draw();
        pasoDeDias();
        // blendIntegrator.target(255);
    }
    
    public void mouseReleased() {
        // animate the variables x and y in 1.5 sec from mouse click position to the initial values
        Ani.from(this, (float) 1.5, "x", mouseX, Ani.QUINT_IN_OUT);
        Ani.from(this, (float) 1.5, "y", mouseY, Ani.QUINT_IN_OUT);
    }
}
