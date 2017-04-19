package com.klan.proyecto.web;

import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clase que permite enviar correos al usuario.
 */
@ManagedBean
public class Mensajero {

    private final String correoKLAN = "yumyumciencias@gmail.com"; // Cuenta que envía el correo.
    private final String claveKLAN = "klanpassword"; // Contraseña de la cuenta que envía el correo.
    private String correo = "patlaniunam@ciencias.unam.mx"; // Correo del usuario que se va a registrar.
    private String contraseña = "passwordl"; // Contraseña del usuario que se va a registrar en YumYum Ciencias.
    private String enlace = "localhost:8080/YumYum-Ciencias/Confirmacion.xhtml?code="; // URL sin código
    private String idPendiente = "ejemplo"; // Código que se concatena al enlace enviado.
    private String nombreUsuario = "luis"; // Código que se concatena al enlace enviado.

    public void enviaCorreo() {
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");
        Session session = Session.getInstance(p, new javax.mail.Authenticator() {
            @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoKLAN, claveKLAN);}});
        //session.setDebug(true); // Para ver el proceso de envío.
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoKLAN));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
            message.setSubject("Registro YumYum Ciencias");
            String mensaje = "Estimado usuario:\n\nDe click en el siguiente enlace para confirmar su registro en YumYum Ciencias:"
                    + "\n\n%s\n"
                    + "\n\nDatos Registrados:\n"
                    + "\nNombre de usuario: %s"
                    + "\nContraseña: %s"
                    + "\n\nSaludos,"
                    + "\n\nYumYum Ciencias.";
            String mensaje_correo = String.format(mensaje, enlace + idPendiente, nombreUsuario, contraseña);
            message.setText(mensaje_correo); // Se usa si se va a mandar texto plano.
            // msg.setContent(message, "text/html; charset=utf-8"); // Se usa para mandar texto html (para el enlace).
            Transport.send(message);
            System.out.println("Correo enviado con Éxito!!!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}