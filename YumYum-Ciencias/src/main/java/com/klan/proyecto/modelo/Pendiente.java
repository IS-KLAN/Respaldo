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
@Table(name = "pendiente", catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"correo"})
    , @UniqueConstraint(columnNames = {"id_usuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pendiente.findAll", query = "SELECT p FROM Pendiente p")
    , @NamedQuery(name = "Pendiente.findByIdUsuario", query = "SELECT p FROM Pendiente p WHERE p.idUsuario = :idUsuario")
    , @NamedQuery(name = "Pendiente.findByNombreUsuario", query = "SELECT p FROM Pendiente p WHERE p.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Pendiente.findByCorreo", query = "SELECT p FROM Pendiente p WHERE p.correo = :correo")
    , @NamedQuery(name = "Pendiente.findByContrase\u00f1a", query = "SELECT p FROM Pendiente p WHERE p.contrase\u00f1a = :contrase\u00f1a")})
public class Pendiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private long idUsuario;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_usuario", nullable = false, length = 64)
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String correo;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String contraseña;

    public Pendiente() {
    }

    public Pendiente(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Pendiente(String nombreUsuario, long idUsuario, String correo, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Pendiente(String nombreUsuario, String correo, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
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
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pendiente)) {
            return false;
        }
        Pendiente other = (Pendiente) object;
        if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombreUsuario;
    }  
}