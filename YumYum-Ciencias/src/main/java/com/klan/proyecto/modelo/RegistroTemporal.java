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
@Table(name = "registro_temporal", catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_usuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegistroTemporal.findAll", query = "SELECT r FROM RegistroTemporal r")
    , @NamedQuery(name = "RegistroTemporal.findByIdUsuario", query = "SELECT r FROM RegistroTemporal r WHERE r.idUsuario = :idUsuario")
    , @NamedQuery(name = "RegistroTemporal.findByNombreUsuario", query = "SELECT r FROM RegistroTemporal r WHERE r.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "RegistroTemporal.findByCorreo", query = "SELECT r FROM RegistroTemporal r WHERE r.correo = :correo")
    , @NamedQuery(name = "RegistroTemporal.findByContrase\u00f1a", query = "SELECT r FROM RegistroTemporal r WHERE r.contrase\u00f1a = :contrase\u00f1a")})
public class RegistroTemporal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private long idUsuario;
    @Basic(optional = false)
    @Column(name = "nombre_usuario", nullable = false, length = 64)
    private String nombreUsuario;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String correo;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String contraseña;

    public RegistroTemporal() {
    }

    public RegistroTemporal(String correo) {
        this.correo = correo;
    }

    public RegistroTemporal(String correo, long idUsuario, String nombreUsuario, String contraseña) {
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (correo != null ? correo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroTemporal)) {
            return false;
        }
        RegistroTemporal other = (RegistroTemporal) object;
        if ((this.correo == null && other.correo != null) || (this.correo != null && !this.correo.equals(other.correo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.RegistroTemporal[ correo=" + correo + " ]";
    }
    
}
