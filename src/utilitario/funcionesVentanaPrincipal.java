/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;

import static constantes.constantesGenerales.*;
import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import mapa.Mapa;
import static mapa.Mapa.mostrarBotonPausa;
import vista.PanelSim;
import vista.VentanaPrincipal;

/**
 *
 * @author gerson
 */
public class funcionesVentanaPrincipal {
    public void esconderMenu(JMenu menu){
        menu.setEnabled(false);
        menu.setVisible(false);
    }
    
    public void pausarSimulacion(){
        if (PanelSim.simulacion != null) {
            PanelSim.simulacion.stop();
        }
    }
    
    public void continuarSimulacion(){
        if (PanelSim.simulacion != null) {
            System.out.println("GG");
            PanelSim.simulacion.resume();
        }
    }
    
    public void terminarSimulacion(){
        if (PanelSim.simulacion != null) {
            PanelSim.simulacion.stop();
        }
        VentanaPrincipal.labelMostrarTiempoReal.setText("");
        VentanaPrincipal.labelMostrarTiempoReal.setVisible(false);
        VentanaPrincipal.botonPausa.setVisible(false);
        Mapa.mostrarBotonPausa = BOTON_PAUSA_NOVISIBLE;
    }
    
    public String devolverTipoUsuario(String usuario, String contrasenha) throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();//null
                
        String valorTipoUsuario= "";
        String tipo = "";
        
        String sqlBuscarTipo = "SELECT tipoUsuario "
                             + "FROM usuario "
                             + "WHERE nombreUsuario='"+usuario+"'  AND contrasenha='"+contrasenha+"'";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarTipo = st.executeQuery(sqlBuscarTipo);
            
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
