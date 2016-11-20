/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static constantes.constantesGenerales.*;
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
import utilitario.funcionesPanelSimulacion;

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

    public void leerSinPedidosYVuelos(String archAeropuertos, String archHusos, TreeMap<String, Ciudad> aeropuertos){
        leerAeropuertos(archAeropuertos, aeropuertos);
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
    
    private List<Pedido> leerPaquetesDeAeropuerto(String rutaArchivosPaquetes){
        String linea;
        List<Pedido> lstPedido = new ArrayList<Pedido>();
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(rutaArchivosPaquetes));
            try {
                while ((linea = buffer.readLine()) != null) {
                    String origen = rutaArchivosPaquetes.substring((rutaArchivosPaquetes.length() - 8 ),(rutaArchivosPaquetes.length() - 4 + 1));
                    String codigoPaquete = linea.substring(0, 9);
                    int anho = Integer.parseInt(linea.substring(9,13));
                    int mes = Integer.parseInt(linea.substring(13,15));
                    int dia = Integer.parseInt(linea.substring(15,17));
                    int hora = Integer.parseInt(linea.substring(17,19));
                    int min = Integer.parseInt(linea.substring(20,22));
                    String destino = linea.substring(22,26);
                    
                    Pedido pedido = new Pedido();
                    pedido.setAño(anho);
                    pedido.setDestino(destino);
                    pedido.setDia(dia);
                    pedido.setHora(hora);
                    pedido.setMin(min);
                    pedido.setOrigen(origen);
                    pedido.setNumeroRastreo(codigoPaquete);
                    
                    lstPedido.add(pedido);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPedido;
    }
    
    public void leerPaquetesArchivos(String rutaArchivosPaquetesDirectorio,List<Pedido>  lstPedido){
        
        File carpeta = new File(rutaArchivosPaquetesDirectorio);
        File[] listaDeArchivos = carpeta.listFiles();
        String nombreCarpeta = "";
        
        for(int i = 0; i < listaDeArchivos.length; i++){
            if(listaDeArchivos[i].isFile()){
                nombreCarpeta = listaDeArchivos[i].getName();
                System.out.println(nombreCarpeta);
                String nombreArchivo = nombreCarpeta.substring(0,4);
                if(nombreArchivo.equals(NOMBRE_ARCH)){
                    lstPedido.addAll(leerPaquetesDeAeropuerto(rutaArchivosPaquetesDirectorio + "/" + nombreCarpeta));
                }
            }
        }
        System.out.println("La longitud fue de " + lstPedido.size());
    }
    
    public void leerVuelosArchivos(String rutaArchivosVuelos) throws InstantiationException, IllegalAccessException{
        String linea;
        String[] palabras;
        ArrayList<String[]> lstPlanesDeVuelo = new ArrayList<String[]>();
        
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(rutaArchivosVuelos));
            try {
                while ((linea = buffer.readLine()) != null) {
                    palabras = linea.trim().split("-");
                    lstPlanesDeVuelo.add(palabras);
                }
                
                funcionesInicializarBaseDatos fInicializarBaseDatos = new funcionesInicializarBaseDatos();
                fInicializarBaseDatos.insertarVuelos(lstPlanesDeVuelo);
                
            } catch (IOException ex) {
                Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        public static String[] leeCodAero(){
        String linea;
        
        String[] aeropuertos=new String[40];
        String[] valor;
        String continente = "";
        String codAeropuerto;
        int cont=0;
        
        try {
            BufferedReader bufer = new BufferedReader(new FileReader("src/recursos/_aeropuertos.OACI.txt"));
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
                codAeropuerto = valor[1];
                
                aeropuertos[cont]=codAeropuerto;
                cont++;
            }
            bufer.close();

        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i=0;
        
       
        return aeropuertos;
    }
            public static void leerArchPedidos(){
        String linea;
        String[] valor;
        String origen, destino;
        int cant;
        int hora, min;
        int dia, mes, año;
         BufferedReader bufer;
         String[] aeropuertos = null;
         double[][] arrCoefReg = null;
         funcionesPanelSimulacion func= new funcionesPanelSimulacion();
         //lee aeropuertos
            aeropuertos=leeCodAero();
         //
          
         for(int i=0;i<aeropuertos.length;i++){
            String nombre="src/recursos/arch_";
            nombre=nombre.concat(aeropuertos[i]);
            nombre=nombre.concat(".txt");
            func.calculaRegExp(nombre);
            //arrCoefReg[i]=leerPedidoxAeropuertoD(nombre);
            
           
        }
    }
    public static double[] leerPedidoxAeropuerto(String archPedidos){
        String linea;
        String[] valor;
        String origen, destino;
        int cant;
        int hora, min;
        int dia, mes, año;
        double[] intArr={0,0,0,0,0,0,0,0,0,0,0,0};
        double[] intArrD=new double[312];
        for(int i=0;i<312;i++)
            intArrD[i]=0.0;
        
        BufferedReader bufer;
        try {
            bufer = new BufferedReader(new FileReader(archPedidos));
            while((linea=bufer.readLine())!= null){
           
                valor=linea.trim().split(":");
                if(valor[0].isEmpty())
                    continue;
                mes=Integer.parseInt(valor[0].substring(13, 15));
                //dia=Integer.parseInt(valor[0].substring(15, 17));
                //System.out.println(mes);
               // System.out.println(dia);
                //int pos=(dia-1)+((mes-1)*26);
                //intArrD[pos]+=1;
                intArr[mes-1]+=1;
                
            }
            bufer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return intArr;
    }
    public static double[] leerPedidoxAeropuertoD(String archPedidos){
        String linea;
        String[] valor;
        String origen, destino;
        int cant;
        int hora, min;
        int dia, mes, año;
        double[] intArr={0,0,0,0,0,0,0,0,0,0,0,0};
        double[] intArrD=new double[312];
        for(int i=0;i<312;i++)
            intArrD[i]=0.0;
        
        BufferedReader bufer;
        try {
            bufer = new BufferedReader(new FileReader(archPedidos));
            while((linea=bufer.readLine())!= null){
           
                valor=linea.trim().split(":");
                if(valor[0].isEmpty())
                    continue;
                mes=Integer.parseInt(valor[0].substring(13, 15));
                dia=Integer.parseInt(valor[0].substring(15, 17));
                //System.out.println(mes);
               // System.out.println(dia);
                int pos=(dia-1)+((mes-1)*26);
                intArrD[pos]+=1;
                
            }
            bufer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return intArrD;
    }
}
