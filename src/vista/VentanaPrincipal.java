/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;

/**
 *
 * @author a20125540
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal(){
        initComponents();
    }
    public VentanaPrincipal(String type) {
        initComponents();
        if(type.equals("admin")){
            this.remove(pnlFrente);
            PanelSim ps=new PanelSim();
            pnlFondo.add(ps);
            this.revalidate();
            this.repaint();
        }
        if(type.equals("client")){
            this.remove(pnlFrente);
            panelMantUsuario pmu=new panelMantUsuario();
            pnlFondo.add(pmu);
            this.revalidate();
            this.repaint();
        }    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pnlFondo = new javax.swing.JPanel();
        pnlFrente = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuMant = new javax.swing.JMenu();
        mantUsuario = new javax.swing.JMenuItem();
        menuEnvio = new javax.swing.JMenu();
        menuRastreo = new javax.swing.JMenu();
        menuSim = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Bienvenido !");

        jLabel2.setText("Opciones");

        jLabel3.setText("Cerrar Sesión");

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

        jMenuBar1.add(menuMant);

        menuEnvio.setText("Envío");
        jMenuBar1.add(menuEnvio);

        menuRastreo.setText("Rastreo");
        jMenuBar1.add(menuRastreo);

        menuSim.setText("Simulación");
        jMenuBar1.add(menuSim);

        setJMenuBar(jMenuBar1);

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
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mantUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mantUsuarioActionPerformed
        // TODO add your handling code here:
        this.remove(pnlFrente);
        panelMantUsuario pmu=new panelMantUsuario();
        pnlFondo.add(pmu,BorderLayout.CENTER);
        this.revalidate();
    }//GEN-LAST:event_mantUsuarioActionPerformed

    private void mantUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mantUsuarioMouseClicked
        // TODO add your handling code here:
        this.remove(pnlFrente);
        panelMantUsuario pmu=new panelMantUsuario();
        pnlFondo.add(pmu,BorderLayout.CENTER);
        this.revalidate();
    }//GEN-LAST:event_mantUsuarioMouseClicked

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem mantUsuario;
    private javax.swing.JMenu menuEnvio;
    private javax.swing.JMenu menuMant;
    private javax.swing.JMenu menuRastreo;
    private javax.swing.JMenu menuSim;
    private javax.swing.JPanel pnlFondo;
    private javax.swing.JPanel pnlFrente;
    // End of variables declaration//GEN-END:variables
}
