/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import java.util.List;

import com.klan.proyecto.controlador.EvaluacionC;
import com.klan.proyecto.controlador.UsuarioC;
import com.klan.proyecto.controlador.PuestoC;
import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import com.klan.proyecto.modelo.Evaluacion;
import com.klan.proyecto.modelo.EvaluacionPK;
import com.klan.proyecto.modelo.Puesto;
import com.klan.proyecto.modelo.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;

/**
 *
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class Contenido implements Serializable{

    private Usuario usuario; // Usario que va a comentar.
    private Puesto puesto; // Puesto del que se muestra el contenido.
    private String comentario = ""; // Texto del comentario a guardar.
    private int calificacion = 0; // Calificación que dará el usuario.
    private int calificacionGlobal = 0; // Calificación calculada del puesto.
    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación

    /**
     * Constructor que inicializa las instancias de FaceContext y HttpServerRequest, así como
     * las variables necesarias para las consultas.
     */
    public Contenido() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("puesto") != null) {
            Puesto p = ((Puesto)httpServletRequest.getSession().getAttribute("puesto"));
            System.out.println("Nombre de puesto cargado: " + p.getNombrePuesto());
            puesto = new PuestoC(emf).findPuesto(p.getNombrePuesto());
        } else puesto = new Puesto();
        if (httpServletRequest.getSession().getAttribute("usuario") != null) {
            Usuario u = ((Usuario)httpServletRequest.getSession().getAttribute("usuario"));
            usuario = new UsuarioC(emf).findUsuario(u.getNombreUsuario());
        } else usuario = new Usuario();
    }
    
    @PostConstruct
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        EvaluacionPK id = new EvaluacionPK(puesto.getNombrePuesto(), usuario.getNombreUsuario());
        Evaluacion actual = new EvaluacionC(emf).findEvaluacion(id);
        calificacion = (actual != null)? actual.getCalificacion() : 0;
        comentario = (actual != null)? actual.getComentario() : "";
        List<Evaluacion> evaluaciones = puesto.getEvaluacionList();
        if (evaluaciones != null && evaluaciones.size() > 0) {
            calificacionGlobal = 0;
            for (Evaluacion e : evaluaciones) calificacionGlobal += e.getCalificacion();
            calificacionGlobal /= evaluaciones.size();
        }
    }

    /**
     * Método de acceso al usuario de la sesión actual.
     * @return El usuario que ha iniciado sesión.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establecer el usuario de la sesión actual.
     * @param usuario Es el usuario correspondiente a la sesión actual.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Método de acceso al puesto elegido por el usuario.
     * @return Devuelve el puesto que el usuario haya elegido.
     */
    public Puesto getPuesto() {
        return puesto;
    }

    /**
     * Establecer el puesto elegido por el usuario.
     * @param puesto El puesto que se haya elegido.
     */    
    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    /**
     * Método de acceso al comentario que se ha escrito en la interfaz.
     * @return Devuelve el mensaje comentado en la interfaz.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Método que establece el comentario del comentario que será insertado.
     * @param comentario Texto ingresado para ser guardado.
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }    

    /**
     * Método de acceso a la calificacion que se asigna en la interfaz.
     * @return Devuelve la calificacion asignada en la interfaz.
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Método que establece la calificacion que será insertada.
     * @param calificacion Puntaje ingresado para ser guardado.
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }    

    /**
     * Calificacion del puesto segun las evaluaciones de los usuarios.
     * @return Devuelve el número de estrellas en promedio.
     */
    public int getCalificacionGlobal() {
        return calificacionGlobal;
    }

    /**
     * Se establece la calificacion global del puesto.
     * @param calificacionGlobal Calificacion promedio del puesto.
     */
    public void setCalificacionGlobal(int calificacionGlobal) {
        this.calificacionGlobal = calificacionGlobal;
    }

    /**
     * Método que se encarga de capturar el comentario ingresado en la interfaz.
     */
    public void comentar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        EvaluacionC controlador = new EvaluacionC(emf);
        EvaluacionPK id = new EvaluacionPK(puesto.getNombrePuesto(), usuario.getNombreUsuario());
        Evaluacion actual = new Evaluacion(id, comentario, calificacion);
        Evaluacion encontrada = controlador.findEvaluacion(id);
        try{
            actual.setPuesto(puesto);
            actual.setUsuario(usuario);
            if (encontrada != null) {
                actual.setIdEvaluacion(encontrada.getIdEvaluacion());
                System.out.println("Se intenta editar la evaluación: " + actual.getIdEvaluacion());
                controlador.edit(actual);
            } else {
                System.out.println("Se intenta crear la evaluación: " + actual.getIdEvaluacion());
                controlador.create(actual);
            } // Para actualizar las listas de comida y evaluaciones se actualiza el puesto.
            puesto = new PuestoC(emf).findPuesto(puesto.getNombrePuesto());
            init();
        }catch(NonexistentEntityException neex){
            System.out.println("No se pudo editar la evaluación." + neex.getMessage());
        }catch(PreexistingEntityException peex){
            System.out.println("No se pudo crear la evaluación." + peex.getMessage());
        }catch(Exception ex){
            System.out.println("Error desconocido: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
            (FacesMessage.SEVERITY_INFO, "Error al guardar el comentario:" + actual.toString(), null));
        }finally{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage
            (FacesMessage.SEVERITY_INFO, "Comentario Guardado.", null));
        }
    }
    
    /**
     * Método que restablece la evaluación obtenida al momento.
     */
    public void cancelar() {
        calificacion = 0;
        comentario = "";
    }

    /**
     * Metodo que decide la comida que se muestra según el contenido disponible para un puesto.
     * @return Devuelve el nombre de la página correspondiente al contenido del puesto.
     */
    public String comidaDisponible() {
        if (puesto.getComidaPuestoList().size() > 0) return "comida";
        return "noHayContenidoDisponible";
    }

    /**
     * Metodo que decide las evaluaciones que se muestran según el contenido disponible para un puesto.
     * @return Devuelve el nombre de la página correspondiente al contenido del puesto.
     */
    public String evaluacionesDisponibles() {
        if(puesto.getEvaluacionList().size() > 0) return "evaluaciones";
        return "noHayContenidoDisponible";
    }

    /**
     * Metodo que decide la comida que se muestra según el contenido disponible para un puesto.
     * @return Devuelve el nombre de la página correspondiente al contenido del puesto.
     */
    public String comentarioDisponible() {
        if(httpServletRequest.getSession().getAttribute("usuario") != null) return "comentarioDisponible";
        return "comentarioNulo";
    }
    
    /**
     * Metodo que decide la comida que se muestra según el contenido disponible para un puesto.
     * @return Devuelve el nombre de la página correspondiente al contenido del puesto.
     */
    public String contenidoDisponible() {
        if(httpServletRequest.getSession().getAttribute("puesto") != null) return "perfilPuesto";
        return "noHayContenidoDisponible";
    }
}