/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import java.io.IOException;
import javax.faces.context.ExternalContext;
  
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.klan.proyecto.jpa.PuestoC;
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
public class Mapa implements Serializable {
      
    private double lat;
      
    private double lng;
     
    private MapModel modelo;
    private Marker marcador;

    private ExternalContext ec;
  
    @PostConstruct
    public void cargar() {      
        modelo = new DefaultMapModel();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");        
        List<Puesto> todos = new PuestoC(emf).buscaPuestos();
        for (Puesto uno: todos){
            double lat= Double.parseDouble(uno.getLatitud());
            double lon= Double.parseDouble(uno.getLongitud());
            String nombre = uno.getNombre();          
            modelo.addOverlay(new Marker(new LatLng(lat, lon), nombre));
        }       
    }

      
    public void onMarkerSelect(OverlaySelectEvent event) throws IOException {
        marcador = (Marker) event.getOverlay();
        //System.out.println(marcador.getLatlng());
        Puesto p = buscaPuesto(marcador);
        //System.out.println("Seleccionando el puesto: " + p.getNombrePuesto());
        ec = FacesContext.getCurrentInstance().getExternalContext();
        ((HttpServletRequest)ec.getRequest()).getSession().setAttribute("puesto", p);
        ec.redirect("perfilPuesto.xhtml");
    }
    
    public Puesto buscaPuesto(Marker marker){
        String lat= Double.toString(marker.getLatlng().getLat());
        String lng= Double.toString(marker.getLatlng().getLng());
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        return new PuestoC(emf).buscaLugar(lat, lng);
    }
          
    public MapModel getModelo() {
        return modelo;
    }
      
    public Marker getMarcador() {
        return marcador;
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
}