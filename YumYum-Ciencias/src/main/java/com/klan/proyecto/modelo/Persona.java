/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author patlani
 */
@Entity
@Table(catalog = "yumyum_ciencias", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre_usuario"})
    , @UniqueConstraint(columnNames = {"correo"})
    , @UniqueConstraint(columnNames = {"id_persona"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona")
    , @NamedQuery(name = "Persona.findByNombreUsuario", query = "SELECT p FROM Persona p WHERE p.nombreUsuario = :nombreUsuario")
    , @NamedQuery(name = "Persona.findByCorreo", query = "SELECT p FROM Persona p WHERE p.correo = :correo")
    , @NamedQuery(name = "Persona.findByContrase\u00f1a", query = "SELECT p FROM Persona p WHERE p.contrase\u00f1a = :contrase\u00f1a")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona", nullable = false)
    private Long idPersona;
    @Basic(optional = false)
    @Column(name = "nombre_usuario", nullable = false, length = 64)
    private String nombreUsuario;
    @Column(length = 32)
    private String correo;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String contraseña;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idPersona")
    private Usuario usuario;

    public Persona() {
    }

    public Persona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Persona(Long idPersona, String nombreUsuario, String contraseña) {
        this.idPersona = idPersona;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.klan.proyecto.modelo.Persona[ idPersona=" + idPersona + " ]";
    }
    
}
