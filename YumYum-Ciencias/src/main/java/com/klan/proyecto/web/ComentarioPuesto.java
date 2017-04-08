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

import com.klan.proyecto.modelo.Comida;
import com.klan.proyecto.modelo.Evaluacion;
import java.util.ArrayList;

/**
 *
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class ComentarioPuesto {

    private int usuario;
    private int puesto;
    private String texto;
    private ArrayList<Evaluacion> evaluaciones;
    private ArrayList<Comida> comida;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;

    /**
     * Creates a new instance of Comentario
     */
    public ComentarioPuesto() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("idusuario") != null) {
            usuario = Integer.parseInt(httpServletRequest.getSession().getAttribute("idusuario").toString());
        }
        if (httpServletRequest.getSession().getAttribute("idpuesto") != null) {
            puesto = Integer.parseInt(httpServletRequest.getSession().getAttribute("idpuesto").toString());
        }
    }

    public FacesMessage getMessage() {
        return message;
    }

    public void setMessage(FacesMessage message) {
        this.message = message;
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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ArrayList<Evaluacion> verComentarios() {
        return evaluaciones;
    }

    public ArrayList<Comida> verProductos() {
        return comida;
    }
    public String guardaDatos() {
        httpServletRequest.getSession().setAttribute("idusuario", usuario);
        httpServletRequest.getSession().setAttribute("idpuesto", puesto);
        return "perfilPuesto";
    }
    
    public void comentar() {
    }
}