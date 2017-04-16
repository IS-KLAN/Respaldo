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

    private Pendiente usuario;
    private String contraseña2;
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de correo.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;

    /**
     * Constructor para inicializar los valores de faceContext y
     * httpServletRequest.
     */
    public Registro() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    /**
     * Acceso al usuario que se va a registrar.
     * @return Devuelve el usuario pendiente que se construye.
     */
    public Pendiente getUsuario() {
        return usuario;
    }

    /**
     * Acceso a la segunda contraseña ingresada.
     * @return Devuelve la segunda contraseña ingresada.
     */
    public String getContraseña2() {
        return contraseña2;
    }

    /**
     * Establece la segunda contraseña.
     * @param contraseña2 Es la segunda contraseña establecida.
     */
    public void setContraseña2(String contraseña2) {
        this.contraseña2 = contraseña2;
    }

    
    /**
     * Método encargado de ingresar los datos ingresados.
     * @return El nombre de la vista que va a responder.
     */
    public String registrar() {
        // Se realiza la conexión a la BD.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        if (contraseñaVerificada() && existenciaVerificada(emf)) {
            try{
                new PendienteC(emf).create(usuario);
            }catch(Exception ex){
                System.err.println(ex.getMessage());
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al registrar, intente de nuevo", null);
                faceContext.addMessage(null, message);
                return "registro";
            }
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha enviado la confirmación del correo.", null);
            faceContext.addMessage(null, message);
            return "index";
        } // Se informa el error si ha ocurrido algún error.
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos no válidos", null);
        faceContext.addMessage(null, message);
        return "registro";
    }
    
    public void enviarConfirmacion() {
        // Código para enviar el correo de confirmación.
    }
    
    public boolean contraseñaVerificada() {
        // Código para verificar que las 2 contraseñas coincidan.
        return false;
    }
    
    public boolean existenciaVerificada(EntityManagerFactory emf) {
        // Código para verificar que el usuario no ha sido registrado.
        return false;
    }
}