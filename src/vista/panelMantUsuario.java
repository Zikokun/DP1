/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import constantes.constantesVentanaPrincipal;
import static constantes.constantesVentanaPrincipal.*;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Persona;
import utilitario.funcionesPanelMantUsuario;

/**
 *
 * @author FranciscoMartin
 */
public class panelMantUsuario extends javax.swing.JPanel {
    private String usuario;
    private String contrasenha;
    private String tipoUsuario;
    
    public panelMantUsuario() {
        initComponents();
        this.setVisible(true);
    }
    
    public panelMantUsuario(String usuario, String contrasenha,String tipoUsuario, int distinguidor) {
        this.usuario = usuario;
        this.contrasenha = contrasenha;
        this.tipoUsuario = tipoUsuario;
        initComponents();
        this.setVisible(true);
    }
    
    private int CamposObligatorios() throws InstantiationException, IllegalAccessException{
        int cont=0;
        String cadena="Ingresar el:";
        if(nombreCampo.getText().isEmpty()) {
            cadena+= "\n Contraseña";
            cont++;
        }
        if(apellidoPaternoCampo.getText().isEmpty()) {
            cadena+= "\n Apellido Paterno";
            cont++;
        }
        if(apellidoMaternoCampo.getText().isEmpty()){
            cadena+= "\n Apellido Materno";
            cont++;
        }
        if(fechaNacimientoCampo.getText().isEmpty()) {
            cadena+= "\n Fecha de Nacimiento";
            cont++;
        }
        if(direccionCampo.getText().isEmpty()) {
            cadena+= "\n Dirección";
            cont++;
        }
        if(correoCampo.getText().isEmpty()) {
            cadena+= "\n Correo";
            cont++;
        }
        if(docIdentidadCampo.getText().isEmpty()) {
            cadena+= "\n Documento de Identidad";
            cont++;
        }
        if(usuarioCampo.getText().isEmpty()) {
            cadena+= "\n Nombre de Usuario";
            cont++;
        }
        if(contrasenhaCampo.getText().isEmpty()) {
            cadena+= "\n Contraseña";
            cont++;
        }
        if(cont>0)JOptionPane.showMessageDialog(this, cadena, "Campo Obligatorio", JOptionPane.INFORMATION_MESSAGE);
        return cont;
    }

