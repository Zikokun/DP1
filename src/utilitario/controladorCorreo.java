/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitario;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author FranciscoMartin
 */
public class controladorCorreo {
   
    public boolean enviarCorreo(Correo corr){
        try{
            Properties prop=new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.setProperty("mail.smtp.port", "587");
            prop.setProperty("mail.smtp.user", corr.getUsuarioCorreo());
            prop.setProperty("mail.smtp.auth", "true");
            
            Session sess=Session.getDefaultInstance(prop,null);
            BodyPart texto= new MimeBodyPart();
            texto.setText(corr.getMensaje());
            BodyPart adjunto= new MimeBodyPart();
            if(!corr.getRutaArch().equals("")){
                adjunto.setDataHandler(new DataHandler(new FileDataSource(corr.getRutaArch())));
                adjunto.setFileName(corr.getNombArch());
            }
            MimeMultipart mult=new MimeMultipart();
            mult.addBodyPart(texto);
            
            if(!corr.getRutaArch().equals("")){
                mult.addBodyPart(adjunto);
            }
            MimeMessage mensaje=new MimeMessage(sess);
            mensaje.setFrom(new InternetAddress(corr.getUsuarioCorreo()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(corr.getDestino()));
            mensaje.setSubject(corr.getAsunto());
            mensaje.setContent(mult);
            
            Transport trans=sess.getTransport("smtp");
            trans.connect(corr.getUsuarioCorreo(),corr.getContrasenha());
            trans.sendMessage(mensaje, mensaje.getAllRecipients());
            trans.close();
            return true;
            
            
                
        }catch(Exception e){
            System.out.println("error en mail:" +e);
        }
        return false;
    }
}
