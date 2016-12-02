/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constanteEstadoPaquete.*;
import constantes.constantesEstadoPaquetePorVuelo;
import static constantes.constantesEstadoPaquetePorVuelo.*;
import static constantes.constantesGenerales.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapa.Mapa;
import vista.PanelSim;

/**
 *
 * @author gerson
 */
public class funcionesMapa {
    public Date devolverFechaInicioSimuAnt() throws InstantiationException, IllegalAccessException{
        Date fechaInicio = null;
        
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        /*String sqlBuscarMenorFecha = " SELECT MIN(fechaEnvio) " +
                                           " FROM paquete; ";*/
        int tipoSimu = PanelSim.tipoSim;
        int tipoSimulacion;
        if (tipoSimu == 2) {
            tipoSimulacion = SIN_ENVIAR_CON_RUTA.ordinal();
        } else if (tipoSimu == 0) {
            tipoSimulacion = CON_TRES_DIAS_SIN_RUTA.ordinal();
        } else { // tipoSimu==1
            tipoSimulacion = SIMULACION_SIN_TRES_DIAS_SIN_RUTA.ordinal();
        }

        String sqlBuscarMenorFecha = " SELECT fechaActualSimu "
                + " FROM `datossimu` "
                + " WHERE iddatosSimu = '1' " + tipoSimulacion + "; ";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoMenorFecha = st.executeQuery(sqlBuscarMenorFecha);
            
            
            while(resultadoMenorFecha!=null && resultadoMenorFecha.next()){
                fechaInicio = (Date)resultadoMenorFecha.getObject(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fechaInicio;
    } 
    public Date devolverFechaInicio() throws InstantiationException, IllegalAccessException{
        Date fechaInicio = null;
        
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        /*String sqlBuscarMenorFecha = " SELECT MIN(fechaEnvio) " +
                                           " FROM paquete; ";*/
        int tipoSimu = PanelSim.tipoSim;
        int tipoSimulacion;
        if (tipoSimu == 2) {
            tipoSimulacion = SIN_ENVIAR_CON_RUTA.ordinal();
        } else if (tipoSimu == 0) {
            tipoSimulacion = CON_TRES_DIAS_SIN_RUTA.ordinal();
        } else { // tipoSimu==1
            tipoSimulacion = SIMULACION_SIN_TRES_DIAS_SIN_RUTA.ordinal();
        }

        String sqlBuscarMenorFecha = " SELECT MIN(fechaEnvio) "
                + " FROM paquete "
                + " WHERE paquete.estado = " + tipoSimulacion + "; ";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoMenorFecha = st.executeQuery(sqlBuscarMenorFecha);
            
            
            while(resultadoMenorFecha!=null && resultadoMenorFecha.next()){
                fechaInicio = (Date)resultadoMenorFecha.getObject(1);
            }
        }catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return fechaInicio;
    }  
    public int devolverHoraInicial() throws InstantiationException, IllegalAccessException{
        int horaInicial = -1;
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String sqlBuscarMenorHoraLlegada = " SELECT MIN(horaSalida) " +
                                           " FROM vuelo; ";
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoMenorHoraLlegada = st.executeQuery(sqlBuscarMenorHoraLlegada);
            
            while(resultadoMenorHoraLlegada!=null && resultadoMenorHoraLlegada.next()){
                Time horaSalida = (Time) resultadoMenorHoraLlegada.getObject(1);
                Calendar cal = Calendar.getInstance();
                cal.setTime(horaSalida);
                int horaS = cal.get(Calendar.HOUR_OF_DAY);
                int minS = cal.get(Calendar.MINUTE);

                horaInicial = horaS;
            }
        }catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        return horaInicial;
    }
    
