/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author patlani
 */
@Entity
@Table(name = "comida", catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_comida"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comida.findAll", query = "SELECT c FROM Comida c")
    , @NamedQuery(name = "Comida.findByIdComida", query = "SELECT c FROM Comida c WHERE c.idComida = :idComida")
    , @NamedQuery(name = "Comida.findByNombreComida", query = "SELECT c FROM Comida c WHERE c.nombreComida = :nombreComida")})
public class Comida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_comida", nullable = false)
    private long idComida;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_comida", nullable = false, length = 64)
    private String nombreComida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comida")
    private List<ComidaPuesto> comidaPuestoList;

    public Comida() {
    }

    public Comida(String nombreComida) {
        this.nombreComida = nombreComida;
    }

    public Comida(String nombreComida, long idComida) {
        this.nombreComida = nombreComida;
        this.idComida = idComida;
    }

    public long getIdComida() {
        return idComida;
    }

    public void setIdComida(long idComida) {
        this.idComida = idComida;
    }

    public String getNombreComida() {
        return nombreComida;
    }

    public void setNombreComida(String nombreComida) {
        this.nombreComida = nombreComida;
    }

    @XmlTransient
    public List<ComidaPuesto> getComidaPuestoList() {
        return comidaPuestoList;
    }

    public void setComidaPuestoList(List<ComidaPuesto> comidaPuestoList) {
        this.comidaPuestoList = comidaPuestoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreComida != null ? nombreComida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comida)) {
            return false;
        }
        Comida other = (Comida) object;
        if ((this.nombreComida == null && other.nombreComida != null) || (this.nombreComida != null && !this.nombreComida.equals(other.nombreComida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.Comida[ nombreComida=" + nombreComida + " ]";
    }
    
}
