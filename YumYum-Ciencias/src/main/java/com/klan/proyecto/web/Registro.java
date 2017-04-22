
package com.klan.proyecto.web;

import com.klan.proyecto.controlador.PendienteC; // Para insertar un nuevo pendiente.
import com.klan.proyecto.controlador.PuestoC;
import com.klan.proyecto.controlador.UsuarioC;
import com.klan.proyecto.modelo.Pendiente; // Para construir un usuario pendiente.
import com.klan.proyecto.modelo.Usuario;

import javax.faces.application.FacesMessage; // Para mostrar y obtener mensajes de avisos.
import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.persistence.EntityManagerFactory; // Para conectarse a la BD.
import javax.persistence.Persistence; // Para definir los parámetros de conexión a la BD.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.
import javax.faces.context.FacesContext;

import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * Bean utilizado para pruebas al perfil de un puesto.
 * @author nancy
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class Registro implements Serializable{

    private int id_usuario;
    private String nombre_usuario;
    private String correo;
    private String contraseña;
    private String confirmaContraseña;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    
    
    private final String correoKLAN = "yumyumciencias@gmail.com"; // Cuenta que envía el correo.
    private final String claveKLAN = "klanpassword"; // Contraseña de la cuenta que envía el correo.
    //private String correo = "patlaniunam@ciencias.unam.mx"; // Correo del usuario que se va a registrar.
    //private String contraseña = "passwordl"; // Contraseña del usuario que se va a registrar en YumYum Ciencias.
   private String enlace = "http://localhost:8080/YumYum-Ciencias/Confirmacion.xhtml?code=";
    //private String idPendiente = "ejemplo"; // Código que se concatena al enlace enviado.
    //private String nombreUsuario = "luis"; // Código que se concatena al enlace enviado.



  
     /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Registro() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();  
        if (httpServletRequest.getSession().getAttribute("registro") != null) {
            Pendiente usuario = ((Pendiente)httpServletRequest.getSession().getAttribute("registro"));
            id_usuario = (int) usuario.getIdUsuario();
            nombre_usuario = usuario.getNombreUsuario();
            correo = usuario.getCorreo();
            contraseña = usuario.getContraseña();
        }

    }

    public String getConfirmaContraseña() {
        return confirmaContraseña;
    }

    public void setConfirmaContraseña(String confirmaContraseña) {
        this.confirmaContraseña = confirmaContraseña;
    }

    /**
     * @return the id_usuario
     */
    public int getId_usuario() {
        return id_usuario;
    }

    /**
     * @param id_usuario the id_usuario to set
     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /**
     * @return the nombre_usuario
     */
    public String getNombre_usuario() {
        return nombre_usuario;
    }

    /**
     * @param nombre_usuario the nombre_usuario to set
     */
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    /**
     * Método para registrar un nuevo usuario en la tabla Pendientes
     */
    public String registrar(){
        //Realizamos la conexión a la BD
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");  //Realiza la conexión a la BD 
        //Obtenemos el último id de la tabla Pendiente y le sumamos uno para que sea el id del usuario que se esta registrando
        id_usuario = new PendienteC(emf).getPendienteCount() + 1;
        //A lo que el usuario ingresó como nombre de usuario, le agregamos la cadena "@ciencias.unam.mx" para que sea un correo válido
        correo = correo + "@ciencias.unam.mx";
        //Creamos un objeto de la clase pendiente que realizará las operaciones en la BD (crear, actualizar o borrar)
        PendienteC controlador = new PendienteC(emf);
        //Creamos un objeto de la clase pediente con todos los datos obtenidos del formulario, los cuales serán guardados en la BD
        Pendiente usuario = new Pendiente(nombre_usuario, id_usuario, correo, contraseña);
        //Creamos un objeto de la clase Properties y le agregamos las propiedades necesarias que se deben tener para el envío del correo.
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
            String mensaje_correo = String.format(mensaje, enlace + id_usuario , nombre_usuario , contraseña);
            message.setText(mensaje_correo); 
            Transport.send(message);
            System.out.println("Correo enviado con Éxito!!!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
        try{
            controlador.create(usuario);
        }catch(Exception e){
            System.out.println("Error al Registrar");
            
        }
        return "mensajeCorreo";
    }
}
