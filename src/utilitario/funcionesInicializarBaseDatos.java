/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constantesEstadoVuelo;
import static constantes.constantesEstadoVuelo.*;
import constantes.constantesGenerales;
import static constantes.constantesGenerales.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class funcionesInicializarBaseDatos {
    public void insertarAlmacenes(ArrayList<String> listaAlmacenes) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        String cadena;
        try{
            for(int i = 0; i < listaAlmacenes.size(); i++){
                String codigoAlmacen = listaAlmacenes.get(i);
                PreparedStatement sqlInsertAlmacenes = conexion.prepareStatement("INSERT INTO almacen VALUES (NULL,?,?,?,?,?)");
                sqlInsertAlmacenes.setString(1, codigoAlmacen);
                sqlInsertAlmacenes.setInt(2, CAPACIDAD_MAX_ALMACEN);
                sqlInsertAlmacenes.setInt(3, CAPACIDAD_MIN_ALMACEN);
                sqlInsertAlmacenes.setInt(4,0);
                sqlInsertAlmacenes.setInt(5,0);
                int rows = sqlInsertAlmacenes.executeUpdate();
                
                if(rows > 0){
                    cadena= "Almacen insertado con éxito";
                }else{
                    cadena= "Almacen no se ha insertado";
                }                
            }
        }catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, "Error al registrar un almacen", ex);
        }
    }
    
    public void insertarVuelos(ArrayList<String[]> listaVuelos) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String cadena;
        try{
            for(int i = 0; i < listaVuelos.size(); i++){
                String[] datosVuelo = listaVuelos.get(i);
                PreparedStatement sqlInsertAlmacenes = conexion.prepareStatement("INSERT INTO vuelo VALUES (NULL,?,?,?,?,?,?,?,?)");
                sqlInsertAlmacenes.setInt(1, CAPACIDAD_MAX_VUELO); //Capacidad Maxima
                sqlInsertAlmacenes.setInt(2, CAPACIDAD_MIN_VUELO); //Capacidad Minima
                sqlInsertAlmacenes.setInt(3, DISPONIBLE.ordinal()); //Estado
                
                int idCiudadOrigen = -1;
                String sqlBuscarCiudadOrigen = " SELECT A.idAlmacen " +
                                               " FROM almacen A " +
                                               " WHERE A.ubicacion = '"+datosVuelo[0]+"';";
                                
                Statement st = conexion.createStatement();
                ResultSet resultadoBuscarCiudadOrigen = st.executeQuery(sqlBuscarCiudadOrigen);
                
                while(resultadoBuscarCiudadOrigen!=null && resultadoBuscarCiudadOrigen.next()){
                    idCiudadOrigen = resultadoBuscarCiudadOrigen.getInt(1);
                }
                
                sqlInsertAlmacenes.setInt(4, idCiudadOrigen);
                
                int idCiudadDestino = -1;
                String sqlBuscarCiudadDestino = " SELECT B.idAlmacen " +
                                               " FROM almacen B " +
                                               " WHERE B.ubicacion = '"+datosVuelo[1]+"';";
                
                st = conexion.createStatement();
                ResultSet resultadoBuscarCiudadDestino = st.executeQuery(sqlBuscarCiudadDestino);
                
                while(resultadoBuscarCiudadDestino!=null && resultadoBuscarCiudadDestino.next()){
                    idCiudadDestino = resultadoBuscarCiudadDestino.getInt(1);
                }
                
                sqlInsertAlmacenes.setInt(5, idCiudadDestino);
                sqlInsertAlmacenes.setInt(6,1); //Si es continental o no , aqui el id le pongo 1, por que la verdad no se

                sqlInsertAlmacenes.setTime(7, Time.valueOf(datosVuelo[2] + ":00"));
                sqlInsertAlmacenes.setTime(8, Time.valueOf(datosVuelo[3] + ":00"));
                
                int rows = sqlInsertAlmacenes.executeUpdate();
                
                if(rows > 0){
                    cadena= "Vuelo insertado con éxito";
                }else{
                    cadena= "Vuelo no se ha insertado";
                }                
            }
        }catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, "Error al registrar un Vuelo", ex);
        }
    }
}
