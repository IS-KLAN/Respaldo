/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import com.klan.proyecto.controlador.PendienteC; // Para insertar un nuevo pendiente.
import com.klan.proyecto.modelo.Pendiente; // Para construir un usuario pendiente.

import javax.faces.application.FacesMessage; // Para mostrar y obtener mensajes de avisos.
import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.persistence.EntityManagerFactory; // Para conectarse a la BD.
import javax.persistence.Persistence; // Para definir los parámetros de conexión a la BD.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.

/**
 *
 * Bean utilizado para pruebas al perfil de un puesto.
 * @author nancy
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class Registro implements Serializable{

    private int id_usuario;
    private String nombre_usuario;
    private String correo;
    private String contraseña;
    private String confirmaContraseña;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    
    /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Registro() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();  
        if (httpServletRequest.getSession().getAttribute("registro") != null) {
            Pendiente usuario = ((Pendiente)httpServletRequest.getSession().getAttribute("registro"));
            id_usuario = (int) usuario.getIdUsuario();
            nombre_usuario = usuario.getNombreUsuario();
            correo = usuario.getCorreo();
            contraseña = usuario.getContraseña();
        }
    }

    public String getConfirmaContraseña() {
        return confirmaContraseña;
    }

    public void setConfirmaContraseña(String confirmaContraseña) {
        this.confirmaContraseña = confirmaContraseña;
    }

    /**
     * @return the id_usuario
     */
    public int getId_usuario() {
        return id_usuario;
    }

    /**
     * @param id_usuario the id_usuario to set
     */
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    /**
     * @return the nombre_usuario
     */
    public String getNombre_usuario() {
        return nombre_usuario;
    }

    /**
     * @param nombre_usuario the nombre_usuario to set
     */
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    /**
     * Método para registrar un nuevo usuario en la tabla Pendientes
     */
    public String registrar(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");  //Realiza la conexión a la BD 
        id_usuario = new PendienteC(emf).getPendienteCount() + 1;
        correo = correo + "@ciencias.unam.mx";
        PendienteC controlador = new PendienteC(emf);
        Pendiente usuario = new Pendiente(nombre_usuario, id_usuario, correo, contraseña);
        try{
            controlador.create(usuario);
        }catch(Exception e){
            System.out.println("Error al Registrar");
            
        }
        return "mensajeCorreo";
    }
    
}