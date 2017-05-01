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
@Table(name = "evaluacion", catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_evaluacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evaluacion.busca", query = "SELECT e FROM Evaluacion e")
    , @NamedQuery(name = "Evaluacion.buscaId", query = "SELECT e FROM Evaluacion e WHERE e.id = :id")
    , @NamedQuery(name = "Evaluacion.buscaComentario", query = "SELECT e FROM Evaluacion e WHERE e.comentario = :comentario")
    , @NamedQuery(name = "Evaluacion.buscaCalificacion", query = "SELECT e FROM Evaluacion e WHERE e.calificacion = :calificacion")
    , @NamedQuery(name = "Evaluacion.buscaNombrePuesto", query = "SELECT e FROM Evaluacion e WHERE e.llave.nombrePuesto = :nombrePuesto")
    , @NamedQuery(name = "Evaluacion.buscaNombreUsuario", query = "SELECT e FROM Evaluacion e WHERE e.llave.nombreUsuario = :nombreUsuario")})
public class Evaluacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionP llave;
    @Basic(optional = false)
    @Column(name = "id_evaluacion", nullable = false)
    private long id;
    @Column(length = 255)
    private String comentario;
    @Basic(optional = false)
    @Column(nullable = false)
    private int calificacion;
    @JoinColumn(name = "nombre_puesto", referencedColumnName = "nombre_puesto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Puesto puesto;
    @JoinColumn(name = "nombre_usuario", referencedColumnName = "nombre_usuario", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Evaluacion() {
    }

    public Evaluacion(EvaluacionP evaluacionPK) {
        this.llave = evaluacionPK;
    }

    public Evaluacion(EvaluacionP evaluacionPK, String comentario, int calificacion) {
        this.llave = evaluacionPK;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public Evaluacion(String nombrePuesto, String nombreUsuario) {
        this.llave = new EvaluacionP(nombrePuesto, nombreUsuario);
    }

    public EvaluacionP getLlave() {
        return llave;
    }

    public void setLlave(EvaluacionP llave) {
        this.llave = llave;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Evaluacion)) {
            return false;
        }
        Evaluacion other = (Evaluacion) object;
        if ((this.llave == null && other.llave != null) || (this.llave != null && !this.llave.equals(other.llave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Evaluacion[ evaluacionPK=" + llave.toString() + " ]";
    }
    
}
