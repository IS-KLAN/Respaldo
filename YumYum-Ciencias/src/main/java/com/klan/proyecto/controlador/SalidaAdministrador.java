/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import javax.faces.application.FacesMessage; // Para mostrar y obtener mensajes de avisos.
import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.

/**
 *
 * Bean utilizado para pruebas al perfil de un puesto.
 * @author anahiqj
 */
@ManagedBean // LEER LA DOCUMENTACIÖN DE ESTA ANOTACIÓN: Permite dar de alta al bean en la aplicación
@RequestScoped // Sólo está disponible a partir de peticiones al bean
public class SalidaAdministrador implements Serializable{

    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de correo.
    private final FacesContext faceContext; // Obtiene información de la aplicación
    private FacesMessage message;

    /**
     * Constructor para inicializar los valores de faceContext y
     * httpServletRequest.
     */
    public SalidaAdministrador() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    /**
     * Método encargado de cerrar la sesión.
     */
    public void cerrar() {
            //System.out.println("Cerrando la sesión.");
            httpServletRequest.getSession().removeAttribute("administrador"); // Se borra al usuario.
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Salida satisfactoria.", null);
            faceContext.addMessage(null, message); // Se agrega el mensaje.
            // Se asegura que el mensaje se muestre después de la redirección.
            faceContext.getExternalContext().getFlash().setKeepMessages(true);
    }
}