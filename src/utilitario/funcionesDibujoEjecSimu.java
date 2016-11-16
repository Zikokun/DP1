/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 *
 * @author FranciscoMartin
 */
public class funcionesDibujoEjecSimu {
    
	private int CentroX, CentroY;
	private int LimX, LimY;//posicion final a la que se desea llegar
	private int Radio;
	private int DespX, DespY;
        private int difX,difY;
        private int delta;
        private BufferedImage img;
        private JLabel imgLabel;
	public funcionesDibujoEjecSimu(int InicioX,  int InicioY,int LimX, int LimY, int Radio) {
             try {
                img =ImageIO.read(new File("src/recursos/avion.png"));
            } catch (IOException ex) {
                Logger.getLogger(funcionesDibujoEjecSimu.class.getName()).log(Level.SEVERE, null, ex);
            }
             imgLabel=new JLabel(new ImageIcon(getImg()));
		CentroX = InicioX;
		CentroY = InicioY;
		this.LimX = LimX;
		this.LimY = LimY;
		this.Radio = Radio;
                difX=LimX-InicioX;
                difY=LimY-InicioY;
                difX=difX<<1;
                difY=difY<<1;
                DespX=InicioX<LimX?1:-1;
                DespY=InicioY<LimY?1:-1;
                delta=0;
//		double Direccion = (Math.atan2((LimY-InicioY),(LimX-InicioX)) * 360) * 3.1416 / 180;//direccion inicial
//		double Magnitud = 5.0;
//                
//		DespX = Math.cos(Direccion) * Magnitud;
//		DespY = Math.sin(Direccion) * Magnitud;
	}
	public void Mover() {
//            if(getCentroX() < Radio || getCentroX() > (LimX - Radio))
//			DespX *= -1;
//		if(getCentroY() < Radio || getCentroY() > (LimY - Radio))
//			DespY *= -1;
//                if(LimX-CentroX<=Radio && LimY-CentroY<=Radio){
//                    DespX *= 0;
//                    DespY *= 0;
//                }
//		setCentroX(getCentroX() + DespX);
//		setCentroY(getCentroY() + DespY);
                if(difY<=difX){
                    if(CentroX==LimX){
                        DespX=0;
                        DespY=0;
                    }else{
                        setCentroX(getCentroX() + DespX);
                        delta+=difY;
                        if(delta>difX){
                            setCentroY(getCentroY() + DespY);
                            delta-=difX;
                        }
                    }
                }else{
                    if(CentroY==LimY){
                        DespX=0;
                        DespY=0;
                    }else{
                        setCentroY(getCentroY() + DespY);
                        delta+=difX;
                        if(delta>difY){
                            setCentroX(getCentroX() + DespX);
                            delta-=difY;
                        }
                    }
                    
                }
	}
	public void Dibujar(JPanel pnl,Graphics g) {
                this.getImgLabel().setLocation(getCentroX(), getCentroY());
                this.getImgLabel().setVisible(true);
                pnl.add(this.imgLabel);
                g.drawImage(getImg(), getCentroX(), getCentroY(), null);
		//g.drawOval((int)(getCentroX() - Radio), (int)(getCentroY() - Radio), (int)(2 * Radio), (int)(2 * Radio));
	}

    /**
     * @return the LimX
     */
    public int getLimX() {
        return LimX;
    }

    /**
     * @param LimX the LimX to set
     */
    public void setLimX(int LimX) {
        this.LimX = LimX;
    }

    /**
     * @return the LimY
     */
    public int getLimY() {
        return LimY;
    }

    /**
     * @param LimY the LimY to set
     */
    public void setLimY(int LimY) {
        this.LimY = LimY;
    }

    /**
     * @return the CentroX
     */
    public int getCentroX() {
        return CentroX;
    }

    /**
     * @param CentroX the CentroX to set
     */
    public void setCentroX(int CentroX) {
        this.CentroX = CentroX;
    }

    /**
     * @return the CentroY
     */
    public int getCentroY() {
        return CentroY;
    }

    /**
     * @param CentroY the CentroY to set
     */
    public void setCentroY(int CentroY) {
        this.CentroY = CentroY;
    }
    public String[] devolverDatosAlmacenes() throws InstantiationException, IllegalAccessException{
        funcionesBaseDeDatos cc = new funcionesBaseDeDatos();
        Connection conexion = cc.conexion();
        
        String sqlBuscarAlmacenes = "";
        String[] datosAlmacen = new String[10];
        int cont=0;
        
        sqlBuscarAlmacenes = "SELECT ubicacion " + " FROM `almacen` ";
        
        try {
            Statement st = conexion.createStatement();
            ResultSet resultadoBuscarAlmacen = st.executeQuery(sqlBuscarAlmacenes);
            
            while(resultadoBuscarAlmacen!=null && resultadoBuscarAlmacen.next()){
                datosAlmacen[cont] = resultadoBuscarAlmacen.getString(1); //Ciudad(ubicacion)
                cont++;
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(funcionesVentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return datosAlmacen;
    }
    /**
     * @return the img
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(BufferedImage img) {
        this.img = img;
    }

    /**
     * @return the imgLabel
     */
    public JLabel getImgLabel() {
        return imgLabel;
    }

    /**
     * @param imgLabel the imgLabel to set
     */
    public void setImgLabel(JLabel imgLabel) {
        this.imgLabel = imgLabel;
    }
}
