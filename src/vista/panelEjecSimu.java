/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;
import mapa.Mapa;
import modelo.Cromosoma;
import modelo.Gen;
import modelo.Ruta;
import modelo.Vuelo;
import static test1_gui_alg.Test1_gui_alg.principal;
import utilitario.funcionesAnimacionEjecSimu;
import utilitario.funcionesBaseDeDatos;
import utilitario.funcionesDibujoEjecSimu;
import utilitario.funcionesHiloEjecSimu;
import utilitario.funcionesPanelCrearEnvio;
import utilitario.funcionesVentanaPrincipal;
/**
 *
 * @author FranciscoMartin
 */
public class panelEjecSimu extends javax.swing.JPanel {
    public BufferedImage mapaFondo=null;
    public funcionesDibujoEjecSimu Dib;
    public ArrayList<funcionesDibujoEjecSimu> ADib=new ArrayList<funcionesDibujoEjecSimu>();    
    public funcionesAnimacionEjecSimu Anim;
    public String almacen;
    public Cromosoma rutas;//contenedor de las rutas existentes
    //this.txtaLog
    //((DefaultCaret)  txtaLog.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    /**
     * Creates new form panelEjecSimu
     */
    
    public panelEjecSimu(int tipo) throws InstantiationException, IllegalAccessException, SQLException  {
      if(tipo==0){
            /////////////Cromosoma de prueba///////
            Vuelo tmpV1=new Vuelo(0,0,0,0,"Lima","Sao Paulo");
            Vuelo tmpV2=new Vuelo(0,0,0,0,"Sao Paulo","Chile");
             Vuelo tmpV3=new Vuelo(0,0,0,0,"Lima","Chile");
            Ruta tmpRuta1=new Ruta(tmpV1,0);
            Ruta tmpRuta2=new Ruta(tmpV2,0);
            Ruta tmpRuta3=new Ruta(tmpV3,0);
            Gen tmpGen=new Gen();
            Gen tmpGen2=new Gen();
            Gen tmpGen3=new Gen();
            tmpGen.setRuta(tmpRuta1);
            tmpGen2.setRuta(tmpRuta2);
            tmpGen3.setRuta(tmpRuta3);
            rutas=new Cromosoma();
            rutas.genes.add(tmpGen);
            rutas.genes.add(tmpGen2);
            rutas.genes.add(tmpGen3);
            ///////////////////////////////////////
      }else{
            /////////////Cromosoma de prueba///////
          Vuelo tmpV1=new Vuelo(0,0,0,0,"Sao Paulo","Lima");
          Vuelo tmpV2=new Vuelo(0,0,0,0,"Chile","Sao Paulo");
           Vuelo tmpV3=new Vuelo(0,0,0,0,"Chile","Lima");
          Ruta tmpRuta1=new Ruta(tmpV1,0);
          Ruta tmpRuta2=new Ruta(tmpV2,0);
          Ruta tmpRuta3=new Ruta(tmpV3,0);
          Gen tmpGen=new Gen();
          Gen tmpGen2=new Gen();
          Gen tmpGen3=new Gen();
          tmpGen.setRuta(tmpRuta1);
          tmpGen2.setRuta(tmpRuta2);
          tmpGen3.setRuta(tmpRuta3);
          rutas=new Cromosoma();
          rutas.genes.add(tmpGen);
          rutas.genes.add(tmpGen2);
          rutas.genes.add(tmpGen3);
          ///////////////////////////////////////
      }
        initComponents();
        int xIni,yIni,xFin,yFin,xInt,yInt;
        String Destino,Inicio,Intermedio;
        funcionesDibujoEjecSimu tmpDib;
        try {                
          mapaFondo = ImageIO.read(new File("src/recursos/map.jpg"));
        } catch (IOException ex) {
            System.out.println("error con imagen");
        }
          
        /*JLabel picLabel = new JLabel(new ImageIcon(mapaFondo));
        //JLabel picLabel = new JLabel();
        Mapa mapa = new Mapa();
        picLabel.add(mapa);
        mapa.init();*/
        //JLabel picLabel = new JLabel(new ImageIcon(mapaFondo));
        //JLabel picLabel = new JLabel();
        //Mapa mapa = new Mapa();
        panelMapa.add(PanelSim.simulacion);
        
        //mapa.init();
       
       this.setVisible(true);
             
       for(int i=0;i<(this.rutas.genes.size());i++){
          ArrayList<Vuelo>tmpVuelo=this.rutas.genes.get(i).getRuta().getVuelos();
          if(tmpVuelo.size()==1){ 
            Destino=tmpVuelo.get(0).getDestino();
            Inicio=tmpVuelo.get(0).getOrigen();
          }else{
            Destino=tmpVuelo.get(1).getDestino();
            Inicio=tmpVuelo.get(0).getOrigen();
            Intermedio=tmpVuelo.get(1).getOrigen();
          }
          xIni=this.getPosicionX(Inicio);
          yIni=this.getPosicionY(Inicio);
          xFin=this.getPosicionX(Destino);
          yFin=this.getPosicionY(Destino);
          System.out.print(xIni+" " +yIni+" " +xFin+" " +yFin);
          System.out.println();
          tmpDib=new funcionesDibujoEjecSimu(xIni,yIni,xFin,yFin,3);
          ADib.add(tmpDib);
       }
       
       //Dib = new funcionesDibujoEjecSimu(175,300,227,227,3);
       //Anim = new funcionesAnimacionEjecSimu(this, 20, Dib);
       Anim = new funcionesAnimacionEjecSimu(this, 20, ADib);
    }
    