    public panelMantUsuario(String usuario, String contrasenha,String tipoUsuario) throws InstantiationException, IllegalAccessException {
                
        this.usuario = usuario;
        this.contrasenha = contrasenha;
        this.tipoUsuario = tipoUsuario;
        System.out.println(this.tipoUsuario);
        initComponents();
        this.setVisible(true);
        String[] datosUsuario = new String[10];
        
        funcionesPanelMantUsuario funcionesMantUsuario = new funcionesPanelMantUsuario();

        this.botonRegistrar.setVisible(false);
        datosUsuario = funcionesMantUsuario.devolverDatosUsuario(usuario, tipoUsuario);
        this.nombreCampo.setText(datosUsuario[0]);
        this.apellidoPaternoCampo.setText(datosUsuario[1]);
        this.apellidoMaternoCampo.setText(datosUsuario[2]);
        this.fechaNacimientoCampo.setText(datosUsuario[3]);
        this.direccionCampo.setText(datosUsuario[4]);
        this.correoCampo.setText(datosUsuario[5]);
        this.docIdentidadCampo.setText(datosUsuario[6]);
            
        if(datosUsuario[7].equals(TIPO_CLIENTE)) this.tipoUsuarioComboBox.setSelectedIndex(2);
        else if(datosUsuario[7].equals(TIPO_ADMIN)) this.tipoUsuarioComboBox.setSelectedIndex(0);
        else this.tipoUsuarioComboBox.setSelectedIndex(1); //Operario
            
        this.docIdentidadComboBox.setSelectedIndex(0);
            
        this.usuarioCampo.setText(datosUsuario[9]);
            
        funcionesMantUsuario.colocarCampoComoNoEditable(this.nombreCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.apellidoPaternoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.apellidoMaternoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.fechaNacimientoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.direccionCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.correoCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.docIdentidadCampo);
        funcionesMantUsuario.colocarCampoComoNoEditable(this.usuarioCampo);
            
        funcionesMantUsuario.colocarComboBoxesComoNoEditable(this.tipoUsuarioComboBox);
        funcionesMantUsuario.colocarComboBoxesComoNoEditable(this.docIdentidadComboBox);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nombreCampo = new javax.swing.JTextField();
        apellidoPaternoCampo = new javax.swing.JTextField();
        apellidoMaternoCampo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fechaNacimientoCampo = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        tipoUsuarioComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        direccionCampo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        correoCampo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        docIdentidadComboBox = new javax.swing.JComboBox<>();
        docIdentidadCampo = new javax.swing.JTextField();
        botonRegistrar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        usuarioCampo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        contrasenhaCampo = new javax.swing.JTextField();
        regresarBoton = new javax.swing.JButton();

        jTextField2.setText("jTextField2");

        jLabel6.setText("jLabel6");

        jTextField7.setText("jTextField7");

        jTextField9.setText("jTextField9");

        jLabel1.setText("Nombre:");

        jLabel2.setText("Apellido Paterno");

        jLabel3.setText("Apellido Materno");

        nombreCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nombreCampoKeyTyped(evt);
            }
        });

        apellidoPaternoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                apellidoPaternoCampoKeyTyped(evt);
            }
        });

        apellidoMaternoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                apellidoMaternoCampoKeyTyped(evt);
            }
        });

        jLabel4.setText("Fecha de nacimiento");

        fechaNacimientoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fechaNacimientoCampoKeyTyped(evt);
            }
        });

        jLabel5.setText("Tipo usuario");

        tipoUsuarioComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Operario", "Cliente" }));
        tipoUsuarioComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipoUsuarioComboBoxActionPerformed(evt);
            }
        });

        jLabel7.setText("Direccion");

        direccionCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                direccionCampoKeyTyped(evt);
            }
        });

        jLabel8.setText("Correo");

        correoCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                correoCampoKeyTyped(evt);
            }
        });

        jLabel9.setText("Documento de identidad");

        docIdentidadComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "LE", "CE" }));

        docIdentidadCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                docIdentidadCampoKeyTyped(evt);
            }
        });

        botonRegistrar.setText("Registrar");
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });

        jLabel10.setText("Usuario");

        usuarioCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usuarioCampoKeyTyped(evt);
            }
        });

        jLabel11.setText("Contraseña");

        contrasenhaCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                contrasenhaCampoKeyTyped(evt);
            }
        });

        regresarBoton.setText("Regresar");
        regresarBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regresarBotonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(tipoUsuarioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(docIdentidadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(usuarioCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11)
                                    .addComponent(contrasenhaCampo, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(docIdentidadCampo)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(fechaNacimientoCampo, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(correoCampo)
                            .addComponent(direccionCampo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonRegistrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(regresarBoton))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nombreCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(apellidoPaternoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(apellidoMaternoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellidoPaternoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellidoMaternoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechaNacimientoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccionCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoUsuarioComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(correoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(docIdentidadComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(docIdentidadCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonRegistrar)
                            .addComponent(regresarBoton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usuarioCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contrasenhaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tipoUsuarioComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipoUsuarioComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipoUsuarioComboBoxActionPerformed

    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
        try {
            if (CamposObligatorios() == 0) {
                String nombre = nombreCampo.getText();
                String apellidoP = apellidoPaternoCampo.getText();
                String apellidoM = apellidoMaternoCampo.getText();
                String fechaNac = fechaNacimientoCampo.getText();
                String direccion = direccionCampo.getText();
                String correo = correoCampo.getText();
                String documento = docIdentidadCampo.getText();
                String usuario = usuarioCampo.getText();
                String contrasenhia = contrasenhaCampo.getText();
                
                String tipoUsuario = (String) this.tipoUsuarioComboBox.getSelectedItem();
                String tipoDocumento = "DNI";
                
                Persona usuarioNuevo = new Persona(nombre, apellidoP, apellidoM, fechaNac, direccion, correo, documento, usuario, contrasenhia);
                
                funcionesPanelMantUsuario utilitarioMantenimientos = new funcionesPanelMantUsuario();
                try {
                    String mensaje = utilitarioMantenimientos.RegistrarUsuario(usuarioNuevo, tipoUsuario);
                    JOptionPane.showMessageDialog(null, mensaje);
                } catch (SQLException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (InstantiationException ex) {
            Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(panelMantUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botonRegistrarActionPerformed

    private void regresarBotonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regresarBotonMouseClicked
        VentanaPrincipal.pnlFondo.removeAll();
        
        if(tipoUsuario.equals(TIPO_ADMIN)){
            PanelSim ps=new PanelSim();
            VentanaPrincipal.pnlFondo.add(ps,BorderLayout.CENTER);
        }
        VentanaPrincipal.pnlFondo.revalidate();
        VentanaPrincipal.pnlFondo.repaint();
    }//GEN-LAST:event_regresarBotonMouseClicked

    private void nombreCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreCampoKeyTyped
        char key = evt.getKeyChar();
        if(Character.isDigit(key)){
            evt.consume();
        }
        if(nombreCampo.getText().length()>=30) {  
            evt.consume();
        }
    }//GEN-LAST:event_nombreCampoKeyTyped

    private void apellidoPaternoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoPaternoCampoKeyTyped
        char key = evt.getKeyChar();
        if(Character.isDigit(key)){
            evt.consume();
        }
        if(apellidoPaternoCampo.getText().length()>=30) {  
            evt.consume();
        }
    }//GEN-LAST:event_apellidoPaternoCampoKeyTyped

    private void apellidoMaternoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidoMaternoCampoKeyTyped
        char key = evt.getKeyChar();
        if(Character.isDigit(key)){
            evt.consume();
        }
        if(apellidoMaternoCampo.getText().length()>=30) {  
            evt.consume();
        }
    }//GEN-LAST:event_apellidoMaternoCampoKeyTyped

    private void usuarioCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuarioCampoKeyTyped
        if(usuarioCampo.getText().length()>=30) {  
            evt.consume();
        }
    }//GEN-LAST:event_usuarioCampoKeyTyped

    private void docIdentidadCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_docIdentidadCampoKeyTyped
        if(docIdentidadCampo.getText().length()>=8) {  
            evt.consume();
        }
    }//GEN-LAST:event_docIdentidadCampoKeyTyped

    private void fechaNacimientoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fechaNacimientoCampoKeyTyped
        if(fechaNacimientoCampo.getText().length()>=10) {  
            evt.consume();
        }
    }//GEN-LAST:event_fechaNacimientoCampoKeyTyped

    private void direccionCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_direccionCampoKeyTyped
        if(direccionCampo.getText().length()>=8) {  
            evt.consume();
        }
    }//GEN-LAST:event_direccionCampoKeyTyped

    private void contrasenhaCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contrasenhaCampoKeyTyped
        if(contrasenhaCampo.getText().length()>=8) {  
            evt.consume();
        }
    }//GEN-LAST:event_contrasenhaCampoKeyTyped

    private void correoCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_correoCampoKeyTyped
        if(correoCampo.getText().length()>=10) {  
            evt.consume();
        }
    }//GEN-LAST:event_correoCampoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidoMaternoCampo;
    private javax.swing.JTextField apellidoPaternoCampo;
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JTextField contrasenhaCampo;
    private javax.swing.JTextField correoCampo;
    private javax.swing.JTextField direccionCampo;
    private javax.swing.JTextField docIdentidadCampo;
    private javax.swing.JComboBox<String> docIdentidadComboBox;
    private javax.swing.JFormattedTextField fechaNacimientoCampo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField nombreCampo;
    private javax.swing.JButton regresarBoton;
    private javax.swing.JComboBox<String> tipoUsuarioComboBox;
    private javax.swing.JTextField usuarioCampo;
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
