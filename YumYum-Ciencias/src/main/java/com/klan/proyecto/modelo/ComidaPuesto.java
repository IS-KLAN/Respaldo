/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.modelo;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author patlani
 */
@Entity
@Table(name = "comida_puesto", catalog = "yumyum_ciencias", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComidaPuesto.findAll", query = "SELECT c FROM ComidaPuesto c")
    , @NamedQuery(name = "ComidaPuesto.findByIdComida", query = "SELECT c FROM ComidaPuesto c WHERE c.comidaPuestoPK.idComida = :idComida")
    , @NamedQuery(name = "ComidaPuesto.findByIdPuesto", query = "SELECT c FROM ComidaPuesto c WHERE c.comidaPuestoPK.idPuesto = :idPuesto")})
public class ComidaPuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComidaPuestoPK comidaPuestoPK;

    public ComidaPuesto() {
    }

    public ComidaPuesto(ComidaPuestoPK comidaPuestoPK) {
        this.comidaPuestoPK = comidaPuestoPK;
    }

    public ComidaPuesto(int idComida, int idPuesto) {
        this.comidaPuestoPK = new ComidaPuestoPK(idComida, idPuesto);
    }

    public ComidaPuestoPK getComidaPuestoPK() {
        return comidaPuestoPK;
    }

    public void setComidaPuestoPK(ComidaPuestoPK comidaPuestoPK) {
        this.comidaPuestoPK = comidaPuestoPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comidaPuestoPK != null ? comidaPuestoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComidaPuesto)) {
            return false;
        }
        ComidaPuesto other = (ComidaPuesto) object;
        if ((this.comidaPuestoPK == null && other.comidaPuestoPK != null) || (this.comidaPuestoPK != null && !this.comidaPuestoPK.equals(other.comidaPuestoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.ComidaPuesto[ comidaPuestoPK=" + comidaPuestoPK + " ]";
    }
    
}
