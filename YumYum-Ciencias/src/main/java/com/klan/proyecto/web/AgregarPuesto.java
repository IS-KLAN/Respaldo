/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import com.klan.proyecto.controlador.ComidaC;
import com.klan.proyecto.controlador.PuestoC;
import com.klan.proyecto.modelo.Comida;
import com.klan.proyecto.modelo.Puesto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author nancy
 */
@ManagedBean
@RequestScoped
public class AgregarPuesto {
    
    private MapModel advancedModel;
    private OverlaySelectEvent event;
    private Marker marker;
    private double lat;
    private double lng;
    private int id_puesto;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String[] comida_seleccionada;
    private List<Comida> comida;
    private UploadedFile archivo;


    private PuestoC puestoC;

    private final HttpServletRequest httpServletRequest; // Obtiene información de todas las peticiones de usuario.
    private final FacesContext faceContext; // Obtiene información de la aplicación

    /**
     * Creates a new instance of AgregarPuesto
     */
    public AgregarPuesto() {
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("agregar") != null) {
            Puesto puesto = ((Puesto) httpServletRequest.getSession().getAttribute("agregar"));
            id_puesto = (int) puesto.getIdPuesto();
            nombre = puesto.getNombrePuesto();
            descripcion = puesto.getDescripcion();
            imagen = puesto.getRutaImagen();
        }
    }

    @PostConstruct
    public void init() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        advancedModel = new DefaultMapModel();
        puestoC = new PuestoC(emf);
        //archivo = new U;
        List<Puesto> lugares = puestoC.findPuestoEntities();
        for (Puesto lugar : lugares) {
            Double latitud = Double.parseDouble(lugar.getLatitud());
            Double longitud = Double.parseDouble(lugar.getLongitud());
            String nombre = lugar.getNombrePuesto();
            System.out.println(latitud + ", " + longitud + ", " + nombre);
            advancedModel.addOverlay(new Marker(new LatLng(latitud, longitud), nombre));
        }
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Comida> getComida() {
        return comida;
    }

    public void setComida(List<Comida> comida) {
        this.comida = comida;
    }

    public String[] getComida_seleccionada() {
        return comida_seleccionada;
    }

    public void setComida_seleccionada(String[] comida_seleccionada) {
        this.comida_seleccionada = comida_seleccionada;
    }

    public List<Comida> encuentraComida() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        comida = new ComidaC(emf).findComidaEntities();
        return comida;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }
    
    
   /**
     * Método para registrar un nuevo Puesto
     *
     */
    public void agregar() {
        Puesto p = new Puesto();
        final String dir = System.getProperty("user.dir").replace("\\", "/"); // Directorio de ejecución actual.
        final String sub = "/src/main/webapp/resources"; // Directorio especificado para guardar imagenes.
        final String ext = ".jpg"; // Se define la extensión del archivo.
        
        //Cargar la imagen en la BD
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");  //Realiza la conexión a la BD 
        if (archivo != null) {
            FacesMessage mensaje = new FacesMessage("Éxito", archivo.getFileName() + " al subir la imagen");
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            p.setRutaImagen(nombre+ext);
            try { // EL proceso de escritura en archivos puede lanzar excepciones.
                File f = new File(dir + sub, nombre + ext); // Se define el Directorio y Nombre con extensión del file.
                FileOutputStream output = new FileOutputStream(f); // Flujo de escritura para guardar la imagen.
                InputStream input = archivo.getInputstream(); // Flujo de lectura para cargar el archivo subido.
                int read = 0; // Bandera para saber si se sigue leyendo bytes del archivo subido.
                byte[] bytes = new byte[1024]; // Buffer para cargar bloques de 1024 bytes (1 MegaByte).
                while ((read = input.read(bytes)) != -1) output.write(bytes, 0, read); // Se escribe el archivo con lo que se lee.
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Imagen guardada con éxito!!!", "Checa la carpeta .../resources"));
            }catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error al guardar la imagen", ex.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Error al cargar la imagen", null));
        }
        
        p.setNombrePuesto(nombre);
        p.setDescripcion(descripcion);
        p.setLatitud(Double.toString(lat));
        p.setLongitud(Double.toString(lng));
        System.out.println(p.getNombrePuesto());
        System.out.println(p.getDescripcion());
        System.out.println(p.getRutaImagen());
        System.out.println(p.getLatitud());
        System.out.println(p.getLongitud());
    
        try{
            puestoC.create(p);
        }catch(Exception e){
            System.out.println("Error al Agregar Puesto");   
        }
    }
}
