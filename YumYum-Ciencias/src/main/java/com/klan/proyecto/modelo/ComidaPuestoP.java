/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author patlani
 */
@Embeddable
public class ComidaPuestoP implements Serializable {

    @Basic(optional = false)
    @Column(name = "nombre_comida", nullable = false, length = 64)
    private String nombreComida;
    @Basic(optional = false)
    @Column(name = "nombre_puesto", nullable = false, length = 64)
    private String nombrePuesto;

    public ComidaPuestoP() {
    }

    public ComidaPuestoP(String nombreComida, String nombrePuesto) {
        this.nombreComida = nombreComida;
        this.nombrePuesto = nombrePuesto;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) {
        this.nombreComida = nombreComida;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreComida != null ? nombreComida.hashCode() : 0);
        hash += (nombrePuesto != null ? nombrePuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComidaPuestoP)) {
            return false;
        }
        ComidaPuestoP other = (ComidaPuestoP) object;
        if ((this.nombreComida == null && other.nombreComida != null) || (this.nombreComida != null && !this.nombreComida.equals(other.nombreComida))) {
            return false;
        }
        if ((this.nombrePuesto == null && other.nombrePuesto != null) || (this.nombrePuesto != null && !this.nombrePuesto.equals(other.nombrePuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ nombreComida=" + nombreComida + ", nombrePuesto=" + nombrePuesto + " ]";
    }
    
}
