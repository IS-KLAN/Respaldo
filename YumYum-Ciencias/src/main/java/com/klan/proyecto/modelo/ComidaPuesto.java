/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author patlani
 */
@Entity
@Table(name = "comida_puesto", catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_comida_puesto"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComidaPuesto.findAll", query = "SELECT c FROM ComidaPuesto c")
    , @NamedQuery(name = "ComidaPuesto.findByIdComidaPuesto", query = "SELECT c FROM ComidaPuesto c WHERE c.idComidaPuesto = :idComidaPuesto")
    , @NamedQuery(name = "ComidaPuesto.findByNombreComida", query = "SELECT c FROM ComidaPuesto c WHERE c.comidaPuestoPK.nombreComida = :nombreComida")
    , @NamedQuery(name = "ComidaPuesto.findByNombrePuesto", query = "SELECT c FROM ComidaPuesto c WHERE c.comidaPuestoPK.nombrePuesto = :nombrePuesto")})
public class ComidaPuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComidaPuestoPK comidaPuestoPK;
    @Basic(optional = false)
    @Column(name = "id_comida_puesto", nullable = false)
    private long idComidaPuesto;
    @JoinColumn(name = "nombre_comida", referencedColumnName = "nombre_comida", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comida comida;
    @JoinColumn(name = "nombre_puesto", referencedColumnName = "nombre_puesto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Puesto puesto;

    public ComidaPuesto() {
    }

    public ComidaPuesto(ComidaPuestoPK comidaPuestoPK) {
        this.comidaPuestoPK = comidaPuestoPK;
    }

    public ComidaPuesto(ComidaPuestoPK comidaPuestoPK, long idComidaPuesto) {
        this.comidaPuestoPK = comidaPuestoPK;
        this.idComidaPuesto = idComidaPuesto;
    }

    public ComidaPuesto(String nombreComida, String nombrePuesto) {
        this.comidaPuestoPK = new ComidaPuestoPK(nombreComida, nombrePuesto);
    }

    public ComidaPuestoPK getComidaPuestoPK() {
        return comidaPuestoPK;
    }

    public void setComidaPuestoPK(ComidaPuestoPK comidaPuestoPK) {
        this.comidaPuestoPK = comidaPuestoPK;
    }

    public long getIdComidaPuesto() {
        return idComidaPuesto;
    }

    public void setIdComidaPuesto(long idComidaPuesto) {
        this.idComidaPuesto = idComidaPuesto;
    }

    public Comida getComida() {
        return comida;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
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
