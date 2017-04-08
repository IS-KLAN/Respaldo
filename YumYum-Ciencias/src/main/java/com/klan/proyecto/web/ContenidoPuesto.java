/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

/**
 *
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class ContenidoPuesto {

    private int usuario;
    private int puesto;
    private ArrayList<Object> evaluaciones;
    private ArrayList<Object> comida;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;

    /**
     * Creates a new instance of Comentario
     */
    public ContenidoPuesto() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("idusuario") != null) {
            usuario = Integer.parseInt(httpServletRequest.getSession().getAttribute("idusuario").toString());
        }
        if (httpServletRequest.getSession().getAttribute("idpuesto") != null) {
            puesto = Integer.parseInt(httpServletRequest.getSession().getAttribute("idpuesto").toString());
        }
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getPuesto() {
        return puesto;
    }

    public void setPuesto(int puesto) {
        this.puesto = puesto;
    }

    public ArrayList<Object> verComentarios() {
        return evaluaciones;
    }

    public ArrayList<Object> verProductos() {
        return comida;
    }
    
    public void actualizar() {
    }
    
    public void comentar() {
    }
}