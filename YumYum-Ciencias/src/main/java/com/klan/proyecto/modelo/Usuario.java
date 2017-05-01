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
@Table(name = "usuario", catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"correo"})
    , @UniqueConstraint(columnNames = {"id_usuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.busca", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.buscaIdUsuario", query = "SELECT u FROM Usuario u WHERE u.id = :id")
    , @NamedQuery(name = "Usuario.buscaNombreUsuario", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.buscaCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private long id;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_usuario", nullable = false, length = 64)
    private String nombre;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String correo;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String contraseña;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Evaluacion> evaluaciones;
    public static final String dominio = "@ciencias.unam.mx";

    public Usuario() {
    }

    public Usuario(String nombreUsuario) {
        this.nombre = nombreUsuario;
    }

    public Usuario(String nombreUsuario, long idUsuario, String correo, String contraseña) {
        this.nombre = nombreUsuario;
        this.id = idUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Usuario(String nombreUsuario, String correo, String contraseña) {
        this.nombre = nombreUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
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

    public String getCorreo() {
        return correo + dominio;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
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
