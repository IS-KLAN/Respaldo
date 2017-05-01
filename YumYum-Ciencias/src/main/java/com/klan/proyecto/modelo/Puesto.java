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
@Table(name = "puesto", catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_puesto"})
    , @UniqueConstraint(columnNames = {"latitud", "longitud"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Puesto.busca", query = "SELECT p FROM Puesto p")
    , @NamedQuery(name = "Puesto.buscaId", query = "SELECT p FROM Puesto p WHERE p.id = :id")
    , @NamedQuery(name = "Puesto.buscaNombre", query = "SELECT p FROM Puesto p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Puesto.buscaLugar", query = "SELECT p FROM Puesto p WHERE p.latitud = :latitud AND p.longitud = :longitud")})
public class Puesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_puesto", nullable = false)
    private long id;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_puesto", nullable = false, length = 64)
    private String nombre;
    @Column(length = 255)
    private String descripcion;
    @Basic(optional = false)
    @Column(nullable = false, length = 16)
    private String latitud;
    @Basic(optional = false)
    @Column(nullable = false, length = 16)
    private String longitud;
    @Column(length = 255)
    private String rutaImagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puesto")
    private List<ComidaPuesto> comida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "puesto")
    private List<Evaluacion> evaluaciones;

    public Puesto() {
    }

    public Puesto(String nombre, String descripcion, String latitud, String longitud, String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.rutaImagen = rutaImagen;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    @XmlTransient
    public List<ComidaPuesto> getComida() {
        return comida;
    }

    public void setComida(List<ComidaPuesto> comida) {
        this.comida = comida;
    }

    @XmlTransient
    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
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
        if (!(object instanceof Puesto)) {
            return false;
        }
        Puesto other = (Puesto) object;
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
