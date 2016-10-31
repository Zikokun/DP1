/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.*; 

/**
 *
 * @author gerson
 */
public class funcionesBaseDeDatos {
    private Connection conectar=null;
    
    public Connection conexion() throws InstantiationException, IllegalAccessException{   
        try{
           Class.forName("com.mysql.jdbc.Driver").newInstance();//.newInstance();
           
            setConectar(DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","contrasenha"));
       }catch(SQLException ex) {
           JOptionPane.showMessageDialog(null, "Error de conexion de la base de datos");
       }catch(ClassNotFoundException ex) { 
           return getConectar();
       }
        return getConectar();
    }
    public Connection getConectar() {
        return conectar;
    }

    public void setConectar(Connection conectar) {
        this.conectar = conectar;
    }
}
