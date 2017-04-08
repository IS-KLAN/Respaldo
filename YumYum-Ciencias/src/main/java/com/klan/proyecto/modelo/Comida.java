/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author patlani
 */
@Entity
@Table(catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre_alimento"})
    , @UniqueConstraint(columnNames = {"id_comida"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comida.findAll", query = "SELECT c FROM Comida c")
    , @NamedQuery(name = "Comida.findByIdComida", query = "SELECT c FROM Comida c WHERE c.idComida = :idComida")
    , @NamedQuery(name = "Comida.findByNombreAlimento", query = "SELECT c FROM Comida c WHERE c.nombreAlimento = :nombreAlimento")})
public class Comida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comida", nullable = false)
    private Long idComida;
    @Column(name = "nombre_alimento", length = 64)
    private String nombreAlimento;
    @JoinTable(name = "comida_puesto", joinColumns = {
        @JoinColumn(name = "id_comida", referencedColumnName = "id_comida", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "id_puesto", referencedColumnName = "id_puesto", nullable = false)})
    @ManyToMany
    private List<Puesto> puestoList;

    public Comida() {
    }

    public Comida(Long idComida) {
        this.idComida = idComida;
    }

    public Long getIdComida() {
        return idComida;
    }

    public void setIdComida(Long idComida) {
        this.idComida = idComida;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    @XmlTransient
    public List<Puesto> getPuestoList() {
        return puestoList;
    }

    public void setPuestoList(List<Puesto> puestoList) {
        this.puestoList = puestoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComida != null ? idComida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comida)) {
            return false;
        }
        Comida other = (Comida) object;
        if ((this.idComida == null && other.idComida != null) || (this.idComida != null && !this.idComida.equals(other.idComida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.Comida[ idComida=" + idComida + " ]";
    }
    
}
