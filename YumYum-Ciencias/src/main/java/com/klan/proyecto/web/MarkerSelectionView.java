/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import java.io.IOException;
import javax.faces.context.ExternalContext;
  
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.klan.proyecto.controlador.PuestoC;
import com.klan.proyecto.modelo.Puesto;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;


/**
 *
 * @author karla
 */

@ManagedBean
@ViewScoped
public class MarkerSelectionView implements Serializable {
     
    private MapModel simpleModel;
    private Marker marker;
    
    private Puesto puesto;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    
    public MarkerSelectionView() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }
  
    @PostConstruct
    public void init() {      
        simpleModel = new DefaultMapModel();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        
        List<Puesto> todos = new PuestoC(emf).findPuestoEntities();
        for (Puesto uno: todos){
            double lat= Double.parseDouble(uno.getLatitud());
            double lon= Double.parseDouble(uno.getLongitud());
            String nombre = uno.getNombrePuesto();          
            simpleModel.addOverlay(new Marker(new LatLng(lat, lon), nombre)); 
        }       
    }
      
    public MapModel getSimpleModel() {
        return simpleModel;
    }
      
    public void onMarkerSelect(OverlaySelectEvent event) throws IOException {
        marker = (Marker) event.getOverlay();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("perfilPuesto.xhtml");
    }
      
    public Marker getMarker() {
        return marker;
    }
    
    public Puesto getPuesto() {
        return puesto;
    }
}