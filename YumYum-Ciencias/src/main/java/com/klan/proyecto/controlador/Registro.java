/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.jpa.PendienteJPA; // Para insertar un nuevo pendiente.
import com.klan.proyecto.jpa.UsuarioJPA;
import com.klan.proyecto.modelo.Pendiente; // Para construir un usuario pendiente.
import com.klan.proyecto.modelo.Usuario;

import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.persistence.EntityManagerFactory; // Para conectarse a la BD.
import javax.persistence.Persistence; // Para definir los parámetros de conexión a la BD.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.
import java.util.Properties;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Bean utilizado para pruebas al perfil de un puesto.
 * @author nancy
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class Registro implements Serializable{

    private final String correoKLAN = "yumyumciencias@gmail.com"; // Cuenta que envía el correo.
    private final String claveKLAN = "klanpassword"; // Contraseña de la cuenta que envía el correo.
    private final String enlace = "http://localhost:8080/YumYum-Ciencias/confirmacion.xhtml?clave="; // URL sin código
    private String clave; // Código utilizado para identificar al usuario a través de un enlace.
    private String nombreUsuario; // Nombre del usuario que se registra.
    private String correo; // Correo del usuario que se registra.
    private String contraseña; // Contraseña del usuario que se registra.
    private String confirmaContraseña; // Confirmación de contraseña del usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación

    public Registro() {
        faceContext = FacesContext.getCurrentInstance();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getConfirmaContraseña() {
        return confirmaContraseña;
    }

    public void setConfirmaContraseña(String confirmaContraseña) {
        this.confirmaContraseña = confirmaContraseña;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Método que se encarga de enviar un correo de confirmación al usuario.
     * @return Devuelve true si el correo ha sido enviado correctamente, false en otro caso.
     */
    public boolean enviaCorreo() {
        String correociencias = correo + "@ciencias.unam.mx";
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correociencias));
            message.setSubject("Registro YumYum Ciencias");
            String texto = "Estimado usuario:\n\nDe click en el siguiente enlace para confirmar su registro en YumYum Ciencias:"
                    + "\n\n%s\n"
                    + "\n\nDatos Registrados:\n"
                    + "\nNombre de usuario: %s"
                    + "\nContraseña: %s"
                    + "\n\nSaludos,"
                    + "\n\nYumYum Ciencias.";
            String mensaje = String.format(texto, enlace + cifraClave(), nombreUsuario, contraseña);
            message.setText(mensaje); // Se usa si se va a mandar texto plano.
            Transport.send(message);
            System.out.println("Correo enviado a " + correociencias + " con Éxito!!!\n" + mensaje);
            return true;
        } catch (MessagingException e) {
            System.err.println("\n" + e.getMessage());
        } return false;
    }
    
    /**
     * Método para registrar un nuevo usuario en la tabla Pendientes
     * @return Devuelve el nombre de la página del destino correspondiente.
     */
    public String registrar(){
        if (datosVerificados()) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
            PendienteJPA pc = new PendienteJPA(emf);
            Pendiente usuario = new Pendiente(nombreUsuario, correo, contraseña);
            try{ //Realiza la conexión a la BD y se inserta al nuevo usuario de confirmación pendiente.
                pc.crear(usuario);
                if(enviaCorreo()) {
                    faceContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Se le ha enviado un correo para confirmar su registro :)", null));
                    // Se asegura que el mensaje se muestre después de la redirección.
                    faceContext.getExternalContext().getFlash().setKeepMessages(true);
                    return "index?faces-redirect=true";
                }else {
                    faceContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Ha ocurrido un error al enviar el correo, intente más tarde.", null));
                }
            }catch(Exception e){
                faceContext.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Ha ocurrido un error al registrar sus datos, intente más tarde.", null));
                System.err.println("\n" + e.getMessage());
            }
        } return "registro";
    }

    /**
     * Método que genera una clave con el nombre de usuario cifrado y lo guarda en clave.
     * @return Devuelve la cadena cifrada.
     */
    public String cifraClave() {
        clave = ""; // Se inicializa la clave.
        Random r = new Random(); // Se crea una variable aleatoria.
        int n = nombreUsuario.length(); // Se define la longitud del nombre.
        int max = Math.max(10, r.nextInt(5) + n); // Se acota la longitud de la clave de 15 a n + 5.
        System.out.println("Nombre original: " + nombreUsuario);
        for (int i = 0; i < max; i++) { // Se construye la clave de longitud max.
            // Se utiliza el código hexadecimal de la posición correspondiente si no se ha rebasado n.
            if (i < n) clave += Integer.toHexString((int)(nombreUsuario.charAt(i)));
            else clave += (char)(r.nextInt(20) + 71); // Se agrega cualquier letra no hexadecimal para indicar el fin.
            //System.out.println("Clave actual: " + clave);
            clave += (char)(r.nextInt(20) + 71); // Se agrega cualquier letra no hexadecimal para separa letras.
        } System.out.println("Clave cifrada: " + clave);
        return clave;
    }

    /**
     * Método que descrifra una clave para obtener el nombre de usuario que es guardado.
     * @return Devuelve el nombre de usuario descifrado.
     */
    public String descifraClave() {
        nombreUsuario = ""; // Se inicializa el nombre a descifrar.
        Random r = new Random(); // Se crea una variable aleatoria.
        int n = clave.length(); // Se obtiene la longitud de la clave.
        String hex = ""; // Se utiliza una variable para guardar cada hexadecimal.
        System.out.println("Clave original: " + clave);
        for (int c, i = 0; i < n - 1; i++) { // Se descifra cada char de la clave.
            c = (int)(clave.charAt(i)); // Se obtiene el valor del char i.
            //System.out.println("Nombre actual: " + nombreUsuario);
            if (c > 70 && c < 91) { // Si el valor no es hexadecimal se descifra el hexadecimal.
                nombreUsuario += (char)(Integer.parseInt(hex, 16)); // Se concatena la letra obtenida.
                hex = ""; // Se reinicia la cadena auxiliar para guardar el siguiente hexadecimal.
                c = (int)(clave.charAt(i + 1)); // Se revisa el siguiente valor.
                if (c > 70 && c < 91) break; // Dos no hexadecimales juntos indican el fin del nombre.
            } else hex += clave.charAt(i); // Se agrega el siguiente valor hexadecimal.
        } System.out.println("Clave descifrada: " + nombreUsuario);
        return nombreUsuario;
    }

    /**
     * Método que verifica que no existan usuarios con el mismo correo o nombre de usuario.
     * @return Devuelve true si no existe ningún conflicto, false en otro caso.
     */
    public boolean datosVerificados() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        UsuarioJPA uc = new UsuarioJPA(emf); PendienteJPA pc = new PendienteJPA(emf);
        if(pc.buscaId(nombreUsuario) != null || uc.buscaId(nombreUsuario) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Ese nombre de usuario ya ha sido registrado.", null));
            return false;
        }
        if(pc.buscaCorreo(correo) != null || uc.buscaCorreo(correo) != null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Ese correo ya ha sido registrado.", null));
            return false;
        } return true;
    }
    
    /**
     * Método que confirma un registro suponiendo que se ha leido la clave desde un enlace.
     * @return Devuelve el nombre de la página correspondiente según el resultado.
     */
    public String confirmar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        PendienteJPA controlador = new PendienteJPA(emf);
        Pendiente confirmado = controlador.buscaId(descifraClave());
        if(confirmado != null) {
            correo = confirmado.getCorreo();
            contraseña = confirmado.getContraseña();
            try{ // Se crea al nuevo usuario y se borra al usuario pendiente.
                Usuario nuevo = new Usuario(nombreUsuario, correo, contraseña);
                new UsuarioJPA(emf).crear(nuevo); // Se inserta al nuevo usuario en la BD.
                new PendienteJPA(emf).borrar(nombreUsuario); // Se borra de pendiente.
                faceContext.addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "El registro se ha realizado exitosamente :)", null));
                // Se asegura que el mensaje se muestre después de la redirección.
                faceContext.getExternalContext().getFlash().setKeepMessages(true);
                HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
                httpServletRequest.getSession().setAttribute("usuario", nuevo); // Se guarda al usuario.
                return "index?faces-redirect=true";
            } catch(Exception e) {
                System.err.println("Error al insertar al usuario:\n" + e.getMessage());
            }
        } faceContext.addMessage(null, new FacesMessage(
        FacesMessage.SEVERITY_INFO, "Ha ocurrido un error, intente  más tarde :C", null));
        return "confirmacion";
    }
}