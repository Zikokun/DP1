/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constantesVentanaPrincipal.TIPO_OPERARIO;
import controlador.Genetico;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Ciudad;
import modelo.Cliente;
import modelo.Cromosoma;
import modelo.Gen;
import modelo.Lectura;
import modelo.Paquete;
import modelo.Pedido;
import modelo.Persona;
import modelo.Ruta;
import modelo.Vuelo;
import static test1_gui_alg.Test1_gui_alg.asignarTipoVuelo;
import static test1_gui_alg.Test1_gui_alg.generarRutas;

/**
 *
 * @author Vivian
 */
public class funcionesRuteo {
    int primeraVez=1;
    public Cromosoma ruteoPedidosManual() throws InstantiationException, IllegalAccessException, IOException{
        String mensaje = "";
        int hora,dia;
        Lectura lector= new Lectura();
        TreeMap<String,Ciudad> ciudades=new TreeMap<>();//MAP Key-Codigo Ciudad y VALUE Objeto Ciudad
        ArrayList<Vuelo> vuelos=new ArrayList<>();
        ArrayList<Pedido> pedidos=devolverPedidosTotal();
        
        if(primeraVez==1){
            lector.leerSinPedidos("src/recursos/_aeropuertos.OACI.txt", "src/recursos/_plan_vuelo.txt",
                "src/recursos/_husos_horarios.txt", vuelos, ciudades);
            asignarTipoVuelo(vuelos,ciudades);
            generarRutas(ciudades);            
            primeraVez=0;
        }
        
        Calendar calendario=Calendar.getInstance();
        hora=calendario.get(Calendar.HOUR_OF_DAY);
        dia=calendario.get(Calendar.DAY_OF_WEEK);
        
        Genetico algoritmo=new Genetico();
        algoritmo.ejecutar(ciudades, vuelos, pedidos, hora, dia, mensaje);
        Cromosoma solucion=algoritmo.getMejorCrom();
        //System.out.println("HOla");
        ArrayList<Gen> genes=solucion.genes;
        for(Gen item: genes){
            item.getRuta().print();
            System.out.println("Tiempo Total: "+item.tiempo/60+" horas");
        }
        return solucion;
    }
    
    public ArrayList<Pedido> devolverPedidosTotal()throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
            
        String sqlBuscarPaquetes = "";
        ArrayList<Pedido> lstPaquetes = new ArrayList<>();
            
        sqlBuscarPaquetes = " SELECT A.codCiudad,B.codCiudad, P.fechaRecepcion\n" +
                                "FROM paquete P, almacen A, almacen B\n" +
                                    "WHERE P.idLugarOrigen = A.idAlmacen AND P.idLugarDestino = B.idAlmacen;";
        
