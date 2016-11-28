/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constanteEstadoPaquete.*;
import static constantes.constantesEstadoPaquetePorVuelo.ACTIVO;
import static constantes.constantesEstadoPaquetePorVuelo.INACTIVO;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapa.Mapa;

/**
 *
 * @author gerson
 */
public class funcionesHiloActualizacionPaquetes extends Thread{
    
    public funcionesHiloActualizacionPaquetes(){
        
    }
    
    public void run(){
        try {
            while (true) {
                List<Object[]> lstDetallePaquetes = devolverDetallePaquete();
                actualizarEstadoPaquetesYRutas(lstDetallePaquetes);
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(funcionesHiloActualizacionPaquetes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(funcionesHiloActualizacionPaquetes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(funcionesHiloActualizacionPaquetes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(funcionesHiloActualizacionPaquetes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Object[]> devolverDetallePaquete() throws InstantiationException, IllegalAccessException, SQLException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaActual = new Date();
        String sTiempo = fecha.format(fechaActual);
        
        List<Object[]> lstPaquetes = new ArrayList<Object[]>();
        String SqlBuscarRutasPaquetes = " SELECT H.Paquete_idPaquete, P.longuitud, P.latitud, A.longuitud, A.latitud, B.longuitud , B.latitud, P.estado, H.horaSalida, H.horaLlegada " +
                                        " FROM avion_has_paquete H, vuelo V, almacen A, almacen B, paquete P " +
                                        " WHERE H.estado = " + ACTIVO.ordinal() + " AND '" + sTiempo + "' > H.horaSalida AND H.Avion_idAvion = V.idVuelo AND V.idLugarOrigen = A.idAlmacen AND V.idLugarDestino = B.idAlmacen AND P.idPaquete = H.Paquete_idPaquete "
                                      + " AND P.estado = " + SIN_ENVIAR_CON_RUTA.ordinal();
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarRutasPaquetes = st.executeQuery(SqlBuscarRutasPaquetes);
            
            while(resultadoBuscarRutasPaquetes!=null && resultadoBuscarRutasPaquetes.next()){
                Object[] datosRuta = new Object[10];
                datosRuta[0] = resultadoBuscarRutasPaquetes.getInt(1);
                datosRuta[1] = resultadoBuscarRutasPaquetes.getFloat(2);
                datosRuta[2] = resultadoBuscarRutasPaquetes.getFloat(3);
                datosRuta[3] = resultadoBuscarRutasPaquetes.getFloat(4);
                datosRuta[4] = resultadoBuscarRutasPaquetes.getFloat(5);
                datosRuta[5] = resultadoBuscarRutasPaquetes.getFloat(6);
                datosRuta[6] = resultadoBuscarRutasPaquetes.getFloat(7);
                datosRuta[7] = resultadoBuscarRutasPaquetes.getInt(8);
                
                Date fechaSalida = (Date)resultadoBuscarRutasPaquetes.getObject(9); //FechaNacimiento
                DateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
                datosRuta[8] = fechaFormat.format(fechaSalida);
                
                Date fechaLlegada = (Date)resultadoBuscarRutasPaquetes.getObject(10); //FechaNacimiento
                datosRuta[9] = fechaFormat.format(fechaLlegada);
                
                lstPaquetes.add(datosRuta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.close();
        return lstPaquetes;
    }
    
    public void actualizarEstadoPaquetesYRutas(List<Object[]> listaRutasActualizadas) throws InstantiationException, IllegalAccessException, SQLException, ParseException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        try {
            for(int i = 0; i < listaRutasActualizadas.size(); i++){
                int idPaquete = (Integer)listaRutasActualizadas.get(i)[0];
                SimpleDateFormat formatoDeFechaNacimiento = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fechaLlegadaAlmacen = formatoDeFechaNacimiento.parse((String)listaRutasActualizadas.get(i)[9]);
                
                DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fechaActual = new Date();
                String sTiempo = fecha.format(fechaActual);
                
                if(fechaLlegadaAlmacen.before(fechaActual)){//Se paso la fecha, cambia de ruta
                    String sqlBuscarActual = "SELECT Avion_idAvion, Paquete_idPaquete " +
                                             " FROM avion_has_paquete " +
                                             " WHERE estado = " + ACTIVO.ordinal() + " AND Paquete_idPaquete = " + idPaquete +"; ";
                    
                    Statement st = conexion.createStatement();
                    ResultSet resultadoBuscarActuales = st.executeQuery(sqlBuscarActual);
                    
                    while(resultadoBuscarActuales!=null && resultadoBuscarActuales.next()){ //Si no entra es por que ya llego a su ruta final
                        int idVueloActual = resultadoBuscarActuales.getInt(1);
                        int idPaqueteActual = resultadoBuscarActuales.getInt(2);
                        
                        String sqlBuscarSiguientes = " SELECT M.Avion_idAvion, M.Paquete_idPaquete " +
                                                     " FROM avion_has_paquete M, vuelo W " +
                                                     " WHERE M.Paquete_idPaquete = " + idPaquete + " AND M.estado = " + INACTIVO.ordinal() + " AND " + 
                                                     " M.Avion_idAvion = W.idVuelo AND W.idLugarOrigen = (SELECT V.idLugarDestino " +
                                                                                                   " FROM avion_has_paquete H, vuelo V " +
                                                                                                   " WHERE H.Paquete_idPaquete = " + idPaquete + " AND H.Avion_idAvion = V.idVuelo AND H.estado = " + ACTIVO.ordinal() +");";
                        
                        Statement stBuscarSiguiente = conexion.createStatement();
                        ResultSet resultadoBuscarSiguientes = stBuscarSiguiente.executeQuery(sqlBuscarSiguientes);
                        
                        while(resultadoBuscarSiguientes!=null && resultadoBuscarSiguientes.next()){ //Si no entra era poe que es su destino final, si entra es por que hay mas rutas aun
                            int idVueloSiguiente = resultadoBuscarSiguientes.getInt(1);
                            int idPaqueteSiguiente = resultadoBuscarSiguientes.getInt(2);
                            
                            PreparedStatement sqlVueloSiguiente = conexion.prepareStatement(" UPDATE `avion_has_paquete` SET `estado`='" + ACTIVO.ordinal() +"' WHERE `Avion_idAvion`='" + idVueloSiguiente + "' and `Paquete_idPaquete`='" + idPaqueteSiguiente + "'; ");
                            int rowsVueloSiguiente = sqlVueloSiguiente.executeUpdate();
                        }
                        
                        PreparedStatement sqlVueloActual = conexion.prepareStatement(" UPDATE `avion_has_paquete` SET `estado`='" + INACTIVO.ordinal() +"' WHERE `Avion_idAvion`='" + idVueloActual + "' and `Paquete_idPaquete`='" + idPaqueteActual + "'; ");
                        int rowsVueloActual = sqlVueloActual.executeUpdate();
                    }
                }
            }
        }catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        conexion.close();
    }
}
