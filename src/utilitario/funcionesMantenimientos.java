/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import modelo.*;

/**
 *
 * @author Camila
 */
public class funcionesMantenimientos {
    
    public String RegistrarUsuario(Usuario newuser) throws InstantiationException, IllegalAccessException, SQLException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        System.out.println(cc);
        Connection conexion = cc.conexion();//null
        String cadena="";
        
        try {
            PreparedStatement sqlGuardar = conexion.prepareStatement("INSERT INTO basededatos.persona VALUES (?,?,?,?,?,?,?,?)");
            sqlGuardar.setString(1, "1");
            sqlGuardar.setString(2, newuser.getNombre());
            sqlGuardar.setString(3, newuser.getApellidoP());
            sqlGuardar.setString(4, newuser.getApellidoM());
            sqlGuardar.setString(5, newuser.getFechaNac());
            sqlGuardar.setString(6, newuser.getDireccion());
            sqlGuardar.setString(7, newuser.getCorreo());
            sqlGuardar.setString(8, newuser.getDocumento());
            int rows = sqlGuardar.executeUpdate();
            
            if(rows > 0){
                cadena= "Usuario insertado con éxito";
            }
            else{
                cadena= "Usuario no se ha insertado";
            }
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadena;
    }
}
