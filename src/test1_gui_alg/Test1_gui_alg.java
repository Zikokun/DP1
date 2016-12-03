/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test1_gui_alg;
import static constantes.constanteEstadoPaquete.CON_TRES_DIAS_SIN_RUTA;
import controlador.Genetico;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Ciudad;
import modelo.Lectura;
import modelo.Pedido;
import modelo.Ruta;
import modelo.Vuelo;
import static org.apache.poi.hssf.usermodel.HSSFFooter.time;
import static org.apache.poi.hssf.usermodel.HSSFHeader.time;
import utilitario.FuncionExponencial;
import utilitario.funcionesAnimacionEjecSimu;
import utilitario.funcionesBaseDeDatos;
import utilitario.funcionesPanelSimulacion;
import utilitario.funcionesRuteo;
import vista.Nv;
import vista.VentanaPrincipal;
/**
 *
 * @author alulab
 */
public class Test1_gui_alg {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ParseException, IOException, FileNotFoundException, SQLException {
        /*funcionesPanelSimulacion fps = new funcionesPanelSimulacion();
        try {
            fps.lectorPaquetesSimulacion(0);
        } catch (ParseException ex) {
            Logger.getLogger(Test1_gui_alg.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //DataPrimaria();
        /*
        SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaActual = "2016-10-04 00:04:00";
        
        Date fecha = null;
        try {
            fecha = formatoDeFecha.parse(fechaActual);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(fecha);
        
        for (int i = 0; i < 48; i++) {
            System.out.println("Ruteo pedido en la fecha = " + calendarDate.getTime());
            funcionesRuteo fnRuteo = new funcionesRuteo();
            fnRuteo.ruteoPedidosTresDias(7, 5, calendarDate);
            calendarDate.add(Calendar.HOUR_OF_DAY,1);
        }*/
        
//        Lectura lect = new Lectura();
//        try {
//            lect.leerVuelosArchivos();
//        } catch (SQLException ex) {
//            Logger.getLogger(Test1_gui_alg.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        Nv nv= new Nv();
        nv.setVisible(true);
    }
    
    public static String principal() throws InstantiationException, IllegalAccessException{
        String mensaje = "";
        int hora,dia;
        Lectura lector= new Lectura();
        TreeMap<String,Ciudad> ciudades=new TreeMap<>();//MAP Key-Codigo Ciudad y VALUE Objeto Ciudad
        ArrayList<Vuelo> vuelos=new ArrayList<>();
        ArrayList<Pedido> pedidos=new ArrayList<>();
        
        //lector.leerPaquetesArchivos("src/recursos",pedidos);
        
        //lector.leerAeropuertosArchivos("src/recursos/plan_vuelo.txt");
        //lector.leerVuelosArchivos("src/recursos/plan_vuelo.txt");
        
        lector.leerArchivos("/recursos/_aeropuertos.OACI.txt", "/recursos/_plan_vuelo.txt",
                "/recursos/_pedidos_07-11-2016.txt", "/recursos/_husos_horarios.txt", vuelos, ciudades, pedidos);
        asignarTipoVuelo(vuelos,ciudades);
        generarRutas(ciudades);
//        for(Pedido item:pedidos)
//            item.print();
//        imprimirAeros(ciudades);
        Calendar calendario=Calendar.getInstance();
        hora=calendario.get(Calendar.HOUR_OF_DAY);
        dia=calendario.get(Calendar.DAY_OF_WEEK);  
        
        Genetico algoritmo=new Genetico();
        algoritmo.ejecutar(ciudades, vuelos, pedidos, hora, dia, mensaje);
        return mensaje;
    }
    
    public static void generarRutas(TreeMap<String,Ciudad> ciudades){
        int tEspera;
        int tiempoRuta;
        for(Ciudad ciudad: ciudades.values()){
            ArrayList<Vuelo>vuelos=ciudad.vuelos;
            int cantVuelos=vuelos.size();
            for(int i=0;i<cantVuelos;i++){
                String destino=vuelos.get(i).getDestino();
                if(!ciudad.rutas.containsKey(destino)){ // si todavia no tiene ninguna ruta a ese destino
                   ArrayList<Ruta> rutas=new ArrayList<>();
                   rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                   ciudad.rutas.put(destino, rutas);
                }
                else{
                    ArrayList<Ruta> rutas=ciudad.rutas.get(destino);
                    rutas.add(new Ruta(vuelos.get(i),vuelos.get(i).getTiempo()));
                    ciudad.rutas.put(destino, rutas);
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
                    }
                    else{
                        ArrayList<Ruta> rutas=ciudad.rutas.get(destino2);
                        Ruta ruta=new Ruta(vuelos.get(i),vuelo2,tiempoRuta);
                        rutas.add(ruta);
                        ciudad.rutas.put(destino2,rutas);
                    }
                    //System.out.println(tiempoRuta);
                }
            }
        }        
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
    
