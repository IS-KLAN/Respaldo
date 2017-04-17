/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import java.util.List;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.klan.proyecto.modelo.Puesto;
import com.klan.proyecto.controlador.PuestoC;
import java.io.Serializable;

/**
 *
 * @author miguel
 */
@ManagedBean
@ViewScoped
public class Mapas implements Serializable{

    private MapModel advancedModel;

    private Marker marker;

    private PuestoC puestoC;

    private String nombre;

    private double lat;

    private double lng;

    @PostConstruct
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        advancedModel = new DefaultMapModel();
        puestoC = new PuestoC(emf);
        List<Puesto> lugares = puestoC.findPuestoEntities();
        for (Puesto lugar : lugares) {
            Double latitud = Double.parseDouble(lugar.getLatitud());
            Double longitud = Double.parseDouble(lugar.getLongitud());
            String nombre = lugar.getNombrePuesto();
            System.out.println(latitud + ", " + longitud + ", " + nombre);
            advancedModel.addOverlay(new Marker(new LatLng(latitud, longitud), nombre));
        }
    }

    public MapModel getAdvancedModel() {
        return advancedModel;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        System.out.println(marker.getTitle());
    }

    public Marker getMarker() {
        return marker;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void addMarker() {
        Marker marker = new Marker(new LatLng(lat, lng), nombre);
        Puesto l = new Puesto();
        l.setNombrePuesto(nombre);
        l.setLatitud(Double.toString(lat));
        l.setLongitud(Double.toString(lng));
        try{ puestoC.create(l);
        } catch(Exception ex) {
        } advancedModel.addOverlay(marker);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Marker Added", "Lat:" + lat + ", Lng:" + lng));
    }
}