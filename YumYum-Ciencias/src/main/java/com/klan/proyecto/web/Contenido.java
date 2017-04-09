/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import java.util.List;

import com.klan.proyecto.controlador.ComidaPuestoC;
import com.klan.proyecto.controlador.EvaluacionC;
import com.klan.proyecto.modelo.ComidaPuesto;
import com.klan.proyecto.modelo.Evaluacion;
import com.klan.proyecto.modelo.Puesto;
import com.klan.proyecto.modelo.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author patlani
 */
@ManagedBean
@ViewScoped
public class Contenido {

    private Usuario usuario;
    private Puesto puesto;
    private String texto;
    private List<Evaluacion> evaluaciones;
    private List<ComidaPuesto> comida;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;

    /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Contenido() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("usuario") != null) {
            usuario = (Usuario)httpServletRequest.getSession().getAttribute("usuario");
        }
        if (httpServletRequest.getSession().getAttribute("puesto") != null) {
            puesto = (Puesto)httpServletRequest.getSession().getAttribute("puesto");
        }
    }
    
    @PostConstruct
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        comida = new ComidaPuestoC(emf).findByNombrePuesto(puesto.getNombrePuesto());
        evaluaciones = new EvaluacionC(emf).findByNombrePuesto(puesto.getNombrePuesto());
    }

    /**
     * Método de acceso al usuario de la sesión actual.
     * @return El usuario que ha iniciado sesión.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establecer el usuario de la sesión actual.
     * @param usuario Es el usuario correspondiente a la sesión actual.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Método de acceso al puesto elegido por el usuario.
     * @return Devuelve el puesto que el usuario haya elegido.
     */
    public Puesto getPuesto() {
        return puesto;
    }

    /**
     * Establecer el puesto elegido por el usuario.
     * @param puesto El puesto que se haya elegido.
     */    
    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    /**
     * Método de acceso al texto que se ha escrito en la interfaz.
     * @return Devuelve el mensaje comentado en la interfaz.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Método que establece el texto del comentario que será insertado.
     * @param texto Texto ingresado para ser guardado.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }    
    
    /**
     * Método que se encarga de mostrar los comentarios disponibles a la interfaz.
     * @return Devuelve una lista con los comentarios obtenidos del puesto.
     */
    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    /**
     * Método que se encarga de mostrar los productos disponibles a la interfaz.
     * @return Devuelve una lista con los producto obtenidos del puesto.
     */
    public List<ComidaPuesto> getComida() {
        return comida;
    }

    /**
     * Método que se encarga de actualizar los mensajes de la interfaz.
     */
    public void actualizar() {
    }

    /**
     * Método que se encarga de guardar el comentario insertado.
     */
    public void comentar() {
    }
}