     public int Escribir(String mensaje){
        this.logMensajesPanel.append(mensaje + "\n");
        return 0;
     }
     
     public int getPosicionX(String origen){
           if(origen.compareTo("Lima")==0)
                return 190;
           if(origen.compareTo("Sao Paulo")==0)
             return 195;
           if(origen.compareTo("Chile")==0)
            return 210;
           return 0;
     }
     public int getPosicionY(String origen){
          if(origen.compareTo("Lima")==0)
            return 450;
           if(origen.compareTo("Sao Paulo")==0)
            return 500;
           if(origen.compareTo("Chile")==0)
            return 450;
           return 0;
     }
     public void actualizaDisplay(){
        revalidate();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
            
            //g.drawImage(mapaFondo, 0, 0, this.getWidth()-this.panelLog.getWidth(), this.getHeight(), this);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLog = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logMensajesPanel = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelMapa = new javax.swing.JPanel();

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        logMensajesPanel.setColumns(20);
        logMensajesPanel.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        logMensajesPanel.setRows(5);
        logMensajesPanel.setEnabled(false);
        jScrollPane1.setViewportView(logMensajesPanel);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setText("    0 a 200 paquetes ");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("200 a 400 paquetes");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 51));
        jLabel3.setText("400 a 600 paquetes");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Leyenda:");

        javax.swing.GroupLayout panelLogLayout = new javax.swing.GroupLayout(panelLog);
        panelLog.setLayout(panelLogLayout);
        panelLogLayout.setHorizontalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLogLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLogLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLogLayout.setVerticalGroup(
            panelLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLogLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(22, 22, 22))
        );

        panelMapa.setEnabled(false);

        javax.swing.GroupLayout panelMapaLayout = new javax.swing.GroupLayout(panelMapa);
        panelMapa.setLayout(panelMapaLayout);
        panelMapaLayout.setHorizontalGroup(
            panelMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );
        panelMapaLayout.setVerticalGroup(
            panelMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(panelLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelLog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
//            String mensaje = "";
//                try {
//                    mensaje = principal();
//                    System.out.println("JL"+mensaje);
//                } catch (InstantiationException ex) {
//                    Logger.getLogger(panelEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(panelEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                this.logMensajesPanel.setText(mensaje);
            if(Anim.EstaCorriendo()){
                //this.logMensajesPanel.repaint();
                Anim.Detener();
            }else
                Anim.Iniciar();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea logMensajesPanel;
    private javax.swing.JPanel panelLog;
    private javax.swing.JPanel panelMapa;
    // End of variables declaration//GEN-END:variables
}