    public static void DataPrimaria() throws InstantiationException, IllegalAccessException, FileNotFoundException, IOException, SQLException, ParseException{
        String linea, archivo = "src/recursos/2arch_";
        int contBD=4;
        int llaveGeneradaPersona = -1;
        for (contBD = 4; contBD < 44; contBD++) {
            funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
            Connection conexion = cc.conexion();//null
            
            String origen = "";
            String sqlBuscarCiudad = "SELECT codCiudad FROM `almacen` WHERE idAlmacen = '" + contBD + "'";
            try {
                Statement st = conexion.createStatement();
                ResultSet resultadoBuscar = st.executeQuery(sqlBuscarCiudad);

                while (resultadoBuscar != null && resultadoBuscar.next()) {
                    origen = resultadoBuscar.getString("codCiudad");
                }
            } catch (SQLException ex) {
            }
            //System.out.println(contBD + " " + origen);
            
            String fecha = "";
            String sqlBuscarDestino = "";
            String nombreArchivo = archivo + origen + ".txt";
            int idDestino = 0;
            BufferedReader buffer = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = buffer.readLine()) != null) {
                String codigoPaquete = linea.substring(0, 9);
                int anho = Integer.parseInt(linea.substring(9, 13));
                int mes = Integer.parseInt(linea.substring(13, 15));
                int dia = Integer.parseInt(linea.substring(15, 17));
                int hora = Integer.parseInt(linea.substring(17, 19));
                int min = Integer.parseInt(linea.substring(20, 22));
                fecha = anho + "-" + mes + "-" + dia + " " + hora + ":" + min + ":00";
                String destino = linea.substring(22, 26);

                sqlBuscarDestino = "SELECT idAlmacen FROM `almacen` WHERE codCiudad = '" + destino + "'";
                Statement st2 = conexion.createStatement();
                ResultSet resultadoBuscar2 = st2.executeQuery(sqlBuscarDestino);

                while (resultadoBuscar2 != null && resultadoBuscar2.next()) {
                    idDestino = resultadoBuscar2.getInt("idAlmacen");
                }
                //CadenaQuery = "INSERT INTO `mydb`.`paquete` (`numeroRastreo`, `idLugarOrigen`, `idLugarDestino`, `fechaEnvio`,  `fechaRecepcion`, `estado`, `Cliente_idCliente`, `Persona_idPersona`) VALUES ('" + codigoPaquete + "', '" + contBD + "', '" + idDestino + "', '" + fecha + "', '" + fecha + "', '7' ,'1', '1')\n";
                PreparedStatement CadenaQuery = conexion.prepareStatement("INSERT INTO paquete VALUES (NULL,?,?,?,?,?,?,?,?,?,0,0)", PreparedStatement.RETURN_GENERATED_KEYS);
                CadenaQuery.setString(1, codigoPaquete);
                CadenaQuery.setInt(2, contBD);
                CadenaQuery.setInt(3, idDestino);
                java.text.SimpleDateFormat df = new java.text.SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
                Date formatDate = df.parse(fecha);
                Timestamp finalDate = new Timestamp(formatDate.getTime());
                CadenaQuery.setTimestamp(4, finalDate);
                CadenaQuery.setTimestamp(5, finalDate);
                CadenaQuery.setString(6, "");
                CadenaQuery.setInt(7, CON_TRES_DIAS_SIN_RUTA.ordinal());
                CadenaQuery.setInt(8, 1);
                CadenaQuery.setInt(9, 1);
                int rows = CadenaQuery.executeUpdate();

                ResultSet rs = CadenaQuery.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    llaveGeneradaPersona = rs.getInt(1);
                }
            }
            conexion.close();

        }
    }
}
