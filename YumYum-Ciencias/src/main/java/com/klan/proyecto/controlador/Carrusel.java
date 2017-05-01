/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import java.util.List;

import com.klan.proyecto.jpa.PuestoC; // Para consultar la lista de puestos.
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
    private boolean hayPuestos;

    /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Carrusel() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }

    @PostConstruct
    public void cargar() {
        // Se realiza la conexión a la BD.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        // Se realiza la consulta de todos los puestos en la BD con un C de Puesto y una conexión EMF.
        puestos = new PuestoC(emf).buscaPuestos();
        // Se separa la lista para la correcta visualización del carrusel.
        if (hayPuestos = puestos.size() > 0)  primero = puestos.remove(0);
        for (Puesto p : puestos) System.out.println("Imagen cargada: " + p.getRutaImagen());
    }
    
    /**
     * Acceso al nombre del primer elemento de la lista de puestos.
     * @return Devuelve el nombre del primer puesto en la tabla.
     */
    public String primerNombre() {
        return primero.getNombre();
    }
    
    /**
     * Acceso a la imagen del primer elemento de la lista de puestos.
     * @return Devuelve la ruta de la imagen del primer puesto en la tabla.
     */
    public String primerImagen() {
        return primero.getRutaImagen();
    }
    
    /**
     * Acceso a la desxripción del primer elemento de la lista de puestos.
     * @return Devuelve la descripción del primer puesto en la tabla.
     */
    public String primerDescripcion() {
        return primero.getDescripcion();
    }

    /**
     * Acceso a la lista de puestos a mostrar.
     * @return Devuelve la lista de puestos guardados.
     */
    public List<Puesto> getPuestos() {
        return puestos;
    }

    public boolean hayPuestos() {
        return hayPuestos;
    }
    
    /**
     * Método que guarda el primer puesto como seleccionado para redirigir al un selecciona del primer puesto.
     * @return Devuelve la página del selecciona del puesto a la que se redirigie.
     */
    public String primerSeleccion() {
        // Se guarda el primer puesto para ser mostrado en perfilPuesto.
        httpServletRequest.getSession().setAttribute("puesto", primero);
        // Se redirige al perfilPuesto con redirect=true para actualizar la url.
        return "perfilPuesto?faces-redirect=true";
    }

    /**
     * Método que guarda un puesto como seleccionado.
     * @param p Puesto que se guarda para mostrar su contenido.
     * @return Devuelve la página del selecciona del puesto a la que se redirigie.
     */
    public String selecciona(Puesto p) {
        // Se guarda el puesto p para ser mostrado en perfilPuesto.
        httpServletRequest.getSession().setAttribute("puesto", p);
        // Se redirige al perfilPuesto con redirect=true para actualizar la url.
        return "perfilPuesto?faces-redirect=true";
    }
}