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


/**
 *
 * @author gerson
 */
public class funcionesBaseDeDatos {
    private Connection conectar=null;
    public Connection conexion(){   
      try{
        Class.forName("org.gjt.mm.mysql.Driver");//.newInstance();
            setConectar(DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","jinxRipperkiller12345"));
        //conectar=DriverManager.getConnection("jdbc:mysql://192.168.1.5:3306/personas","luis","");

      }catch(SQLException ex) {
                 JOptionPane.showMessageDialog(null, "Error de conexion de la base de datos");
                                       }catch(ClassNotFoundException ex) {                                                                }
      return getConectar();
    }

    /**
     * @return the conectar
     */
    public Connection getConectar() {
        return conectar;
    }

    /**
     * @param conectar the conectar to set
     */
    public void setConectar(Connection conectar) {
        this.conectar = conectar;
    }
}
