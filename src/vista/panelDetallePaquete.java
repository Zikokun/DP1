/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Paquete;
import utilitario.funcionesPanelDetallePaquete;

/**
 *
 * @author a20090245
 */
public class panelDetallePaquete extends javax.swing.JPanel {
    private String usuario;
    private String contrasenha;
    private String tipoUsuario;
    
    public panelDetallePaquete() {
        initComponents();
    }
    
    public panelDetallePaquete(String numeroRastreo, String usuario, String contrasenha, String tipoUsuario) throws InstantiationException, IllegalAccessException {
        initComponents();
        this.setVisible(true);
        
        this.usuario = usuario;
        this.contrasenha = contrasenha;
        this.tipoUsuario = tipoUsuario;
        
        funcionesPanelDetallePaquete fuDetallePaquete = new funcionesPanelDetallePaquete();
        Paquete paquete = fuDetallePaquete.devolverDescripcionGeneralPaquete(numeroRastreo);
        
        this.tituloRastreoLabel.setText("Detalle de envio: " + numeroRastreo);
        this.lugarOrigenTextField.setText(paquete.getAlmacenOrigen().getCiudad());
        this.lugarDestinoTextField.setText(paquete.getAlamcenDestino().getCiudad());
        String sFechaEnvio = fuDetallePaquete.convertirStringFecha(paquete.getFechaEnvio());
        String sFechaRecepcion = fuDetallePaquete.convertirStringFecha(paquete.getFechaRecepcion());
        this.fechaSalidaTextField.setText(sFechaEnvio);
        this.fechaLlegadaTextField.setText(sFechaRecepcion);
        this.estadoPaqueteTextField.setText(fuDetallePaquete.devolverEstadoPaquete(paquete.getEstado()));
        this.DesciprcionTextField.setText(paquete.getDescripcion());
        
        fuDetallePaquete.colocarCampoComoNoEditable(lugarOrigenTextField);
        fuDetallePaquete.colocarCampoComoNoEditable(lugarDestinoTextField);
        fuDetallePaquete.colocarCampoComoNoEditable(fechaSalidaTextField);
        fuDetallePaquete.colocarCampoComoNoEditable(fechaLlegadaTextField);
        fuDetallePaquete.colocarCampoComoNoEditable(estadoPaqueteTextField);
        fuDetallePaquete.colocarCampoComoNoEditable(DesciprcionTextField);
        
        List<String[]> listadoPaquetes = fuDetallePaquete.devolverDetallePaquete(numeroRastreo);
        
        fuDetallePaquete.mostrarDetallePaquete(listadoPaquetes,this.detallePaqueteTabla);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        detallePaqueteTabla = new javax.swing.JTable();
        panelDatosPaquete = new javax.swing.JPanel();
        lugarOrigenLabel = new javax.swing.JLabel();
        lugarOrigenTextField = new javax.swing.JTextField();
        lugarDestinoLabel = new javax.swing.JLabel();
        lugarDestinoTextField = new javax.swing.JTextField();
        fechaSalidaLabel = new javax.swing.JLabel();
        fechaSalidaTextField = new javax.swing.JTextField();
        fechaLlegadaLabel = new javax.swing.JLabel();
        fechaLlegadaTextField = new javax.swing.JTextField();
        estadoLabel = new javax.swing.JLabel();
        estadoPaqueteTextField = new javax.swing.JTextField();
        descripcionLabel = new javax.swing.JLabel();
        DesciprcionTextField = new javax.swing.JTextField();
        regresarBoton = new javax.swing.JButton();
        tituloRastreoLabel = new javax.swing.JLabel();

        detallePaqueteTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro de Rastreo", "Estado", "Destinatario", "País Destino", "Detalle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(detallePaqueteTabla);

        panelDatosPaquete.setBackground(new java.awt.Color(193, 189, 189));

        lugarOrigenLabel.setText("Lugar de Origen:");

        lugarDestinoLabel.setText("Lugar de Destino:");

        fechaSalidaLabel.setText("Fecha de Salida:");

        fechaLlegadaLabel.setText("Fecha de Llegada:");

        estadoLabel.setText("Estado:");

        descripcionLabel.setText("Descripción:");

        javax.swing.GroupLayout panelDatosPaqueteLayout = new javax.swing.GroupLayout(panelDatosPaquete);
        panelDatosPaquete.setLayout(panelDatosPaqueteLayout);
        panelDatosPaqueteLayout.setHorizontalGroup(
            panelDatosPaqueteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPaqueteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDatosPaqueteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDatosPaqueteLayout.createSequentialGroup()
                        .addGroup(panelDatosPaqueteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lugarDestinoLabel)
                            .addComponent(fechaSalidaLabel)
                            .addGroup(panelDatosPaqueteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelDatosPaqueteLayout.createSequentialGroup()
                                    .addComponent(fechaLlegadaLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(fechaLlegadaTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(fechaSalidaTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lugarDestinoTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lugarOrigenTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(estadoPaqueteTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(DesciprcionTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(estadoLabel))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelDatosPaqueteLayout.createSequentialGroup()
                        .addGroup(panelDatosPaqueteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lugarOrigenLabel)
                            .addComponent(descripcionLabel))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelDatosPaqueteLayout.setVerticalGroup(
            panelDatosPaqueteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosPaqueteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lugarOrigenLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lugarOrigenTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lugarDestinoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lugarDestinoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechaSalidaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaSalidaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fechaLlegadaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaLlegadaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(estadoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(estadoPaqueteTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(descripcionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DesciprcionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                .addContainerGap())
        );

        regresarBoton.setText("Regresar");
        regresarBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regresarBotonMouseClicked(evt);
            }
        });

        tituloRastreoLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelDatosPaquete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(tituloRastreoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(regresarBoton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tituloRastreoLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(regresarBoton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelDatosPaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void regresarBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regresarBotonMouseClicked
        // TODO add your handling code here:
        panelPaqueteBusqueda pnlPaqueteBusqueda;
        try {
            pnlPaqueteBusqueda = new panelPaqueteBusqueda(getUsuario(),getContrasenha(),getTipoUsuario());
                    
            VentanaPrincipal.pnlFondo.removeAll();
            VentanaPrincipal.pnlFondo.add(pnlPaqueteBusqueda,BorderLayout.CENTER);
            VentanaPrincipal.pnlFondo.revalidate();
            VentanaPrincipal.pnlFondo.repaint();
        } catch (InstantiationException ex) {
            Logger.getLogger(panelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(panelDetallePaquete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_regresarBotonMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DesciprcionTextField;
    private javax.swing.JLabel descripcionLabel;
    private javax.swing.JTable detallePaqueteTabla;
    private javax.swing.JLabel estadoLabel;
    private javax.swing.JTextField estadoPaqueteTextField;
    private javax.swing.JLabel fechaLlegadaLabel;
    private javax.swing.JTextField fechaLlegadaTextField;
    private javax.swing.JLabel fechaSalidaLabel;
    private javax.swing.JTextField fechaSalidaTextField;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lugarDestinoLabel;
    private javax.swing.JTextField lugarDestinoTextField;
    private javax.swing.JLabel lugarOrigenLabel;
    private javax.swing.JTextField lugarOrigenTextField;
    private javax.swing.JPanel panelDatosPaquete;
    private javax.swing.JButton regresarBoton;
    private javax.swing.JLabel tituloRastreoLabel;
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

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
