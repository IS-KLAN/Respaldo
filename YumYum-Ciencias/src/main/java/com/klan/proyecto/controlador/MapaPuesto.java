/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;


import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.klan.proyecto.modelo.Puesto;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


/**
 *
 * @author karla
 */

@ManagedBean
@ViewScoped
public class MapaPuesto implements Serializable {
    private MapModel modelo;
    private Puesto elegido;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext;
    
    public MapaPuesto() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }
    
    @PostConstruct
    public void cargar() {      
        modelo = new DefaultMapModel();
        
        elegido = (Puesto) httpServletRequest.getSession().getAttribute("puesto");
        
        double lat= Double.parseDouble(elegido.getLatitud());
        double lon= Double.parseDouble(elegido.getLongitud());
        String nombre = elegido.getNombre();          
        modelo.addOverlay(new Marker(new LatLng(lat, lon), nombre)); 
    }       
    
    public MapModel getModelo() {
        return modelo;
    }
}
