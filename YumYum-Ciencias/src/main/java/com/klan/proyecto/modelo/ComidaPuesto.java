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
    @NamedQuery(name = "ComidaPuesto.busca", query = "SELECT c FROM ComidaPuesto c")
    , @NamedQuery(name = "ComidaPuesto.buscaId", query = "SELECT c FROM ComidaPuesto c WHERE c.id = :id")
    , @NamedQuery(name = "ComidaPuesto.buscaNombreComida", query = "SELECT c FROM ComidaPuesto c WHERE c.llave.nombreComida = :nombreComida")
    , @NamedQuery(name = "ComidaPuesto.buscaNombrePuesto", query = "SELECT c FROM ComidaPuesto c WHERE c.llave.nombrePuesto = :nombrePuesto")})
public class ComidaPuesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComidaPuestoP llave;
    @Basic(optional = false)
    @Column(name = "id_comida_puesto", nullable = false)
    private long id;
    @JoinColumn(name = "nombre_comida", referencedColumnName = "nombre_comida", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comida comida;
    @JoinColumn(name = "nombre_puesto", referencedColumnName = "nombre_puesto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Puesto puesto;

    public ComidaPuesto() {
    }

    public ComidaPuesto(ComidaPuestoP comidaPuestoPK) {
        this.llave = comidaPuestoPK;
    }

    public ComidaPuesto(ComidaPuestoP comidaPuestoPK, long idComidaPuesto) {
        this.llave = comidaPuestoPK;
        this.id = idComidaPuesto;
    }

    public ComidaPuesto(String nombreComida, String nombrePuesto) {
        this.llave = new ComidaPuestoP(nombreComida, nombrePuesto);
    }

    public ComidaPuestoP getLlave() {
        return llave;
    }

    public void setLlave(ComidaPuestoP llave) {
        this.llave = llave;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        hash += (llave != null ? llave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComidaPuesto)) {
            return false;
        }
        ComidaPuesto other = (ComidaPuesto) object;
        if ((this.llave == null && other.llave != null) || (this.llave != null && !this.llave.equals(other.llave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[ comidaPuestoPK=" + llave.toString() + " ]";
    }
    
}
