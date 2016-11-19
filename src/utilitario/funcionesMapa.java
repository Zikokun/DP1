/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constantesEstadoPaquetePorVuelo;
import static constantes.constantesEstadoPaquetePorVuelo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gerson
 */
public class funcionesMapa {
    public List<Object[]> devolverDetallePaquete() throws InstantiationException, IllegalAccessException, SQLException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        List<Object[]> lstPaquetes = new ArrayList<Object[]>();
        
        String SqlBuscarRutasPaquetes = " SELECT H.Paquete_idPaquete, P.longuitud, P.latitud, A.longuitud, A.latitud, B.longuitud , B.latitud " +
                                        " FROM avion_has_paquete H, vuelo V, almacen A, almacen B, paquete P " +
                                        " WHERE H.estado = " + ACTIVO.ordinal() + " AND H.Avion_idAvion = V.idVuelo AND V.idLugarOrigen = A.idAlmacen AND V.idLugarDestino = B.idAlmacen AND P.idPaquete = H.Paquete_idPaquete ";
        
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
        
        for(int i = 0; i < listaRutasActualizadas.size(); i++){
            int idPaquete = (Integer)listaRutasActualizadas.get(i)[0];
            float longuitudActual = (float)listaRutasActualizadas.get(i)[1];
            float latitudActual = (float)listaRutasActualizadas.get(i)[2];

            PreparedStatement sqlActualizarLongYLatActuales = conexion.prepareStatement(" UPDATE `paquete` SET `longuitud`='" + longuitudActual + "', `latitud`='" + latitudActual + "' WHERE `idPaquete`='"+ idPaquete +"'; ");
            int rows = sqlActualizarLongYLatActuales.executeUpdate();
        }
        conexion.close();
    }
}
