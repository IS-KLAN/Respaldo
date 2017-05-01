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
    @NamedQuery(name = "Pendiente.busca", query = "SELECT p FROM Pendiente p")
    , @NamedQuery(name = "Pendiente.buscaId", query = "SELECT p FROM Pendiente p WHERE p.id = :id")
    , @NamedQuery(name = "Pendiente.buscaNombre", query = "SELECT p FROM Pendiente p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Pendiente.buscaCorreo", query = "SELECT p FROM Pendiente p WHERE p.correo = :correo")})
public class Pendiente implements Serializable {

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

    public Pendiente() {
    }

    public Pendiente(String nombreUsuario) {
        this.nombre = nombreUsuario;
    }

    public Pendiente(String nombreUsuario, long idUsuario, String correo, String contraseña) {
        this.nombre = nombreUsuario;
        this.id = idUsuario;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public Pendiente(String nombreUsuario, String correo, String contraseña) {
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
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pendiente)) {
            return false;
        }
        Pendiente other = (Pendiente) object;
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