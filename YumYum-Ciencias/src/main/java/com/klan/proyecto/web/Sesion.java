/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import com.klan.proyecto.controlador.PuestoC;
import com.klan.proyecto.controlador.UsuarioC;
import com.klan.proyecto.modelo.Puesto;
import com.klan.proyecto.modelo.Usuario;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * Bean manejado qué se utiliza para el manejo de inicio de Sesión en
 * la aplicación web.
 *
 * @author miguel
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class Sesion {

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
}
