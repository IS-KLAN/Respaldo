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
public class ComidaPuestoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_comida", nullable = false)
    private int idComida;
    @Basic(optional = false)
    @Column(name = "id_puesto", nullable = false)
    private int idPuesto;

    public ComidaPuestoPK() {
    }

    public ComidaPuestoPK(int idComida, int idPuesto) {
        this.idComida = idComida;
        this.idPuesto = idPuesto;
    }

    public int getIdComida() {
        return idComida;
    }

    public void setIdComida(int idComida) {
        this.idComida = idComida;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idComida;
        hash += (int) idPuesto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComidaPuestoPK)) {
            return false;
        }
        ComidaPuestoPK other = (ComidaPuestoPK) object;
        if (this.idComida != other.idComida) {
            return false;
        }
        if (this.idPuesto != other.idPuesto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.ComidaPuestoPK[ idComida=" + idComida + ", idPuesto=" + idPuesto + " ]";
    }
    
}
