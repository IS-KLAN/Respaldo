/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import com.klan.proyecto.controlador.PuestoC; // Para consultar puestos de la BD.
import com.klan.proyecto.controlador.UsuarioC; // Para consultar usuarios de la BD.
import com.klan.proyecto.controlador.EvaluacionC; // Para actualizar y consultar evaluaciones en la BD.
import com.klan.proyecto.modelo.Puesto; // Para construir un puesto.
import com.klan.proyecto.modelo.Usuario; // Para construir un usuario.
import com.klan.proyecto.modelo.Evaluacion; // Para construir evaluaciones.
import com.klan.proyecto.modelo.EvaluacionPK; // Para construir llaves de evaluaciones.

import java.util.List; // Para guardar listas obtenidas de consultas.
import javax.annotation.PostConstruct; // Para realizar tareas una vez que se crea la instancia.
import javax.faces.application.FacesMessage; // Para mostrar y obtener mensajes de avisos.
import javax.faces.bean.ManagedBean; // Para inyectar código dentro de un JSF.
import javax.faces.bean.RequestScoped; // Para que la instancia se conserve activa durante un request.
import javax.faces.context.FacesContext; // Para conocer el contexto de ejecución.
import javax.persistence.EntityManagerFactory; // Para conectarse a la BD.
import javax.persistence.Persistence; // Para definir los parámetros de conexión a la BD.
import javax.servlet.http.HttpServletRequest; // Para manejar datos guardados.
import java.io.Serializable; // Para conservar la persistencia de objetos que se guarden.

/**
 * Clase bean que implementa la evaluación de un puesto y la consulta de su contenido.
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class Contenido implements Serializable{

    private final Usuario usuario; // Usario que va a comentar.
    private Puesto puesto; // Puesto del que se muestra el contenido.
    private String comentario = ""; // Texto del comentario a guardar.
    private int calificacion = 0; // Calificación que dará el usuario.
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
        // Se realiza una conexión a la BD para definir al usuario actual.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        Usuario u; // Se obtienen los datos guardados del usuario.
        if ((u = (Usuario)httpServletRequest.getSession().getAttribute("usuario")) != null) {
            // Se obtiene la versión más actual del usuario en la BD.
            usuario = new UsuarioC(emf).findUsuario(u.getNombreUsuario());
        } else usuario = new Usuario(); // Se inicializa por defecto.        
    }
    
    @PostConstruct
    public void init() {
        // Se realiza una conexión a la BD.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        Puesto p; // Se obtienen los datos guardados del puesto.
        if ((p = (Puesto)httpServletRequest.getSession().getAttribute("puesto")) != null) {
            //System.out.println("Nombre de puesto cargado: " + p.getNombrePuesto());
            // Se obtiene la versión más actual del puesto en la BD.
            puesto = new PuestoC(emf).findPuesto(p.getNombrePuesto());
        } else puesto = new Puesto(); // Se inicializa por defecto.
        // Se construye la llave primaria del usuario encontrado.
        EvaluacionPK id = new EvaluacionPK(puesto.getNombrePuesto(), usuario.getNombreUsuario());
        // Se busca la evalución existente del usuario en el puesto definido.
        Evaluacion actual = new EvaluacionC(emf).findEvaluacion(id);
        // Se inicializa la última calificación asignada del usuario.
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
     * Método de acceso al puesto elegido por el usuario.
     * @return Devuelve el puesto que el usuario haya elegido.
     */
    public Puesto getPuesto() {
        return puesto;
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
     * Método que se encarga de capturar la evaluación ingresada en la interfaz.
     */
    public void comentar() {
        // Se realiza una conexión a la BD.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        // Se inicializa un JPA para realizar una consulta de evaluación.
        EvaluacionC controlador = new EvaluacionC(emf);
        // Se construye la llave primaria de la evaluación actual.
        EvaluacionPK id = new EvaluacionPK(puesto.getNombrePuesto(), usuario.getNombreUsuario());
        // Se construye la evaluación actual con su comentario, calificación y llave primaria.
        Evaluacion actual = new Evaluacion(id, comentario, calificacion);
        try{ // Se busca la existencia previa de una evaluación con el mismo usuario en el mismo puesto.
            Evaluacion encontrada = controlador.findEvaluacion(id);
            if (encontrada != null) {
                // Si se encuentra, se copia su idEvaluación y se actualiza en la BD.
                actual.setIdEvaluacion(encontrada.getIdEvaluacion());
                //System.out.println("Se intenta editar la evaluación: " + actual.getIdEvaluacion());
                controlador.edit(actual);
            } else {
                //System.out.println("Se intenta crear la evaluación: " + actual.getIdEvaluacion());
                controlador.create(actual);
            } init(); // Se recalculan las variables y listas.
        }catch(Exception ex){
            //System.err.println(ex.getMessage());
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