    private String devolverStringSqlTipoSimulacion(int tipoSimulacion){
        String sqlTipoSimulacion = "";
        
        if(tipoSimulacion == TIPO_SIMULACION_MANUAL){
            sqlTipoSimulacion = " AND P.estado = " + SIN_ENVIAR.ordinal() + " ";
        }else if(tipoSimulacion == TIPO_SIMULACION_TRES_DIAS){
            sqlTipoSimulacion = " AND P.estado = " + CON_TRES_DIAS.ordinal() + " ";
        }else {
            sqlTipoSimulacion = " AND P.estado = " + SIMULACION_SIN_TRES_DIAS.ordinal() + " ";
        }
        
        return sqlTipoSimulacion;
    }
    
    public List<Object[]> devolverDetallePaquete(int tipoSimulacion) throws InstantiationException, IllegalAccessException, SQLException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String sqlTipoSimulacion = devolverStringSqlTipoSimulacion(tipoSimulacion);
        
        List<Object[]> lstPaquetes = new ArrayList<Object[]>();
        DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //("yyyy-MM-dd HH:mm:ss");
        String sTiempo = fecha.format(Mapa.getFechaInicial());
        
        String SqlBuscarRutasPaquetes = " SELECT H.Paquete_idPaquete, P.longuitud, P.latitud, A.longuitud, A.latitud, B.longuitud , B.latitud " +
                                        " FROM avion_has_paquete H, vuelo V, almacen A, almacen B, paquete P " +
                                        " WHERE H.estado = " + ACTIVO.ordinal() + " AND '" + sTiempo + "' > H.horaSalida AND H.Avion_idAvion = V.idVuelo AND V.idLugarOrigen = A.idAlmacen AND V.idLugarDestino = B.idAlmacen AND P.idPaquete = H.Paquete_idPaquete "
                                        + sqlTipoSimulacion +";";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarRutasPaquetes = st.executeQuery(SqlBuscarRutasPaquetes);
            
            while(resultadoBuscarRutasPaquetes!=null && resultadoBuscarRutasPaquetes.next()){
                Object[] datosRuta = new Object[7];
                datosRuta[0] = resultadoBuscarRutasPaquetes.getInt(1);
                datosRuta[1] = resultadoBuscarRutasPaquetes.getFloat(2);
                datosRuta[2] = resultadoBuscarRutasPaquetes.getFloat(3);
                datosRuta[3] = resultadoBuscarRutasPaquetes.getFloat(4);
                datosRuta[4] = resultadoBuscarRutasPaquetes.getFloat(5);
                datosRuta[5] = resultadoBuscarRutasPaquetes.getFloat(6);
                datosRuta[6] = resultadoBuscarRutasPaquetes.getFloat(7);
                
                lstPaquetes.add(datosRuta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesPanelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.close();
        return lstPaquetes;
    }
    
    public void insertarLonguitudYLatitudActualizados(List<Object[]> listaRutasActualizadas) throws InstantiationException, IllegalAccessException, SQLException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        try {
            for(int i = 0; i < listaRutasActualizadas.size(); i++){
                int idPaquete = (Integer)listaRutasActualizadas.get(i)[0];
                float longuitudActual = (float)listaRutasActualizadas.get(i)[1];
                float latitudActual = (float)listaRutasActualizadas.get(i)[2];

                float longuitudDestino = (float)listaRutasActualizadas.get(i)[5];
                float latitudDestino = (float)listaRutasActualizadas.get(i)[6];

                //Cambio posición actual del paquete
                PreparedStatement sqlActualizarLongYLatActuales = conexion.prepareStatement(" UPDATE `paquete` SET `longuitud`='" + longuitudActual + "', `latitud`='" + latitudActual + "' WHERE `idPaquete`='"+ idPaquete +"'; ");
                int rows = sqlActualizarLongYLatActuales.executeUpdate();

                //Verifico si el avion ya llegó a su destino
                if (((longuitudActual <= longuitudDestino + 0.2f) && (longuitudActual >= longuitudDestino - 0.2f))
                        || ((latitudActual <= latitudDestino + 0.2f) && (latitudActual >= latitudDestino - 0.2f))) { //Se llego a su destino, se trabaj asi por que son float
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
