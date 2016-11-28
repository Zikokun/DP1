/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import mapa.Mapa;
import processing.core.PApplet;
import de.fhpotsdam.unfolding.marker.Marker;
/**
 *
 * @author a20125540
 */
public class PanelSim extends javax.swing.JPanel {

    /**
     * Creates new form PanelSim
     */
    public static int tipoSim;
    public static PApplet simulacion;
    public PanelSim() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        buttonEmpezar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tipo de simulación: ");

        buttonEmpezar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        buttonEmpezar.setText("Empezar Simulación");
        buttonEmpezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEmpezarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 0, 24)); // NOI18N
        jLabel3.setText("Parámetros iniciales de Simulación");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Operacion 3 dias", "Simulacion" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 125, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(124, 124, 124))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jLabel2)
                        .addGap(91, 91, 91)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(buttonEmpezar)))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(121, 121, 121)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(buttonEmpezar)
                .addGap(97, 97, 97))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEmpezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEmpezarActionPerformed
        // TODO add your handling code here:
        if(this.jComboBox1.getSelectedIndex()==0)
            tipoSim=0;
        else 
            tipoSim=1;
        VentanaPrincipal topFrame = (VentanaPrincipal) SwingUtilities.getWindowAncestor(this);
        topFrame.remove(topFrame.pnlFrente);
        topFrame.pnlFondo.removeAll();
        //panelEjecSimu pse=new panelEjecSimu(tipoSim);
        //topFrame.pnlFondo.add(pse,BorderLayout.CENTER);
        
        PApplet mapa = new Mapa();
        
        this.setSimulacion(mapa);
        //topFrame.pnlFondo.add(mapa);
        mapa.init();
        
        panelEjecSimu pse=new panelEjecSimu(tipoSim);
        topFrame.pnlFondo.add(pse,BorderLayout.CENTER);
        
        this.setVisible(false);
        topFrame.revalidate();
        topFrame.repaint();
    }//GEN-LAST:event_buttonEmpezarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEmpezar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the simulacion
     */
    public PApplet getSimulacion() {
        return simulacion;
    }

    /**
     * @param simulacion the simulacion to set
     */
    public void setSimulacion(PApplet simulacion) {
        this.simulacion = simulacion;
    }
}
