/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constantesGenerales;
import static constantes.constantesGenerales.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}
