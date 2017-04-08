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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @UniqueConstraint(columnNames = {"id_evaluacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluacion.findAll", query = "SELECT e FROM Evaluacion e")
    , @NamedQuery(name = "Evaluacion.findByIdEvaluacion", query = "SELECT e FROM Evaluacion e WHERE e.idEvaluacion = :idEvaluacion")
    , @NamedQuery(name = "Evaluacion.findByComentario", query = "SELECT e FROM Evaluacion e WHERE e.comentario = :comentario")
    , @NamedQuery(name = "Evaluacion.findByCalificacion", query = "SELECT e FROM Evaluacion e WHERE e.calificacion = :calificacion")
    , @NamedQuery(name = "Evaluacion.findByIdPuesto", query = "SELECT e FROM Evaluacion e WHERE e.idPuesto = :idPuesto")
    , @NamedQuery(name = "Evaluacion.findByIdUsuario", query = "SELECT e FROM Evaluacion e WHERE e.idUsuario = :idUsuario")})
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evaluacion", nullable = false)
    private Long idEvaluacion;
    @Column(length = 255)
    private String comentario;
    @Basic(optional = false)
    @Column(nullable = false)
    private int calificacion;
    @Column(name = "id_puesto")
    private Integer idPuesto;
    @Column(name = "id_usuario")
    private Integer idUsuario;

    public Evaluacion() {
    }

    public Evaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Evaluacion(Long idEvaluacion, int calificacion) {
        this.idEvaluacion = idEvaluacion;
        this.calificacion = calificacion;
    }

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(Integer idPuesto) {
        this.idPuesto = idPuesto;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvaluacion != null ? idEvaluacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.idEvaluacion == null && other.idEvaluacion != null) || (this.idEvaluacion != null && !this.idEvaluacion.equals(other.idEvaluacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.Evaluacion[ idEvaluacion=" + idEvaluacion + " ]";
    }
    
}
