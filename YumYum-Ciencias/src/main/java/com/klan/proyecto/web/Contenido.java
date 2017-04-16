/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import com.klan.proyecto.modelo.Puesto; // Para construir un puesto.
import com.klan.proyecto.modelo.Evaluacion; // Para construir evaluaciones.

import java.util.List; // Para guardar listas obtenidas de consultas.
import javax.annotation.PostConstruct; // Para realizar tareas una vez que se crea la instancia.
import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.

/**
 * Clase bean que implementa la evaluación de un puesto y la consulta de su contenido.
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class Contenido implements Serializable{

    private Puesto puesto; // Puesto del que se muestra el contenido.
    private int calificacionGlobal = 0; // Calificación calculada del puesto.
    private final HttpServletRequest httpServletRequest; // Obtiene información de las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación

    /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Contenido() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
    }
    
    @PostConstruct
    public void init() {
        Puesto p = (Puesto)httpServletRequest.getSession().getAttribute("puesto");
        puesto = (p != null)? p : new Puesto(); // Se inicializa con el puesto encontrado o por defecto.
        // Se calcula la evaluación global del puesto.
        List<Evaluacion> evaluaciones = getPuesto().getEvaluacionList();
        if (evaluaciones != null && evaluaciones.size() > 0) {
            calificacionGlobal = 0;
            for (Evaluacion e : evaluaciones) calificacionGlobal += e.getCalificacion();
            calificacionGlobal /= evaluaciones.size();
        }
    }
    
    /**
     * Método de acceso al puesto elegido por el usuario.
     * @return Devuelve el puesto que el usuario haya elegido.
     */
    public Puesto getPuesto() {
        return puesto;
    }

    /**
     * Calificacion del puesto segun las evaluaciones de los usuarios.
     * @return Devuelve el número de estrellas en promedio.
     */
    public int getCalificacionGlobal() {
        return calificacionGlobal;
    }

    /**
     * Metodo que indica si hay comida disponible.
     * @return Devuelve true si hay comida en el puesto, falso en otro caso.
     */
    public boolean comidaDisponible() {
        return getPuesto().getComidaPuestoList().size() > 0;
    }

    /**
     * Metodo que decide las evaluaciones que se muestran según el contenido disponible para un puesto.
     * @return Devuelve el nombre de la página correspondiente al contenido del puesto.
     */
    public boolean evaluacionesDisponibles() {
        return getPuesto().getEvaluacionList().size() > 0;
    }

    /**
     * Método que actualiza el contenido.
     * @return Indica que se ha actualizado.
     */
    public boolean actualiza() {
        init(); return puesto != null;
    }
}