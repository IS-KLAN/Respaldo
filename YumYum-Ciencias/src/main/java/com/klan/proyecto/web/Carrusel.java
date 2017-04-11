/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import java.util.List;

import com.klan.proyecto.controlador.PuestoC;
import com.klan.proyecto.modelo.Puesto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;

/**
 *
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class Carrusel implements Serializable{
    
    private Puesto primero;
    private List<Puesto> puestos;

    /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Carrusel() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        puestos = new PuestoC(emf).findPuestoEntities();
        if (puestos.size() < 1) {
            primero = new Puesto();
            primero.setRutaImagen("default.jpg");
        } else primero = puestos.remove(0);
    }

    /**
     * Acceso al primer elemento de la lista de puestos.
     * @return Devuelve el primer puesto en la tabla.
     */
    public Puesto getPrimero() {
        return primero;
    }

    /**
     * Establece el primer puesto de la lista.
     * @param primero Es el primer puesto que se va a mostrar.
     */
    public void setPrimero(Puesto primero) {
        this.primero = primero;
    }
    
    /**
     * Acceso a la lista de puestos a mostrar.
     * @return Devuelve la lista de puestos guardados.
     */
    public List<Puesto> getPuestos() {
        return puestos;
    }

    /**
     * Establece una lista de puestos.
     * @param puestos  Es la lista que se establece.
     */
    public void setPuestos(List<Puesto> puestos) {
        this.puestos = puestos;
    }    
}