        try {   
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarPaquetes = st.executeQuery(sqlBuscarPaquetes);
            
            while(resultadoBuscarPaquetes!=null && resultadoBuscarPaquetes.next()){

                String origen = resultadoBuscarPaquetes.getString(1);
                String destino = resultadoBuscarPaquetes.getString(2);
                Date fecha = (Date) resultadoBuscarPaquetes.getObject(3);
                Calendar cal = Calendar.getInstance();
                cal.setTime(fecha);  
                int hora = cal.get(Calendar.HOUR_OF_DAY);
                int min=cal.get(Calendar.MINUTE);
                int dia=cal.get(Calendar.DAY_OF_MONTH);
                int mes=cal.get(Calendar.MONTH);
                int año=cal.get(Calendar.YEAR);
                int diaSem=cal.get(Calendar.DAY_OF_WEEK);
                Pedido ped = new Pedido(origen,destino,1,hora,min,dia,mes,año);
                ped.setDiaSemana(diaSem);
                lstPaquetes.add(ped);
                
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelPaqueteBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPaquetes;
    }
    
    public static void generarRutas(TreeMap<String,Ciudad> ciudades) throws FileNotFoundException, IOException{
        int tEspera;
        int tiempoRuta;
//        File fRutas = new File("Rutas.txt");
//	FileOutputStream fOut = new FileOutputStream(fRutas);
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOut));
//        bw.write("Todas las rutas");bw.newLine();
//        System.out.println("Cant ciudades: "+ciudades.size());
        for(Ciudad ciudad: ciudades.values()){
            ArrayList<Vuelo>vuelos=ciudad.vuelos;
            int cantVuelos=vuelos.size();
            for(int i=0;i<cantVuelos;i++){
                String destino=vuelos.get(i).getDestino();
                if(!ciudad.rutas.containsKey(destino)){ // si todavia no tiene ninguna ruta a ese destino
                   ArrayList<Ruta> rutas=new ArrayList<>();
                   rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                   ciudad.rutas.put(destino, rutas);
                    //bw.write(ciudad.getCodAeropuerto()+"-"+destino);
                    //bw.newLine();
                }
                else{
                    ArrayList<Ruta> rutas=ciudad.rutas.get(destino);
                    rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                    ciudad.rutas.put(destino, rutas);
                     //bw.write(ciudad.getCodAeropuerto()+"-"+destino);
                     //bw.newLine();
                }
                //caso con escala
                Ciudad ciudadIntermedia=vuelos.get(i).getAeroFin();
                for(int j=0;j<ciudadIntermedia.vuelos.size();j++){
                    Vuelo vuelo2=ciudadIntermedia.vuelos.get(j);
                    String destino2=vuelo2.getDestino();
                    tEspera=vuelo2.gethSalida()-vuelos.get(i).gethLlegada();
                    int tMax=24;
                    if(!ciudad.getContinente().equals(ciudades.get(destino2).getContinente())) tMax=48;
                    if(tEspera<0) tEspera+=24;
                    tiempoRuta=vuelos.get(i).getTiempo()+vuelo2.getTiempo()+tEspera;
                    //if (ciudad.getContinente().equals(ciudades.get(destino2).getContinente())) continue;
                    //if(tiempoRuta>48) continue;
                    if(tiempoRuta>tMax) continue;
//                    if (ciudad.getContinente().equals(ciudades.get(destino2).getContinente())&& tiempoRuta>24) continue;                    
//                    if(!ciudad.getContinente().equals(ciudades.get(destino2).getContinente())&& tiempoRuta>48) continue; // si se demora más de 48 horas, no tomar en cuenta
                    if(!ciudad.rutas.containsKey(destino2)){ // si todavia no tiene ninguna ruta ese destino
                        ArrayList<Ruta> rutas= new ArrayList<>();
                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
                        rutas.add(ruta);
                        ciudad.rutas.put(destino2, rutas);
                        //bw.write(ciudad.getCodAeropuerto()+"-"+destino+"-"+destino2);
                        //bw.newLine();
                    }
                    else{
                        ArrayList<Ruta> rutas=ciudad.rutas.get(destino2);
                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
                        rutas.add(ruta);
                        ciudad.rutas.put(destino2,rutas);
                        //bw.write(ciudad.getCodAeropuerto()+"-"+destino+"-"+destino2);
                        //bw.newLine();
                    }
                    //System.out.println(tiempoRuta);
                    
                }
            }
        }  
        //bw.close();
    }
    
    public static void asignarTipoVuelo(ArrayList<Vuelo> vuelos, TreeMap<String, Ciudad> ciudades){
        int nVuelos=vuelos.size();
        int nAeros=ciudades.size();
        int nOrig=0, nFin=0;
        for(int i=0; i<nVuelos;i++){
            Vuelo vueloActual=vuelos.get(i);
            String origen=vueloActual.getOrigen();
            String destino=vueloActual.getDestino();
            if(ciudades.get(origen).getContinente().equals(ciudades.get(destino).getContinente())){
                vuelos.get(i).setTipoVuelo("Continental");
                vuelos.get(i).setTiempo(12);
            }
            else{
                vuelos.get(i).setTipoVuelo("Intercontinental");
                vuelos.get(i).setTiempo(24);
            }
            if(!ciudades.get(origen).vecinos.contains(destino)) 
                ciudades.get(origen).vecinos.add(destino);//agrego vecinos
            ciudades.get(origen).vuelos.add(vuelos.get(i));// agrego vuelos del aeropuerto
            vuelos.get(i).setAeroOrig(ciudades.get(origen));//agrego aeropuerto origen
            vuelos.get(i).setAeroFin(ciudades.get(destino)); // agrego aeropuerto fin 
        }
    }
    
    public static void imprimirAeros(TreeMap<String,Ciudad> aeropuertos){
        for(Ciudad item:aeropuertos.values())
            System.out.println(item.getCiudad());
    }
    
}
