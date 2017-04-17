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
import javax.faces.application.FacesMessage;


/**
 *
 * @author karla
 */

@ManagedBean
@ViewScoped
public class Mapa implements Serializable {
    
    private String title;
      
    private double lat;
      
    private double lng;
     
    private MapModel simpleModel;
    private Marker marker;

    private ExternalContext ec;
  
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

      
    public void onMarkerSelect(OverlaySelectEvent event) throws IOException {
        marker = (Marker) event.getOverlay();
        //System.out.println(marker.getLatlng());
        Puesto p = buscaPuesto(marker);
        //System.out.println("Seleccionando el puesto: " + p.getNombrePuesto());
        ec = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpServletRequest)ec.getRequest()).getSession().setAttribute("puesto", p);
        ec.redirect("perfilPuesto.xhtml");
    }
    
    public Puesto buscaPuesto(Marker marker){
        double lat= marker.getLatlng().getLat();
        double lon= marker.getLatlng().getLng();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        List<Puesto> puestos = new PuestoC(emf).findPuestoEntities();
        for(Puesto uno: puestos){
            double lt= Double.parseDouble(uno.getLatitud());
            double ln= Double.parseDouble(uno.getLongitud());
            
            if(lt==lat && ln==lon){
               return uno;
            }
        } return null;
    }
          
    public MapModel getSimpleModel() {
        return simpleModel;
    }
      
    public Marker getMarker() {
        return marker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public ExternalContext getEc() {
        return ec;
    }

    public void setEc(ExternalContext ec) {
        this.ec = ec;
    }
    
    public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), title);
        simpleModel.addOverlay(marker);
          
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
    }
    
    
}