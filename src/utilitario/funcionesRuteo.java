/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constanteEstadoPaquete;
import static constantes.constanteEstadoPaquete.*;
import static constantes.constantesVentanaPrincipal.TIPO_OPERARIO;
import controlador.Genetico;
import static constantes.constantesGenerales.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
    ArrayList<Vuelo> vuelos=new ArrayList<>();
    TreeMap<String,Ciudad> ciudades=new TreeMap<>();//MAP Key-Codigo Ciudad y VALUE Objeto Ciudad
    Correo corr = new Correo();
    
    public Cromosoma ruteoPedidosManual(Integer estadoPedido, Integer estadoFinal) throws InstantiationException, IllegalAccessException, IOException, SQLException{
        String mensaje = "";
        int hora,dia;
        Lectura lector= new Lectura();
        //TreeMap<String,Ciudad> ciudades=new TreeMap<>();//MAP Key-Codigo Ciudad y VALUE Objeto Ciudad
        //ArrayList<Vuelo> vuelos=new ArrayList<>();
        
        System.out.println(ANSI_GREEN+"Estado inicial: "+ constanteEstadoPaquete.values()[estadoPedido]+ANSI_RESET);
        System.out.println(ANSI_GREEN+"Estado final: "+ constanteEstadoPaquete.values()[estadoFinal]+ANSI_RESET);
        
        ArrayList<Pedido> pedidos=devolverPedidosTotal(estadoPedido);
        
        if(primeraVez==1){
            lector.leerSinPedidosYVuelos("/recursos/_aeropuertos.OACI.txt", 
                "/recursos/_husos_horarios.txt", ciudades);
            vuelos=devolverVuelosTotal();
            asignarTipoVuelo(vuelos,ciudades);
            generarRutas(ciudades);            
            primeraVez=0;
        }
        
        Calendar calendario=Calendar.getInstance();
        hora=calendario.get(Calendar.HOUR_OF_DAY);
        dia=calendario.get(Calendar.DAY_OF_WEEK);
        Cromosoma solucion=new Cromosoma();
        if(pedidos.size()!=0){
            Genetico algoritmo=new Genetico();
            algoritmo.ejecutar(ciudades, vuelos, pedidos, hora, dia, mensaje);
            solucion=algoritmo.getMejorCrom();
            asignarRutasBD(solucion,estadoFinal);
            ArrayList<Gen> genes=solucion.genes;
            for(Gen item: genes){
                
                item.getRuta().print();
                System.out.println("Tiempo Total: "+item.tiempo/60+" horas");
            }
        }
        else System.out.println("No hay paquetes para enrutar");
        return solucion;
    }
    
    public Cromosoma ruteoPedidosTresDias(Integer estadoPedido, Integer estadoFinal, Calendar fechaConsulta) throws InstantiationException, IllegalAccessException, IOException, SQLException{
        //fechaConsulta es la fecha a partir de la cual se van a seleccionar los pedidos para enrutar
        String mensaje = "";
        int hora,dia;
        Lectura lector= new Lectura();
        
        System.out.println("Estado inicial: "+ constanteEstadoPaquete.values()[estadoPedido]);
        System.out.println("Estado final: "+ constanteEstadoPaquete.values()[estadoFinal]);
        
        ArrayList<Pedido> pedidos=devolverPedidosPorHora(estadoPedido, fechaConsulta);
        
        if(primeraVez==1){
            lector.leerSinPedidosYVuelos("/recursos/_aeropuertos.OACI.txt", 
                "/recursos/_husos_horarios.txt", ciudades);
            vuelos=devolverVuelosTotal();
            asignarTipoVuelo(vuelos,ciudades);
            generarRutas(ciudades);            
            primeraVez=0;
        }
        
        Calendar calendario=Calendar.getInstance();
        hora=calendario.get(Calendar.HOUR_OF_DAY);
        dia=calendario.get(Calendar.DAY_OF_WEEK);
        Cromosoma solucion=new Cromosoma();
        if(pedidos.size()!=0){
            Genetico algoritmo=new Genetico();
            algoritmo.ejecutar(ciudades, vuelos, pedidos, hora, dia, mensaje);
            solucion=algoritmo.getMejorCrom();
            asignarRutasBD(solucion,estadoFinal);
            ArrayList<Gen> genes=solucion.genes;
            for(Gen item: genes){                
                item.getRuta().print();
                System.out.println("Tiempo Total: "+item.tiempo/60+" horas");
            }
            //imprimirCaps();
        }
        else System.out.println("No hay paquetes para enrutar");
        return solucion;
    }
    
    public ArrayList<Pedido> devolverPedidosPorHora(Integer estadoPedido, Calendar fechaConsulta) throws InstantiationException, IllegalAccessException, SQLException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
            
        String sqlBuscarPaquetes = "";
        ArrayList<Pedido> lstPaquetes = new ArrayList<>();
        
        //Seteamos el rango de fechas de recepcion para la consulta
        SimpleDateFormat formateador=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaInicial = formateador.format(fechaConsulta.getTime());
        //el lapso de tiempo es de una hora
        Calendar fechaActualizada = Calendar.getInstance();
        fechaActualizada.setTime(fechaConsulta.getTime());
        
        fechaActualizada.add(Calendar.MINUTE, 59);
        fechaActualizada.add(Calendar.SECOND,59);
        String fechaFinal = formateador.format(fechaActualizada.getTime());
            
        sqlBuscarPaquetes = "SELECT A.codCiudad,B.codCiudad, P.fechaRecepcion, P.idPaquete\n" +
                            "FROM paquete P, almacen A, almacen B\n" +
                            "WHERE P.idLugarOrigen = A.idAlmacen AND P.idLugarDestino = B.idAlmacen AND estado= "+estadoPedido+"\n" +
                            "AND P.fechaRecepcion BETWEEN '"+fechaInicial+"' AND '"+fechaFinal+"'\n" +
                            "ORDER BY P.fechaRecepcion;";
        
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
                ped.setIdPedido(resultadoBuscarPaquetes.getInt(4));
                lstPaquetes.add(ped);
                
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelPaqueteBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.close();
        return lstPaquetes;
    }
    
    public void imprimirCaps(){
        
    }
    
    public void asignarRutasBD(Cromosoma solucion,int estadoFinal) throws InstantiationException, IllegalAccessException, SQLException{
        ArrayList<Gen> genes=solucion.genes;
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        PreparedStatement sqlCrearRuta; 
        //Statement st = conexion.createStatement();
        ArrayList<Date> horasSalidas=new ArrayList<>();
        ArrayList<Date> horasLlegadas=new ArrayList<>();
        for(Gen item: genes){
            ArrayList<Vuelo> vuelos=item.getRuta().getVuelos();
            int cantVuelos=vuelos.size();
            int codPed=item.pedido.getIdPedido();  
            String fechaLlegada="";
            calcularFechas(item,horasSalidas,horasLlegadas);
            for(int i=0;i<cantVuelos;i++){
                sqlCrearRuta=conexion.prepareStatement("INSERT INTO avion_has_paquete VALUES (?,?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
                System.out.println("Salida: "+horasSalidas.get(i));  
                System.out.println("Llegada: "+horasLlegadas.get(i));                
                sqlCrearRuta.setInt(1,vuelos.get(i).getIdVuelo());
                sqlCrearRuta.setInt(2,codPed);
                Timestamp fechaL = new Timestamp(horasLlegadas.get(i).getTime());
                sqlCrearRuta.setTimestamp(3, fechaL);
                Timestamp fechaS = new Timestamp(horasSalidas.get(i).getTime());
                fechaLlegada = fechaS.toString();
                sqlCrearRuta.setTimestamp(4, fechaS);
                if(i==0) sqlCrearRuta.setInt(5,0);
                else sqlCrearRuta.setInt(5,1);
                sqlCrearRuta.executeUpdate();
            }
            //Cambio estado actual del paquete
            PreparedStatement sqlActualizarEstado = conexion.prepareStatement(" UPDATE `paquete` SET `estado`='" + estadoFinal +"' WHERE `idPaquete`='"+ codPed +"'; ");
            sqlActualizarEstado.executeUpdate();
            //Genera correo de asignacion de ruta
//            if(estadoPedido==0){
//                 enviarCorreo(item.getPedido(),item.getRuta(),item.getTiempo());
//            }
        }
    }
    public String buscaPersona(String numRas) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        String cadena="";
        String sqlBuscarCorreo = "";
        String numeroRastreo = " AND B.numeroRastreo = '" + numRas + "' ";
        sqlBuscarCorreo = " SELECT P.CorreoElectronico, " +
                " FROM persona P, paquete B " +
                " WHERE P.idPersona = B.Persona_idPersona  " + numeroRastreo + ";";
        try{
        Statement st = conexion.createStatement();
        ResultSet resultadoBuscarCorreo = st.executeQuery(sqlBuscarCorreo);
        while(resultadoBuscarCorreo!=null && resultadoBuscarCorreo.next()){
                cadena = "Usuario " + resultadoBuscarCorreo.getString("CorreoElectronico");
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelPaqueteBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadena;
    }
    public void enviarCorreo(Pedido itemP,Ruta itemR,int tiempo) {
        try {
            corr.setContrasenha("dfcljwktcrnxqulr");
            corr.setUsuarioCorreo("traslapack.packsis@gmail.com");
            corr.setAsunto("Enrutamiento de paquete N°"+itemP.getNumeroRastreo());
            corr.setMensaje("Se registro la siguiente ruta: " + itemR.printS() + ".");
            String numR=itemP.getNumeroRastreo();
            String correoUsr=buscaPersona(numR);
            corr.setDestino(correoUsr);
            corr.setNombArch("logo.png");
            corr.setRutaArch("src/recursos/logo.png");
            controladorCorreo corrCor = new controladorCorreo();
            corrCor.enviarCorreo(corr);
        } catch (InstantiationException ex) {
            Logger.getLogger(funcionesRuteo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(funcionesRuteo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void calcularFechas(Gen gen, ArrayList<Date> horasSalidas, ArrayList<Date> horasLlegadas){
        Pedido pedido = gen.pedido;
        ArrayList<Vuelo> vuelos=gen.getRuta().getVuelos();
        Calendar fechaRecepcion= Calendar.getInstance();
        fechaRecepcion.set(pedido.getAño(), pedido.getMes(), pedido.getDia(), pedido.getHora(), pedido.getMin());
        System.out.println("Fecha Recepcion: "+fechaRecepcion.getTime());
        int cantVuelos=vuelos.size();
        int horaP=pedido.getHora();
        int minP=pedido.getMin(); 
        for (int i=0;i<cantVuelos;i++){
            Vuelo vuelo=vuelos.get(i);
            int hSalida=vuelo.gethSalida(); 
            int minutosEspera;
            int husoO=vuelo.getAeroOrig().huso;
            int husoF=vuelo.getAeroFin().huso;
            if(hSalida<horaP ||(hSalida==horaP && minP!=0))fechaRecepcion.add(Calendar.DAY_OF_MONTH, 1);//sale al dia siguiente    
            // seteamos hora salida del origen
            fechaRecepcion.set(Calendar.HOUR_OF_DAY, hSalida);
            fechaRecepcion.set(Calendar.MINUTE, 0);
            fechaRecepcion.set(Calendar.SECOND, 0);
            //se agrega a la lista de horas de salida
            horasSalidas.add(i,fechaRecepcion.getTime());
            //setemos la hora de llegada al destino
            fechaRecepcion.add(Calendar.HOUR_OF_DAY, vuelo.getTiempo()+husoF-husoO);
            horasLlegadas.add(i,fechaRecepcion.getTime());
            horaP=fechaRecepcion.get(Calendar.HOUR_OF_DAY);
            minP=fechaRecepcion.get(Calendar.MINUTE);
        }
    }
    
    public ArrayList<Vuelo> devolverVuelosTotal() throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
            
        String sqlBuscarVuelos = "";
        ArrayList<Vuelo> lstVuelos = new ArrayList<>();
        
        sqlBuscarVuelos="SELECT V.idVuelo, A.codCiudad, B.codCiudad, V.horaSalida, V.horaLlegada\n" +
                        "FROM vuelo V, almacen A, almacen B\n" +
                        "WHERE  V.idLugarOrigen=A.idAlmacen AND V.idLugarDestino=B.idAlmacen\n" +
                        "ORDER BY V.idVuelo;";
        Statement st;
        try {
            st = conexion.createStatement();
            ResultSet resultadoBuscarVuelos = st.executeQuery(sqlBuscarVuelos);
            while(resultadoBuscarVuelos!=null && resultadoBuscarVuelos.next()){
                //System.out.println("Id Vuelo BD: "+resultadoBuscarVuelos.getInt(1));
                int codVuelo=resultadoBuscarVuelos.getInt(1);
                String origen = resultadoBuscarVuelos.getString(2);
                String destino = resultadoBuscarVuelos.getString(3);
                Time horaSalida = (Time) resultadoBuscarVuelos.getObject(4);
                Calendar cal = Calendar.getInstance();
                cal.setTime(horaSalida);  
                int horaS = cal.get(Calendar.HOUR_OF_DAY);
                int minS=cal.get(Calendar.MINUTE);
                Time horaLlegada=(Time) resultadoBuscarVuelos.getObject(5);
                cal.setTime(horaLlegada);
                int horaL = cal.get(Calendar.HOUR_OF_DAY);
                int minL=cal.get(Calendar.MINUTE);
                Vuelo vuel = new Vuelo(horaS, minS, horaL, minL, origen, destino);
                vuel.setIdVuelo(codVuelo);
                lstVuelos.add(vuel);
                //System.out.println(codVuelo+"-"+origen+"-"+destino+"-"+horaS+":"+minS+"-"+horaL+":"+minL);
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesRuteo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return lstVuelos;
    }
    
    public ArrayList<Pedido> devolverPedidosTotal(Integer estadoPedido)throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
            
        String sqlBuscarPaquetes = "";
        ArrayList<Pedido> lstPaquetes = new ArrayList<>();
            
        sqlBuscarPaquetes = "SELECT A.codCiudad,B.codCiudad, P.fechaRecepcion, P.idPaquete\n" +
                            "FROM paquete P, almacen A, almacen B\n" +
                            "WHERE P.idLugarOrigen = A.idAlmacen AND P.idLugarDestino = B.idAlmacen AND estado= "+estadoPedido+ " ORDER BY P.fechaRecepcion;";
        
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
                ped.setIdPedido(resultadoBuscarPaquetes.getInt(4));
                lstPaquetes.add(ped);
                
                        
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelPaqueteBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lstPaquetes;
    }
    
    private static ArrayList<Ruta> limpiarRutas(ArrayList<Ruta> rutas, Ciudad ciudFin) {
        ArrayList<Ruta> listadoRutas = new ArrayList<Ruta>();
        int tamanho = rutas.size();
        for (int i = 0; i < tamanho; i++) {
            Ruta rutaAnalizar = rutas.get(i);
            //if(rutaAnalizar.getVuelos().size()==1) continue;
            int tam=rutaAnalizar.getVuelos().size();
            ArrayList<Vuelo> lstVuelos = rutaAnalizar.getVuelos();
            
            boolean esPosible = true;
            for(int j = 0; j < tam; j++){
                Vuelo vuelo = lstVuelos.get(j);
                if(vuelo.getOrigen().equals(ciudFin.getCodAeropuerto())){
                    esPosible = false;
                    break;
                }
            }
            
            if(esPosible) listadoRutas.add(rutaAnalizar);
        }
        return listadoRutas;
    }
    
    public static void generarRutas(TreeMap<String,Ciudad> ciudades){
        int tEspera;
        int tiempoRuta;
        int i=0,j=0;
        Collection<Ciudad> listaCiudades=ciudades.values();
        for(Ciudad ciudad : listaCiudades) {
            //System.out.println("Ciudad "+i+": "+ciudad.getCiudad()); i++;
            j=0;
            for(Ciudad ciudFin: listaCiudades){
                if (ciudFin==ciudad) continue;
                //System.out.println("  a Ciudad"+j+": "+ciudFin.getCiudad());
                int tMax=48; //maximo de horas
                if(ciudFin.getContinente().equals(ciudad.getContinente())) tMax=24;
                ArrayList<Ruta> rutas=encuentraRutas(ciudad,ciudFin.getCodAeropuerto(),tMax,0);
                if (rutas.size() > 0) {
                    ArrayList<Ruta> rutaLimpia = limpiarRutas(rutas,ciudFin);
                    ciudad.rutas.put(ciudFin.getCodAeropuerto(), rutaLimpia);
                }
                j++;
            }
        }        
    } 
    
    public static ArrayList<Ruta> encuentraRutas(Ciudad ciudOrigen, String ciudFinal,int tiempoDisp,int nivel){
        ArrayList<Ruta> rutas= new ArrayList<>();
        if(tiempoDisp<=0 || nivel>=3) return rutas; // si ya no hay más tiempo no seguir más
        ArrayList<Vuelo>vuelos = ciudOrigen.vuelos;
        for(int i=0;i<vuelos.size();i++){
            //caso directo
            Vuelo vuelo=vuelos.get(i);
            Ciudad ciudadFinVuelo=vuelo.getAeroFin();
            if(ciudadFinVuelo.getCodAeropuerto().equals(ciudFinal)){ // si cumple el destino
                if(vuelo.getTiempo()<=tiempoDisp) // si cumple la regla de negocio
                    rutas.add(new Ruta(vuelo,vuelo.getTiempo()));
            }
             //intento con escala
            int nivel2=nivel+1; // nuevocambio
            ArrayList<Ruta> rutasEscala=encuentraRutas(ciudadFinVuelo,ciudFinal,tiempoDisp-vuelo.getTiempo(),nivel2);
            int cantRutasEscala=rutasEscala.size();
            for(int j=0;j<cantRutasEscala;j++){ //verificar que se cumple el tiempo(considerando espera) x ruta
                Ruta ruta =rutasEscala.get(j);
                int tEspera;
                int tEsperaTotal=0;
                int tVueloTotal=vuelo.getTiempo();
                int cantVuelos=ruta.getVuelos().size();
                for(int h=0;h<cantVuelos;h++){
                    Vuelo vueloInt=ruta.getVuelos().get(h);
                    if(h==0) tEspera=vueloInt.gethSalida()-vuelo.gethLlegada(); //para el primer vuelo
                    else tEspera= vueloInt.gethSalida()-ruta.getVuelos().get(h-1).gethLlegada();
                    if(tEspera<0) tEspera+=24;
                    tEsperaTotal+=tEspera;
                    tVueloTotal+=vueloInt.getTiempo();
                }
                int tTotal=tVueloTotal+tEsperaTotal;
                if(tTotal<=tiempoDisp) {
                    ruta.getVuelos().add(0, vuelo);
                    ruta.setTiempo(tTotal);
                    rutas.add(ruta);
                }                  

            }
        }      
        return rutas;
    }
//    public static void generarRutas(TreeMap<String,Ciudad> ciudades) throws FileNotFoundException, IOException{
//        int tEspera;
//        int tiempoRuta;
////        File fRutas = new File("Rutas.txt");
////	FileOutputStream fOut = new FileOutputStream(fRutas);
////        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fOut));
////        bw.write("Todas las rutas");bw.newLine();
////        System.out.println("Cant ciudades: "+ciudades.size());
//        for(Ciudad ciudad: ciudades.values()){
//            ArrayList<Vuelo>vuelos=ciudad.vuelos;
//            int cantVuelos=vuelos.size();
//            for(int i=0;i<cantVuelos;i++){
//                String destino=vuelos.get(i).getDestino();
//                if(!ciudad.rutas.containsKey(destino)){ // si todavia no tiene ninguna ruta a ese destino
//                   ArrayList<Ruta> rutas=new ArrayList<>();
//                   rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
//                   ciudad.rutas.put(destino, rutas);
//                    //bw.write(ciudad.getCodAeropuerto()+"-"+destino);
//                    //bw.newLine();
//                }
//                else{
//                    ArrayList<Ruta> rutas=ciudad.rutas.get(destino);
//                    rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
//                    ciudad.rutas.put(destino, rutas);
//                     //bw.write(ciudad.getCodAeropuerto()+"-"+destino);
//                     //bw.newLine();
//                }
//                //caso con escala
//                Ciudad ciudadIntermedia=vuelos.get(i).getAeroFin();
//                for(int j=0;j<ciudadIntermedia.vuelos.size();j++){
//                    Vuelo vuelo2=ciudadIntermedia.vuelos.get(j);
//                    String destino2=vuelo2.getDestino();
//                    tEspera=vuelo2.gethSalida()-vuelos.get(i).gethLlegada();
//                    int tMax=24;
//                    if(!ciudad.getContinente().equals(ciudades.get(destino2).getContinente())) tMax=48;
//                    if(tEspera<0) tEspera+=24;
//                    tiempoRuta=vuelos.get(i).getTiempo()+vuelo2.getTiempo()+tEspera;
//                    //if (ciudad.getContinente().equals(ciudades.get(destino2).getContinente())) continue;
//                    //if(tiempoRuta>48) continue;
//                    if(tiempoRuta>tMax) continue;
////                    if (ciudad.getContinente().equals(ciudades.get(destino2).getContinente())&& tiempoRuta>24) continue;                    
////                    if(!ciudad.getContinente().equals(ciudades.get(destino2).getContinente())&& tiempoRuta>48) continue; // si se demora más de 48 horas, no tomar en cuenta
//                    if(!ciudad.rutas.containsKey(destino2)){ // si todavia no tiene ninguna ruta ese destino
//                        ArrayList<Ruta> rutas= new ArrayList<>();
//                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
//                        rutas.add(ruta);
//                        ciudad.rutas.put(destino2, rutas);
//                        //bw.write(ciudad.getCodAeropuerto()+"-"+destino+"-"+destino2);
//                        //bw.newLine();
//                    }
//                    else{
//                        ArrayList<Ruta> rutas=ciudad.rutas.get(destino2);
//                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
//                        rutas.add(ruta);
//                        ciudad.rutas.put(destino2,rutas);
//                        //bw.write(ciudad.getCodAeropuerto()+"-"+destino+"-"+destino2);
//                        //bw.newLine();
//                    }
//                    //System.out.println(tiempoRuta);
//                    
//                }
//            }
//        }  
//        //bw.close();
//    }
    
    public static int calcTiempoVuelo(Vuelo vuelo){
        int hSalida=vuelo.gethSalida();
        int hLlegada=vuelo.gethLlegada();
        int husoO=vuelo.getAeroOrig().huso;
        int husoF=vuelo.getAeroFin().huso;
        int tiempoVuelo=hLlegada-husoF-(hSalida-husoO);
        if (tiempoVuelo<0)tiempoVuelo+=24;
        return tiempoVuelo;
    }
    public static void asignarTipoVuelo(ArrayList<Vuelo> vuelos, TreeMap<String, Ciudad> ciudades){
        int nVuelos=vuelos.size();
        int nAeros=ciudades.size();
        int nOrig=0, nFin=0;
        for(int i=0; i<nVuelos;i++){
            Vuelo vueloActual=vuelos.get(i);
            String origen=vueloActual.getOrigen();
            String destino=vueloActual.getDestino();
            vuelos.get(i).setAeroOrig(ciudades.get(origen));//agrego aeropuerto origen
            vuelos.get(i).setAeroFin(ciudades.get(destino)); // agrego aeropuerto fin 
            int tiempoVuelo=calcTiempoVuelo(vueloActual);
            //System.out.println(origen+"-"+destino+" Tiempo Vuelo: "+tiempoVuelo);
            if(ciudades.get(origen).getContinente().equals(ciudades.get(destino).getContinente())){
                vuelos.get(i).setTipoVuelo("Continental");
                vuelos.get(i).setTiempo(tiempoVuelo);
            }
            else{
                vuelos.get(i).setTipoVuelo("Intercontinental");
                vuelos.get(i).setTiempo(tiempoVuelo);
            }
            if(!ciudades.get(origen).vecinos.contains(destino)) 
                ciudades.get(origen).vecinos.add(destino);//agrego vecinos
            ciudades.get(origen).vuelos.add(vuelos.get(i));// agrego vuelos del aeropuerto            
        }
    }
    
    public static void imprimirAeros(TreeMap<String,Ciudad> aeropuertos){
        for(Ciudad item:aeropuertos.values())
            System.out.println(item.getCiudad());
    }
    
}
