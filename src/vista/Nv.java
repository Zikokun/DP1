/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utilitario.funcionesVentanaPrincipal;

/**
 *
 * @author a20090245
 */
public class Nv extends javax.swing.JFrame {

    /**
     * Creates new form Nv
     */
    public Nv() {
        initComponents();
         this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlFondo = new javax.swing.JPanel();
        pnlFrente = new javax.swing.JPanel();
        etiquetaNombre = new javax.swing.JLabel();
        etiquetaID = new javax.swing.JLabel();
        etiquetaCon = new javax.swing.JLabel();
        campoIdUsuario = new javax.swing.JTextField();
        campoContrasenha = new javax.swing.JTextField();
        botonConfirmar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.BorderLayout());

        pnlFondo.setLayout(new java.awt.BorderLayout());

        pnlFrente.setPreferredSize(new java.awt.Dimension(642, 444));
        pnlFrente.setLayout(new java.awt.GridBagLayout());

        etiquetaNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        etiquetaNombre.setText("TraslaPACK");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 10, 0, 0);
        pnlFrente.add(etiquetaNombre, gridBagConstraints);

        etiquetaID.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        etiquetaID.setText("ID Usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(61, 42, 0, 0);
        pnlFrente.add(etiquetaID, gridBagConstraints);

        etiquetaCon.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        etiquetaCon.setText("Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(33, 42, 0, 0);
        pnlFrente.add(etiquetaCon, gridBagConstraints);

        campoIdUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoIdUsuarioActionPerformed(evt);
            }
        });
        campoIdUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoIdUsuarioKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 158;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(58, 18, 0, 102);
        pnlFrente.add(campoIdUsuario, gridBagConstraints);

        campoContrasenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoContrasenhaKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 158;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 18, 0, 102);
        pnlFrente.add(campoContrasenha, gridBagConstraints);

        botonConfirmar.setText("Confirmar");
        botonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConfirmarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(81, 6, 25, 0);
        pnlFrente.add(botonConfirmar, gridBagConstraints);

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(81, 31, 25, 102);
        pnlFrente.add(botonCancelar, gridBagConstraints);

        pnlFondo.add(pnlFrente, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlFondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void abrirVentanaInicio() throws InstantiationException, IllegalAccessException{
        String usuario=this.campoIdUsuario.getText();
        String contrasenha=this.campoContrasenha.getText();

        funcionesVentanaPrincipal utilitarioVentanaPrincial = new funcionesVentanaPrincipal();
        String tipo = utilitarioVentanaPrincial.devolverTipoUsuario(usuario, contrasenha);

        if(tipo != USUARIO_NO_VALIDO){
            VentanaPrincipal vp=new VentanaPrincipal(usuario,contrasenha);
            vp.setVisible(true);
            this.setVisible(false);
        this.dispose();
        }else JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
        
    }
    
    private void botonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConfirmarActionPerformed
        try {
            // TODO add your handling code here:
            abrirVentanaInicio();
        } catch (InstantiationException ex) {
            Logger.getLogger(Nv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Nv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonConfirmarActionPerformed

    private void campoIdUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoIdUsuarioActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_campoIdUsuarioActionPerformed

    private void campoIdUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoIdUsuarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ENTER){
            try {
                abrirVentanaInicio();
            } catch (InstantiationException ex) {
                Logger.getLogger(Nv.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Nv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_campoIdUsuarioKeyPressed

    private void campoContrasenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoContrasenhaKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == evt.VK_ENTER){
            try {
                abrirVentanaInicio();
            } catch (InstantiationException ex) {
                Logger.getLogger(Nv.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Nv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_campoContrasenhaKeyPressed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Nv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Nv().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonConfirmar;
    private javax.swing.JTextField campoContrasenha;
    private javax.swing.JTextField campoIdUsuario;
    private javax.swing.JLabel etiquetaCon;
    private javax.swing.JLabel etiquetaID;
    private javax.swing.JLabel etiquetaNombre;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlFrente;
    // End of variables declaration//GEN-END:variables
}
