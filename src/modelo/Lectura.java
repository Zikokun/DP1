/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String linea;
        String []valor;
        
        try {
            BufferedReader bufer= new BufferedReader(new FileReader(archHusos));
            try {
                while((linea=bufer.readLine())!= null){
                    valor=linea.trim().split("\t");
                    String ciudad=valor[1];
                    int huso=Integer.parseInt(valor[2]);
                    ciudades.get(ciudad).huso=huso;
                }
            }catch(IOException ex){
                Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE,null,ex);
                
            }
            
        }catch(FileNotFoundException ex){
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE,null,ex);
        }
        
    }
    
    public void leerAeropuertos(String archAeropuertos,TreeMap<String,Ciudad> aeropuertos){
        String linea;
        String [] valor;
        String continente="";
        String codAeropuerto, ciudad, pais, abreviado;
        int id;
        
        try{
            BufferedReader bufer= new BufferedReader(new FileReader(archAeropuertos));
            while((linea=bufer.readLine())!=null){
                if(linea.contains("America del Sur")){
                    continente="America del Sur";
                    break;
                }
            }
            while((linea=bufer.readLine())!=null){
                if(linea.contains("Europa")){
                    continente="Europa";
                    continue;
                }
                valor=linea.trim().split("\t");
                if(valor[0].isEmpty()) 
                    continue;
                id=Integer.parseInt(valor[0]);
                codAeropuerto=valor[1];
                ciudad=valor[2];
                pais=valor[3];
                abreviado=valor[4];
                Ciudad aero= new Ciudad(id,codAeropuerto,ciudad,pais,abreviado,continente);
                aeropuertos.put(codAeropuerto,aero);
            }

        }catch(IOException ex){
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE,null,ex);                        
        }
    }
    
    public void leerVuelos(String archVuelos, ArrayList<Vuelo> vuelos){
    }
    
    public void leerPedidos(String archPedidos,ArrayList<Pedido> pedidos){
    }
}