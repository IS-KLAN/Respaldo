/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import com.klan.proyecto.controlador.UsuarioC; // Para consultar usuarios de la BD.
import com.klan.proyecto.controlador.PuestoC; // Para consultar puestos de la BD.
import com.klan.proyecto.modelo.Puesto; // Para construir un puesto.
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

    private String nombreUsuario;
    private String nombrePuesto;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de nombreUsuario.
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
     * Obtiene el nombre de nombreUsuario.
     *
     * @return El nombre de nombreUsuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Establece el nombre de nombreUsuario.
     *
     * @param nombreUsuario El nombre de nombreUsuario a establecer.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Regresa el nombrePuesto elegido.
     *
     * @return El nombrePuesto elegido.
     */
    public String getNombrePuesto() {
        return nombrePuesto;
    }

    /**
     * Establece el nombrePuesto elegido.
     *
     * @param nombrePuesto El nombrePuesto elegido por el nombreUsuario.
     */
    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    /**
     * Método encargado de capturar los datos ingresados.
     * @return El nombre de la vista que va a responder.
     */
    public String capturar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        Usuario u = new UsuarioC(emf).findUsuario(nombreUsuario);
        Puesto p = new PuestoC(emf).findPuesto(nombrePuesto);
        httpServletRequest.getSession().setAttribute("usuario", u);
        httpServletRequest.getSession().setAttribute("puesto", p);
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
        faceContext.addMessage(null, message);
        return "perfilPuesto";
    }

    public String opcionesDisponibles() {
        if (httpServletRequest.getSession().getAttribute("usuario") != null) return "opcionesUsuario";
        return "opcionesInvitado";
    }
}
