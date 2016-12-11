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
import java.sql.PreparedStatement;
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
    public void terminarSimulacion2(){
        if (PanelSim.simulacion != null) {
            PanelSim.simulacion.stop();
        }
        if (PanelSim.hilo.EstaCorriendo()) {
            PanelSim.hilo.Detener();
        }
//                if(!PanelSim.hilo.EstaCorriendo()){
//                    System.out.println("Se acabo el hilo de ruteo y se procede a limpiar la base");
//                    try {
//                        limpiarBase(PanelSim.getTipoSim());
////                         break;
//                    } catch (InstantiationException ex) {
//                        Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IllegalAccessException ex) {
//                        Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    
//                } 
//          
//        System.out.println("Fuera del while de limpieza");
                
        VentanaPrincipal.labelMostrarTiempoReal.setText("");
        VentanaPrincipal.labelMostrarTiempoReal.setVisible(false);
        VentanaPrincipal.botonPausa.setVisible(false);
        Mapa.mostrarBotonPausa = BOTON_PAUSA_NOVISIBLE;
    }
    public void terminarSimulacion(){
        if (PanelSim.simulacion != null) {
            PanelSim.simulacion.stop();
        }
        if (PanelSim.hilo.EstaCorriendo()) {
            PanelSim.hilo.Detener();
        }
                if(!PanelSim.hilo.EstaCorriendo()){
                    System.out.println("Se acabo el hilo de ruteo y se procede a limpiar la base");
                    try {
                        limpiarBase(PanelSim.getTipoSim());
//                         break;
                    } catch (InstantiationException ex) {
                        Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } 
          
        System.out.println("Fuera del while de limpieza");
                
        VentanaPrincipal.labelMostrarTiempoReal.setText("");
        VentanaPrincipal.labelMostrarTiempoReal.setVisible(false);
        VentanaPrincipal.botonPausa.setVisible(false);
        Mapa.mostrarBotonPausa = BOTON_PAUSA_NOVISIBLE;
    }
    public static void limpiarBase(int tipoSimu) throws InstantiationException, IllegalAccessException, SQLException{
            funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
            Connection conexion = cc.conexion();//null
            if(tipoSimu==0){//simu 3 dias
            PreparedStatement sqlActualizarLongYLatActuales = conexion.prepareStatement(" UPDATE `paquete` SET `longuitud`='" + 0 + "', `latitud`='" + 0 + "' WHERE `estado`='"+ 5 +"'; ");
            int rows = sqlActualizarLongYLatActuales.executeUpdate();
            
            PreparedStatement sqlActualizaPaquetes = conexion.prepareStatement(" UPDATE `paquete` SET `estado`='" + 7 +"' WHERE `estado`='" + 5 + "'" + " ; ");
            int rowsPaquetesActualizados = sqlActualizaPaquetes.executeUpdate();
            }else{//simu hasta que se caiga
                if(tipoSimu==1){
                    PreparedStatement sqlActualizarLongYLatActuales = conexion.prepareStatement(" UPDATE `paquete` SET `longuitud`='" + 0 + "', `latitud`='" + 0 + "' WHERE `estado`='"+ 6 +"'; ");
                    int rows = sqlActualizarLongYLatActuales.executeUpdate();
            
                    PreparedStatement sqlActualizaPaquetes = conexion.prepareStatement(" UPDATE `paquete` SET `estado`='" + 8 +"' WHERE `estado`='" + 6 + "'" + " ; ");
                    int rowsPaquetesActualizados = sqlActualizaPaquetes.executeUpdate();
                }
            }
            PreparedStatement sqlLimpiaRutas = conexion.prepareStatement(" DELETE  FROM avion_has_paquete; ");
            int rowsLimpiadas = sqlLimpiaRutas.executeUpdate();
              
            conexion.close();
            System.out.println("BD limpia");
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
