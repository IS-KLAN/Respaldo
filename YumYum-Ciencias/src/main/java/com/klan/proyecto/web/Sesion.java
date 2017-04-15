/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import com.klan.proyecto.controlador.UsuarioC; // Para consultar usuarios de la BD.
import com.klan.proyecto.modelo.Usuario; // Para construir un usuario.

import javax.faces.application.FacesMessage; // Para mostrar y obtener mensajes de avisos.
import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.persistence.EntityManagerFactory; // Para conectarse a la BD.
import javax.persistence.Persistence; // Para definir los parámetros de conexión a la BD.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.

/**
 *
 * Bean utilizado para pruebas al perfil de un puesto.
 * @author patlani
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class Sesion implements Serializable{

    private String correo;
    private String contraseña;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de correo.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;

    /**
     * Constructor para inicializar los valores de faceContext y
     * httpServletRequest.
     */
    public Sesion() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    /**
     * Obtiene el nombre de correo.
     *
     * @return El nombre de correo.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el nombre de correo.
     *
     * @param correo El nombre de correo a establecer.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Regresa la contraseña ingresada.
     *
     * @return La contraseña ingresada.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña.
     *
     * @param contraseña La contraseña que ingresa el usuario.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Método encargado de iniciar los datos ingresados.
     * @return El nombre de la vista que va a responder.
     */
    public String iniciar() {
        // Se realiza la conexión a la BD.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        // Se busca al usuario por correo.
        Usuario usuario = new UsuarioC(emf).findByCorreo(correo);
        // Se verifica que el usuario exista y que haya ingresado la contraseña corrrecta.
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            //System.out.println("Iniciando la sesión de " + usuario.getNombreUsuario());
            httpServletRequest.getSession().setAttribute("usuario", usuario); // Se guarda al usuario.
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
            faceContext.addMessage(null, message);
            return "index";
        } // Se informa el error si ha ocurrido algún error.
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
        faceContext.addMessage(null, message);
        return "ingresoUsuario";
    }

    /**
     * Método encargado de cerrar la sesión.
     */
    public void cerrar() {
            //System.out.println("Cerrando la sesión.");
            httpServletRequest.getSession().removeAttribute("usuario"); // Se borra al usuario.
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Salida satisfactoria.", null);
            faceContext.addMessage(null, message); // Se agrega el mensaje.
            // Se asegura que el mensaje se muestre después de la redirección.
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
    }

    /**
     * Método que define las opciones disponibles de la barra de navegación.
     * @return Devuelve el xhtml que contiene las opciones correspondientes.
     */
    public String opcionesDisponibles() {
        if (httpServletRequest.getSession().getAttribute("usuario") != null) return "opcionesUsuario";
        return "opcionesInvitado";
    }

    /**
     * Método que indica si se tiene una sesión activa.
     * @return Devuelve true si hay una sesión iniciada, o falso en otro caso.
     */
    public boolean iniciada() {
        return httpServletRequest.getSession().getAttribute("usuario") != null;
    }

    /**
     * Método que indica el nombre del usuario que se encuentre activo.
     * @return Devuelve el nombre del usuario activo o NULL en otro caso.
     */
    public String usuarioActivo() {
        Usuario u = ((Usuario)httpServletRequest.getSession().getAttribute("usuario"));
        return (u != null)? u.getNombreUsuario() : null;
    }
}