/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_puesto"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Puesto.findAll", query = "SELECT p FROM Puesto p")
    , @NamedQuery(name = "Puesto.findByIdPuesto", query = "SELECT p FROM Puesto p WHERE p.idPuesto = :idPuesto")
    , @NamedQuery(name = "Puesto.findByNombrePuesto", query = "SELECT p FROM Puesto p WHERE p.nombrePuesto = :nombrePuesto")
    , @NamedQuery(name = "Puesto.findByDescripcion", query = "SELECT p FROM Puesto p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Puesto.findByLatitud", query = "SELECT p FROM Puesto p WHERE p.latitud = :latitud")
    , @NamedQuery(name = "Puesto.findByLongitud", query = "SELECT p FROM Puesto p WHERE p.longitud = :longitud")
    , @NamedQuery(name = "Puesto.findByRutaImagen", query = "SELECT p FROM Puesto p WHERE p.rutaImagen = :rutaImagen")})
public class Puesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_puesto", nullable = false)
    private long idPuesto;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_puesto", nullable = false, length = 64)
    private String nombrePuesto;
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

    public Puesto() {
    }

    public Puesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
    }

    public Puesto(String nombrePuesto, long idPuesto, String latitud, String longitud) {
        this.nombrePuesto = nombrePuesto;
        this.idPuesto = idPuesto;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public long getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(long idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombrePuesto() {
        return nombrePuesto;
    }

    public void setNombrePuesto(String nombrePuesto) {
        this.nombrePuesto = nombrePuesto;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombrePuesto != null ? nombrePuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Puesto)) {
            return false;
        }
        Puesto other = (Puesto) object;
        if ((this.nombrePuesto == null && other.nombrePuesto != null) || (this.nombrePuesto != null && !this.nombrePuesto.equals(other.nombrePuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.Puesto[ nombrePuesto=" + nombrePuesto + " ]";
    }
    
}
