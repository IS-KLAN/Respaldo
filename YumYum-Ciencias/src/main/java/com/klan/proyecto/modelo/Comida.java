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
    @NamedQuery(name = "Comida.busca", query = "SELECT c FROM Comida c")
    , @NamedQuery(name = "Comida.buscaId", query = "SELECT c FROM Comida c WHERE c.id = :id")
    , @NamedQuery(name = "Comida.buscaNombre", query = "SELECT c FROM Comida c WHERE c.nombre = :nombre")})
public class Comida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_comida", nullable = false)
    private long id;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_comida", nullable = false, length = 64)
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comida")
    private List<ComidaPuesto> listaRelacionada;

    public Comida() {
    }

    public Comida(String nombreComida) {
        this.nombre = nombreComida;
    }

    public Comida(String nombreComida, long idComida) {
        this.nombre = nombreComida;
        this.id = idComida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<ComidaPuesto> getListaRelacionada() {
        return listaRelacionada;
    }

    public void setListaRelacionada(List<ComidaPuesto> listaRelacionada) {
        this.listaRelacionada = listaRelacionada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comida)) {
            return false;
        }
        Comida other = (Comida) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
