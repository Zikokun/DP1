/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author a20111055 -Vivian
 */
public class Lectura {
    public void leerArchivos(String archAeropuertos, String archVuelos, String archPedidos, String archHusos,
            ArrayList<Vuelo> vuelos,TreeMap<String, Ciudad> aeropuertos, ArrayList<Pedido> pedidos ){
        leerAeropuertos(archAeropuertos, aeropuertos);
        leerVuelos(archVuelos, vuelos);
        leerPedidos(archPedidos, pedidos);
        leerHusos(archHusos, aeropuertos);
    }
    
    public void leerHusos(String archHusos,TreeMap<String,Ciudad> ciudades){
    }
    
    public void leerAeropuertos(String archAeropuertos,TreeMap<String,Ciudad> aeropuertos){
    }
    
    public void leerVuelos(String archVuelos, ArrayList<Vuelo> vuelos){
    }
    
    public void leerPedidos(String archPedidos,ArrayList<Pedido> pedidos){
    }
}