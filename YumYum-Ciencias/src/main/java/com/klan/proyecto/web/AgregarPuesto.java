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
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author nancy
 */
@ManagedBean
@RequestScoped
public class AgregarPuesto {
    private int id_puesto;
    private String nombre_puesto;
    private String descripcion;
    private String[] comida_seleccionada;
    private List<Comida> comida;
    private UploadedFile archivo;
    
    /**
     * Creates a new instance of AgregarPuesto
     */
    public AgregarPuesto() {
    }


    public int getId_puesto() {
        return id_puesto;
    }

    public void setId_puesto(int id_puesto) {
        this.id_puesto = id_puesto;
    }

    public String getNombre_puesto() {
        return nombre_puesto;
    }

    public void setNombre_puesto(String nombre_puesto) {
        this.nombre_puesto = nombre_puesto;
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
    
    public List<Comida> encuentraComida(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        comida= new ComidaC(emf).findComidaEntities();
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
            imagen = archivo.getFileName();
            p.setRutaImagen(imagen);
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
