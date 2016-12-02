/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import static constantes.constanteEstadoPaquete.CON_TRES_DIAS_SIN_RUTA;
import static constantes.constantesGenerales.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import utilitario.funcionesBaseDeDatos;
import utilitario.funcionesInicializarBaseDatos;
import utilitario.funcionesPanelSimulacion;

/**
 *
 * @author a20111055 -Vivian
 */
public class Lectura {
    
    
    public void leerVuelosArchivos() throws SQLException, InstantiationException, IllegalAccessException, FileNotFoundException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String sDirectorio = "src/recursos/Archivos3Anhos/";
        File file = new File(sDirectorio);
        
        if (file.exists()) {
            File[] ficheros = file.listFiles();
            for (int i = 0; i < ficheros.length; i++) {
                String nombreArchivo = ficheros[i].getName();
                
                System.out.println(nombreArchivo);
                
                BufferedReader buffer = new BufferedReader(new FileReader(sDirectorio+nombreArchivo));
                
                String aeropuerto = nombreArchivo.substring(nombreArchivo.length() - 8, nombreArchivo.length() - 4);
                String linea; 
                
                try {
                    String sqlBuscarIdOrigen = " SELECT A.idAlmacen FROM almacen A WHERE A.CodCiudad = '" + aeropuerto + "';";
                    Statement st1 = conexion.createStatement();
                    ResultSet resultadoBuscarIdOrigen= st1.executeQuery(sqlBuscarIdOrigen);
                    
                    int idAlmacenOrigen = -1;
                    while (resultadoBuscarIdOrigen != null && resultadoBuscarIdOrigen.next()) {
                        idAlmacenOrigen = resultadoBuscarIdOrigen.getInt(1);
                    }
                            
                    while ((linea = buffer.readLine()) != null) {
                        String idPaquete = linea.substring(0,9);
                        String fechaEnvio = linea.substring(9,17);
                        String hora = linea.substring(17,22);
                        String airPort = linea.substring(22,26);
                        
                        //("yyyy-MM-dd HH:mm:ss");
                        String anho = fechaEnvio.substring(0,4);
                        String mes = fechaEnvio.substring(4,6);
                        String dia = fechaEnvio.substring(6,8);
                        
                        hora = hora + ":00";
                        
                        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String fechaLlegadaAeropuerto = anho + "-" + mes + "-" + dia + " " + hora;
                        
                        Date fecha = null;
                        try {
                            fecha = formatoDelTexto.parse(fechaLlegadaAeropuerto);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        String sqlBuscarIdPaquete = " SELECT A.idAlmacen FROM almacen A WHERE A.CodCiudad = '" + airPort + "';";
                        Statement st = conexion.createStatement();
                        ResultSet resultadoBuscarIdPaquetes = st.executeQuery(sqlBuscarIdPaquete);
                        
                        int idAlmacen = -1;
                        while(resultadoBuscarIdPaquetes!=null && resultadoBuscarIdPaquetes.next()){
                            idAlmacen = resultadoBuscarIdPaquetes.getInt(1);
                        }
                        
                        PreparedStatement sqlGuardarPaquete = conexion.prepareStatement("INSERT INTO paquete VALUES (NULL,?,?,?,?,NULL,NULL,?,-1,NULL,?,?);");
                        sqlGuardarPaquete.setString(1,idPaquete);
                        sqlGuardarPaquete.setInt(2,idAlmacenOrigen);
                        sqlGuardarPaquete.setInt(3,idAlmacen);
                        sqlGuardarPaquete.setObject(4, fechaLlegadaAeropuerto);
                        sqlGuardarPaquete.setInt(5, CON_TRES_DIAS_SIN_RUTA.ordinal());
                        sqlGuardarPaquete.setInt(6, 0);
                        sqlGuardarPaquete.setInt(7, 0);
                        
                        int rows = sqlGuardarPaquete.executeUpdate();
                        //System.out.println(fechaLlegadaAeropuerto + " " + idPaquete + " " + airPort);
                    }
                }catch (IOException ex) {
                    Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("El directorio no existe");
        }
    }
    
    
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

        InputStream inn=Lectura.class.getResourceAsStream(archHusos);
        BufferedReader bufer = new BufferedReader(new InputStreamReader(inn));
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
    }
    
    private List<Pedido> leerPaquetesDeAeropuerto(String rutaArchivosPaquetes){
        String linea;
        List<Pedido> lstPedido = new ArrayList<Pedido>();
        InputStream inn=Lectura.class.getResourceAsStream(rutaArchivosPaquetes);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inn));
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
        return lstPedido;
    }
    
    public void leerVuelosArchivos(String rutaArchivosVuelos) throws InstantiationException, IllegalAccessException{
        String linea;
        String[] palabras;
        ArrayList<String[]> lstPlanesDeVuelo = new ArrayList<String[]>();
        
        InputStream inn=Lectura.class.getResourceAsStream(rutaArchivosVuelos);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inn));
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
        
        InputStream inn=Lectura.class.getResourceAsStream(rutaArchivosAeropuertos);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(inn));
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
    }
    
    public void leerAeropuertos(String archAeropuertos, TreeMap<String, Ciudad> aeropuertos) {
        String linea;
        String[] valor;
        String continente = "";
        String codAeropuerto, ciudad, pais, abreviado;
        int id;

        try {
            InputStream inn=Lectura.class.getResourceAsStream(archAeropuertos);
            BufferedReader bufer = new BufferedReader(new InputStreamReader(inn));
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
            InputStream inn=Lectura.class.getResourceAsStream(archVuelos);
            BufferedReader bufer = new BufferedReader(new InputStreamReader(inn));
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
            InputStream inn=Lectura.class.getResourceAsStream(archPedidos);
            bufer = new BufferedReader(new InputStreamReader(inn));
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
            InputStream inn=Lectura.class.getResourceAsStream("src/recursos/_aeropuertos.OACI.txt");
            BufferedReader bufer = new BufferedReader(new InputStreamReader(inn));
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
        InputStream inn=Lectura.class.getResourceAsStream(archPedidos);
        BufferedReader bufer;
        try {
            bufer = new BufferedReader(new InputStreamReader(inn));
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
    public static double[] leerPedidoxAeropuerto_full(String archPedidos){
        String linea;
        String[] valor;
        String[] valor2;
        String[] valor3;
        String nombreArchPed;
        String origen, destino;
        int cant;
        int hora, min;
        int dia, mes, año;
        double[] intArr={0,0,0,0,0,0,0,0,0,0,0,0};
        double[] intArrD=new double[312];
        for(int i=0;i<312;i++)
            intArrD[i]=0.0;
        InputStream inn=Lectura.class.getResourceAsStream(archPedidos);
        BufferedReader bufer,bufer3;
        try {
            bufer = new BufferedReader(new InputStreamReader(inn));
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
        /////////////////////////////////////////////////////
        String aux[]=archPedidos.split("_");
        nombreArchPed="src/recursos/dest_"+aux[1];
        /////////////////////////////////////////////////////
        BufferedReader bufer2;
        try {
            InputStream inn2=Lectura.class.getResourceAsStream(archPedidos);
            bufer2 = new BufferedReader(new InputStreamReader(inn2));
            while((linea=bufer2.readLine())!= null){
           
                valor2=linea.trim().split(":");
                if(valor2[0].isEmpty())
                    continue;
                mes=Integer.parseInt(valor2[0].substring(13, 15));
                //dia=Integer.parseInt(valor[0].substring(15, 17));
                //System.out.println(mes);
               // System.out.println(dia);
                //int pos=(dia-1)+((mes-1)*26);
                //intArrD[pos]+=1;
                intArr[mes-1]+=1;
                
            }
            bufer2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ////////////////////////////////////////////////////////////////
//        String archPaquetes="src/recursos/paquetes_vuelos.txt";
//        ////////////////////////////////////////////////////////////////
//        try {
//            bufer3 = new BufferedReader(new FileReader(archPaquetes));
//            while((linea=bufer3.readLine())!= null){
//           
//                valor3=linea.trim().split("-");
//                if(valor3[0].isEmpty())
//                    continue;
//                if(aux[1].equals(valor3[2])){
//                    mes=Integer.parseInt(valor3[1].substring(4, 6));
//                    //dia=Integer.parseInt(valor3[1].substring(6, 8));
//                    //System.out.println(mes);
//                   // System.out.println(dia);
//                    intArr[mes-1]+=1;
//                }
//            }
//            bufer3.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
            InputStream inn=Lectura.class.getResourceAsStream(archPedidos);
            bufer = new BufferedReader(new InputStreamReader(inn));
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
     public static double[] leerPedidoxAeropuertoD_full(String archPedidos){
        String linea;
        String[] valor;
        String[] valor2;
        String[] valor3;
        String nombreArchPed;
        int cant;
        int hora, min;
        int dia, mes, año;
        double[] intArr={0,0,0,0,0,0,0,0,0,0,0,0};
        double[] intArrD=new double[312];
        for(int i=0;i<312;i++)
            intArrD[i]=0.0;
        
        BufferedReader bufer,bufer2,bufer3;
        try {
            InputStream inn=Lectura.class.getResourceAsStream(archPedidos);
            bufer = new BufferedReader(new InputStreamReader(inn));
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
        /////cambiar el nombre para acceder a otro archivo de donde se sacaran datos////
        String aux[]=archPedidos.split("_");
        nombreArchPed="/recursos/dest_"+aux[1];
        
        ////////////
        try {
            InputStream inn2=Lectura.class.getResourceAsStream(nombreArchPed);
            bufer2 = new BufferedReader(new InputStreamReader(inn2));
            while((linea=bufer2.readLine())!= null){
           
                valor2=linea.trim().split(":");
                if(valor2[0].isEmpty())
                    continue;
                mes=Integer.parseInt(valor2[0].substring(13, 15));
                dia=Integer.parseInt(valor2[0].substring(15, 17));
                //System.out.println(mes);
               // System.out.println(dia);
                int pos=(dia-1)+((mes-1)*26);
                intArrD[pos]+=1;
                
            }
            bufer2.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ////////////////////////////////////////////////////////////////
//        String archPaquetes="src/recursos/paquetes_vuelos.txt";
//        ////////////////////////////////////////////////////////////////
//        try {
//            bufer3 = new BufferedReader(new FileReader(archPaquetes));
//            while((linea=bufer3.readLine())!= null){
//           
//                valor3=linea.trim().split("-");
//                if(valor3[0].isEmpty())
//                    continue;
//                if(aux[1].equals(valor3[2])){
//                    mes=Integer.parseInt(valor3[1].substring(4, 6));
//                    dia=Integer.parseInt(valor3[1].substring(6, 8));
//                    //System.out.println(mes);
//                   // System.out.println(dia);
//                    int pos=(dia-1)+((mes-1)*26);
//                    intArrD[pos]+=1;
//                }
//            }
//            bufer3.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return intArrD;
    }
}
