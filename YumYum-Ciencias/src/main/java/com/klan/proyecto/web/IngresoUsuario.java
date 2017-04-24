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
 * @author anahiqj
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class IngresoUsuario implements Serializable{

    private String correo;
    private String contraseña;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de correo.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;

    /**
     * Constructor para inicializar los valores de faceContext y
     * httpServletRequest.
     */
    public IngresoUsuario() {
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
     * Método encargado de ingresar los datos ingresados.
     * @return El nombre de la vista que va a responder.
     */
    public String ingresar() {
        // Se realiza la conexión a la BD.
        EntityManagerFactory emf = Persistence.
                createEntityManagerFactory("YumYum-Ciencias");
        // Se busca al usuario por correo.
        Usuario usuario = new UsuarioC(emf).encuentraCorreo(correo
                +"@ciencias.unam.mx");
        //Si es la primera vez que entra, significa que su confirmación se 
        //encuentra pendiente, por lo cual se copia la tupla de pendiente a 
        //un usuario
        if(usuario == null){
            usuario = new UsuarioC(emf).creaUsuarioPendiente(correo
                    +"@ciencias.unam.mx");
        }else{
        //Si se encuentra el usuario y su contraseña coincide, se le da acceso.
            if (usuario.getContraseña().equals(contraseña)) {
                // Se guarda al usuario que se encuentra activo.
                httpServletRequest.getSession().setAttribute("usuario",usuario); 
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Acceso Correcto", null);
                faceContext.addMessage(null, message);
                return "index";
            }
        }// Se informa el error si ha ocurrido algún error.
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Usuario o contraseña incorrecto", null);
        faceContext.addMessage(null, message);
        return "ingresoUsuario";
    }

    /**
     * Método que define las opciones disponibles de la barra de navegación en
     * caso de ser un Usuario.
     * @return Devuelve el xhtml que contiene las opciones correspondientes.
     */
    public String opcionesDisponibles() {
        if (httpServletRequest.getSession().getAttribute("usuario") != null) return "opcionesUsuario";
        return "opcionesInvitado";
    }

    /**
     * Método que indica si se tiene una sesión activa.
     * @return Devuelve true si hay una sesión seHaIngresado, o falso en otro caso.
     */
    public boolean accedido() {
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