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
public class EvaluacionP implements Serializable {

    @Basic(optional = false)
    @Column(name = "nombre_puesto", nullable = false, length = 64)
    private String nombrePuesto;
    @Basic(optional = false)
    @Column(name = "nombre_usuario", nullable = false, length = 64)
    private String nombreUsuario;

    public EvaluacionP() {
    }

    public EvaluacionP(String nombrePuesto, String nombreUsuario) {
        this.nombrePuesto = nombrePuesto;
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombrePuesto != null ? nombrePuesto.hashCode() : 0);
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionP)) {
            return false;
        }
        EvaluacionP other = (EvaluacionP) object;
        if ((this.nombrePuesto == null && other.nombrePuesto != null) || (this.nombrePuesto != null && !this.nombrePuesto.equals(other.nombrePuesto))) {
            return false;
        }
        if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ nombrePuesto=" + nombrePuesto + ", nombreUsuario=" + nombreUsuario + " ]";
    }
    
}
