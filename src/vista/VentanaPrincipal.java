/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import static constantes.constantesGenerales.*;
import static constantes.constantesVentanaPrincipal.*;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.*;
import utilitario.funcionesBaseDeDatos;
import utilitario.funcionesVentanaPrincipal;

import utilitario.funcionesVentanaPrincipal.*;

import javax.swing.Timer;
 

/**
 *
 * @author a20125540
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    public String tipoUsuario;
    private String usuario;
    private String contrasenha;
    public funcionesVentanaPrincipal utilitarioVentanaPrincial = new funcionesVentanaPrincipal();
    public VentanaPrincipal(){
        initComponents();
        this.setLocationRelativeTo(null);
    }
    public VentanaPrincipal(String type) {
        initComponents();
        this.setLocationRelativeTo(null);

        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection cn = cc.getConectar();
        
        tipoUsuario=type;
        String mensajeBien="Bienvenido " + type +" !";
        if(type.equals("admin")){
            labelBienvenido.setText(mensajeBien);

            utilitarioVentanaPrincial.esconderMenu(menuMant);

            utilitarioVentanaPrincial.esconderMenu(menuEnvio);

            utilitarioVentanaPrincial.esconderMenu(menuRastreo);
            
            pnlFrente.setVisible(false);
            PanelSim ps=new PanelSim();
            pnlFondo.add(ps);
            this.revalidate();
            this.repaint();
        }
        if(type.equals("client")){

            this.pnlFrente.setVisible(false);
            
            utilitarioVentanaPrincial.esconderMenu(menuMant);
            
            utilitarioVentanaPrincial.esconderMenu(menuSim);

            this.revalidate();
            this.repaint();
        }
        if(type.equals("user")){
            labelBienvenido.setText(mensajeBien);

            this.pnlFrente.setVisible(false);

            utilitarioVentanaPrincial.esconderMenu(menuSim);

            this.revalidate();
            this.repaint();
        }
    }
    
    public VentanaPrincipal(String usuario, String contrasenha) throws InstantiationException, IllegalAccessException{
        
        String tipo = utilitarioVentanaPrincial.devolverTipoUsuario(usuario, contrasenha);
        this.usuario = usuario;
        this.contrasenha = contrasenha;
        this.tipoUsuario = tipo;
        String mensajeBien = "";
        
        this.setUsuario(usuario);
        this.setContrasenha(contrasenha);
        
        if(!tipoUsuario.equals(USUARIO_NO_VALIDO)){
            mensajeBien="Bienvenido " + usuario +" !";
            initComponents();
            this.setLocationRelativeTo(null);
            labelBienvenido.setText(mensajeBien);
            pnlFrente.setVisible(false);
        }
        
        if(tipoUsuario.equals(TIPO_ADMIN)){
            utilitarioVentanaPrincial.esconderMenu(menuMant);
            utilitarioVentanaPrincial.esconderMenu(menuEnvio);
            utilitarioVentanaPrincial.esconderMenu(menuRastreo);
            PanelSim ps=new PanelSim();
            pnlFondo.add(ps);
        }
        
        if(tipoUsuario.equals(TIPO_CLIENTE)){ 
            utilitarioVentanaPrincial.esconderMenu(menuMant);
            utilitarioVentanaPrincial.esconderMenu(menuSim);
        }
        
        if(tipoUsuario.equals(TIPO_OPERARIO)){//Operario
            utilitarioVentanaPrincial.esconderMenu(menuSim);
        }
        
        if(!tipoUsuario.equals(USUARIO_NO_VALIDO)){
            this.revalidate();
            this.repaint();
        }else{
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelBienvenido = new javax.swing.JLabel();
        labelOpciones = new javax.swing.JLabel();
        labelCerrarSesion = new javax.swing.JLabel();
        pnlFondo = new javax.swing.JPanel();
        pnlFrente = new javax.swing.JPanel();
        barraMenu = new javax.swing.JMenuBar();
        menuMiCuenta = new javax.swing.JMenu();
        menuMant = new javax.swing.JMenu();
        mantUsuario = new javax.swing.JMenuItem();
        menuEnvio = new javax.swing.JMenu();
        envoRegEnvio = new javax.swing.JMenuItem();
        envioVisualizarHistorial = new javax.swing.JMenuItem();
        menuRastreo = new javax.swing.JMenu();
        menuSim = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelBienvenido.setText("Bienvenido !");

        labelOpciones.setText("Opciones");

        labelCerrarSesion.setText("Cerrar Sesión");
        labelCerrarSesion.setBorder(new javax.swing.border.SoftBevelBorder(0));
        labelCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCerrarSesionMouseClicked(evt);
            }
        });

        pnlFondo.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout pnlFrenteLayout = new javax.swing.GroupLayout(pnlFrente);
        pnlFrente.setLayout(pnlFrenteLayout);
        pnlFrenteLayout.setHorizontalGroup(
            pnlFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlFrenteLayout.setVerticalGroup(
            pnlFrenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );

        pnlFondo.add(pnlFrente, java.awt.BorderLayout.CENTER);

        menuMiCuenta.setText("Mi Cuenta");
        menuMiCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMiCuentaMouseClicked(evt);
            }
        });
        barraMenu.add(menuMiCuenta);

        menuMant.setText("Mantenimiento");

        mantUsuario.setLabel("Usuario");
        mantUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mantUsuarioMouseClicked(evt);
            }
        });
        mantUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mantUsuarioActionPerformed(evt);
            }
        });
        menuMant.add(mantUsuario);

        barraMenu.add(menuMant);

        menuEnvio.setText("Envío");

        envoRegEnvio.setText("Registrar envio");
        envoRegEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                envoRegEnvioActionPerformed(evt);
            }
        });
        menuEnvio.add(envoRegEnvio);

        envioVisualizarHistorial.setText("Visualizar detalle");
        envioVisualizarHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                envioVisualizarHistorialActionPerformed(evt);
            }
        });
        menuEnvio.add(envioVisualizarHistorial);

        barraMenu.add(menuEnvio);

        menuRastreo.setText("Rastreo");
        menuRastreo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuRastreoMouseClicked(evt);
            }
        });
        menuRastreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRastreoActionPerformed(evt);
            }
        });
        barraMenu.add(menuRastreo);

        menuSim.setText("Simulación");
        menuSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSimActionPerformed(evt);
            }
        });
        barraMenu.add(menuSim);

        setJMenuBar(barraMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 588, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelBienvenido)
                                .addGap(29, 29, 29))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelOpciones)
                                .addGap(26, 26, 26)
                                .addComponent(labelCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelBienvenido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelOpciones)
                    .addComponent(labelCerrarSesion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mantUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantUsuarioActionPerformed
        // TODO add your handling code here:
        this.remove(pnlFrente);
        pnlFondo.removeAll();
        panelMantUsuario pmu=new panelMantUsuario(usuario,contrasenha,tipoUsuario,DISTINGUIDOR);
        pnlFondo.add(pmu,BorderLayout.CENTER);
        this.revalidate();
    }//GEN-LAST:event_mantUsuarioActionPerformed

    private void mantUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mantUsuarioMouseClicked
        // TODO add your handling code here:
        this.remove(pnlFrente);
        panelMantUsuario pmu=new panelMantUsuario(usuario,contrasenha,tipoUsuario,DISTINGUIDOR);
        pnlFondo.add(pmu,BorderLayout.CENTER);
        this.revalidate();
    }//GEN-LAST:event_mantUsuarioMouseClicked

    private void labelCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCerrarSesionMouseClicked
        // TODO add your handling code here:
        
        //Mensaje para salir
        Object[] opciones={"Sí","No"};
        int respuesta=JOptionPane.showOptionDialog(this,"¿Estás seguro que deseas cerrar sesión?", "Cerrar Sesión",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
        if(respuesta==JOptionPane.YES_OPTION){ 
            this.setVisible(false);
            this.dispose();
            Nv login=new Nv();
            login.setVisible(true);
        }
    }//GEN-LAST:event_labelCerrarSesionMouseClicked

    private void menuRastreoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuRastreoMouseClicked
        // TODO add your handling code here:
        if(tipoUsuario.equals(TIPO_OPERARIO)||tipoUsuario.equals(TIPO_CLIENTE)){
            this.remove(pnlFrente);
            pnlFondo.removeAll();
            panelPaqueteBusqueda ppb=new panelPaqueteBusqueda();
            pnlFondo.add(ppb,BorderLayout.CENTER);
            this.revalidate();
        }
    }//GEN-LAST:event_menuRastreoMouseClicked

    private void menuRastreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRastreoActionPerformed
        // TODO add your handling code here:
        if(tipoUsuario.equals(TIPO_OPERARIO)||tipoUsuario.equals(TIPO_CLIENTE)){
            this.remove(pnlFrente);
            pnlFondo.removeAll();
            panelPaqueteBusqueda ppb=new panelPaqueteBusqueda();
            pnlFondo.add(ppb,BorderLayout.CENTER);
            this.revalidate();
        }
    }//GEN-LAST:event_menuRastreoActionPerformed

    private void menuSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSimActionPerformed
        // TODO add your handling code here:
        if(tipoUsuario.equals(TIPO_ADMIN)){
            this.remove(pnlFrente);
            pnlFondo.removeAll();
            PanelSim ps=new PanelSim();
            pnlFondo.add(ps,BorderLayout.CENTER);
            this.revalidate();
        }
    }//GEN-LAST:event_menuSimActionPerformed

    private void envoRegEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envoRegEnvioActionPerformed
        // TODO add your handling code here:
        if(tipoUsuario.equals(TIPO_OPERARIO)){
         this.remove(pnlFrente);
         pnlFondo.removeAll();
         PanelCrearEnvio pb = null;
            try {
                pb = new PanelCrearEnvio();
            } catch (InstantiationException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
         pnlFondo.add(pb,BorderLayout.CENTER);
         this.revalidate();
        }
    }//GEN-LAST:event_envoRegEnvioActionPerformed

    private void envioVisualizarHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envioVisualizarHistorialActionPerformed
        if(tipoUsuario.equals(TIPO_OPERARIO)||tipoUsuario.equals(TIPO_CLIENTE)){
            try {
                this.remove(pnlFrente);
                pnlFondo.removeAll();
                panelPaqueteBusqueda pdp=new panelPaqueteBusqueda(usuario,contrasenha,tipoUsuario);
                pnlFondo.add(pdp,BorderLayout.CENTER);
                this.revalidate();
            } catch (InstantiationException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_envioVisualizarHistorialActionPerformed

    private void menuMiCuentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMiCuentaMouseClicked
        try {
            this.remove(pnlFrente);
            pnlFondo.removeAll();
            panelMantUsuario pmu=new panelMantUsuario(usuario,contrasenha,tipoUsuario);
            pnlFondo.add(pmu,BorderLayout.CENTER);
            this.revalidate();
        } catch (InstantiationException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuMiCuentaMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JMenuBar barraMenu;
    private javax.swing.JMenuItem envioVisualizarHistorial;
    private javax.swing.JMenuItem envoRegEnvio;
    public javax.swing.JLabel labelBienvenido;
    public javax.swing.JLabel labelCerrarSesion;
    public javax.swing.JLabel labelOpciones;
    private javax.swing.JMenuItem mantUsuario;
    private javax.swing.JMenu menuEnvio;
    private javax.swing.JMenu menuMant;
    private javax.swing.JMenu menuMiCuenta;
    private javax.swing.JMenu menuRastreo;
    private javax.swing.JMenu menuSim;
    public static javax.swing.JPanel pnlFondo;
    public static javax.swing.JPanel pnlFrente;
    // End of variables declaration//GEN-END:variables

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }
}
