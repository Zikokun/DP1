/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilitario.funcionesInicializarBaseDatos;

/**
 *
 * @author a20111055 -Vivian
 */
public class Lectura {

    public void leerArchivos(String archAeropuertos, String archVuelos, String archPedidos, String archHusos,
            ArrayList<Vuelo> vuelos, TreeMap<String, Ciudad> aeropuertos, ArrayList<Pedido> pedidos) {
        leerAeropuertos(archAeropuertos, aeropuertos);
        leerVuelos(archVuelos, vuelos);
        leerPedidos(archPedidos, pedidos);
        leerHusos(archHusos, aeropuertos);
    }

    public void leerHusos(String archHusos, TreeMap<String, Ciudad> ciudades) {
        String linea;
        String[] valor;

        try {
            BufferedReader bufer = new BufferedReader(new FileReader(archHusos));
            try {
                while ((linea = bufer.readLine()) != null) {
                    valor = linea.trim().split("\t");
                    String ciudad = valor[1];
                    int huso = Integer.parseInt(valor[2]);
                    ciudades.get(ciudad).huso = huso;
                }
            } catch (IOException ex) {
                Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void leerPaquetesArchivos(){
        
    }
    
    public void leerAeropuertosArchivos(String rutaArchivosAeropuertos) throws InstantiationException, IllegalAccessException{
        /*File carpeta = new File(rutaArchivos);
        File[] listaDeArchivos = carpeta.listFiles();
        String nombreCarpeta = "";
        for(int i = 0; i < listaDeArchivos.length; i++){
            if(listaDeArchivos[i].isFile()){
                nombreCarpeta = listaDeArchivos[i].getName();
                System.out.println(nombreCarpeta);
            }
        }*/
        String linea;
        String[] palabras;
        ArrayList<String> listaAlmacenes = new ArrayList<String>();
        
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(rutaArchivosAeropuertos));
            try {
                while ((linea = buffer.readLine()) != null) {
                    palabras = linea.trim().split("-");
                    listaAlmacenes.add(palabras[0]);
                    listaAlmacenes.add(palabras[1]);
                }
                HashSet<String> hashSetAeropuertos = new HashSet<String>(listaAlmacenes);
                listaAlmacenes.clear();
                listaAlmacenes.addAll(hashSetAeropuertos);

                funcionesInicializarBaseDatos fInicializarBaseDatos = new funcionesInicializarBaseDatos();
                fInicializarBaseDatos.insertarAlmacenes(listaAlmacenes);
                
            } catch (IOException ex) {
                Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void leerAeropuertos(String archAeropuertos, TreeMap<String, Ciudad> aeropuertos) {
        String linea;
        String[] valor;
        String continente = "";
        String codAeropuerto, ciudad, pais, abreviado;
        int id;

        try {
            BufferedReader bufer = new BufferedReader(new FileReader(archAeropuertos));
            while ((linea = bufer.readLine()) != null) {
                if (linea.contains("America del Sur")) {
                    continente = "America del Sur";
                    break;
                }
            }
            while ((linea = bufer.readLine()) != null) {
                if (linea.contains("Europa")) {
                    continente = "Europa";
                    continue;
                }
                valor = linea.trim().split("\t");
                if (valor[0].isEmpty()) {
                    continue;
                }
                id = Integer.parseInt(valor[0]);
                codAeropuerto = valor[1];
                ciudad = valor[2];
                pais = valor[3];
                abreviado = valor[4];
                Ciudad aero = new Ciudad(id, codAeropuerto, ciudad, pais, abreviado, continente);
                aeropuertos.put(codAeropuerto, aero);
            }
            bufer.close();

        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leerVuelos(String archVuelos, ArrayList<Vuelo> vuelos) {
        String linea;
        String[] valor;
        String origen, destino, continente = "";
        int hSalida, mSalida, hLlegada, mLlegada;
        try {
            BufferedReader bufer = new BufferedReader(new FileReader(archVuelos));
            while ((linea = bufer.readLine()) != null) {
                valor = linea.trim().split("-|:");
                origen = valor[0];
                destino = valor[1];
                hSalida = Integer.parseInt(valor[2]);
                mSalida = Integer.parseInt(valor[3]);
                hLlegada = Integer.parseInt(valor[4]);
                mLlegada = Integer.parseInt(valor[5]);
                Vuelo vuel = new Vuelo(hSalida, mSalida, hLlegada, mLlegada, origen, destino);
                vuelos.add(vuel);
            }
            bufer.close();
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leerPedidos(String archPedidos, ArrayList<Pedido> pedidos) {
        /*Este archivo de pedidos es UNO solo, no tiene el formato de los ultimos archivos que
         envio el profesor. En este archivo se tiene diferentes pedidos de muchos aeropuertos
         en los archivos del profesor es un archivo de pedidos por aeropuerto, hay que arreglar
         est parte :/
         */

        String linea;
        String[] valor;
        String origen, destino;
        int cant;
        int hora, min;
        int dia, mes, año;

        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        BufferedReader bufer;
        try {
            bufer = new BufferedReader(new FileReader(archPedidos));
            while((linea=bufer.readLine())!= null){
                valor=linea.trim().split("-");
                if(valor[0].isEmpty())
                    continue;
                origen=valor[0];
                destino=valor[1];
                cant=Integer.parseInt(valor[2]);
                String horaMin=valor[3];
                String fecha=valor[4];
                valor=horaMin.trim().split(":");
                hora=Integer.parseInt(valor[0]);
                min=Integer.parseInt(valor[1]);
                valor=fecha.trim().split("/");
                dia=Integer.parseInt(valor[0]);
                mes=Integer.parseInt(valor[1]);
                año=Integer.parseInt(valor[2]);
                Date fech=format.parse(fecha);
                calendario.setTime(fech);
                int diaSem=calendario.get(Calendar.DAY_OF_WEEK);
                Pedido ped = new Pedido(origen,destino,cant,hora,min,dia,mes,año);
                ped.setDiaSemana(diaSem);
                pedidos.add(ped);
            }
            bufer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
