/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;

/**
 *
 * @author gerson
 */
public class funcionesVentanaPrincipal {
    public void esconderMenu(JMenu menu){
        menu.setEnabled(false);
        menu.setVisible(false);
    }
    
    public String devolverTipoUsuario(String usuario, String contrasenha) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();//null
                
        String valorTipoUsuario= "";
        String tipo = "";
        
        String sqlBuscarTipo = "SELECT tipoUsuario "
                             + "FROM `bdlp2_20090245`.`usuario` "
                             + "WHERE username='"+usuario+"'  AND Contrasenha='"+contrasenha+"'";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarTipo = st.executeQuery(sqlBuscarTipo);
            
            if(resultadoBuscarTipo!=null){
                resultadoBuscarTipo.next();
                
                valorTipoUsuario = resultadoBuscarTipo.getString("tipoUsuario");
            }
            
            while(resultadoBuscarTipo!=null && resultadoBuscarTipo.next()){
                valorTipoUsuario = resultadoBuscarTipo.getString(1);
            }
            
            if(!valorTipoUsuario.equals("")){
                tipo = valorTipoUsuario;
            }else{
                tipo = USUARIO_NO_VALIDO;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tipo;
    }
}
