/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import java.util.List;

import com.klan.proyecto.controlador.PuestoC; // Para consultar la lista de puestos.
import com.klan.proyecto.modelo.Puesto; // Para definir puestos.

import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import javax.persistence.EntityManagerFactory; // Para conectarse a la BD.
import javax.persistence.Persistence; // Para definir los parámetros de conexión a la BD.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.
import javax.annotation.PostConstruct; // Para realizar tareas una vez que se crea la instancia.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.

/**
 * Clase que implemenenta la vista en carrusel de los puestos en la página principal.
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class Carrusel implements Serializable{
    
    private Puesto primero; // Primer elemento de la lista de puestos en la BD.
    private List<Puesto> puestos; // Resto de la lista de puestos en la BD.
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones.
    private final FacesContext faceContext; // Obtiene información de la aplicación.

    /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Carrusel() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    @PostConstruct
    public void init() {
        // Se realiza la conexión a la BD.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        // Se realiza la consulta de todos los puestos en la BD con un JPA de Puesto y una conexión EMF.
        puestos = new PuestoC(emf).findPuestoEntities();
        // Se separa la lista para la correcta visualización del carrusel.
        if (puestos.size() < 1) {
            // Si no se tienen puestos disponibles se inicializa el primero por defecto.
            primero = new Puesto();
            primero.setRutaImagen("default.jpg");
            // En otro caso, se toma el primer elemento de la lista.
        } else primero = puestos.remove(0);
    }
    
    /**
     * Acceso al primer elemento de la lista de puestos.
     * @return Devuelve el primer puesto en la tabla.
     */
    public Puesto getPrimero() {
        return primero;
    }

    /**
     * Establece el primer puesto de la lista.
     * @param primero Es el primer puesto que se va a mostrar.
     */
    public void setPrimero(Puesto primero) {
        this.primero = primero;
    }
    
    /**
     * Acceso a la lista de puestos a mostrar.
     * @return Devuelve la lista de puestos guardados.
     */
    public List<Puesto> getPuestos() {
        return puestos;
    }

    /**
     * Establece una lista de puestos.
     * @param puestos  Es la lista que se establece.
     */
    public void setPuestos(List<Puesto> puestos) {
        this.puestos = puestos;
    }    

    /**
     * Método que guarda el primer puesto como seleccionado para redirigir al un perfil de prueba.
     */
    public void perfilDemo() {
        httpServletRequest.getSession().setAttribute("puesto", primero);
    }
}