/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author patlani
 */
@ManagedBean
@RequestScoped
public class Upload implements Serializable{

    private String nombre;
    private UploadedFile archivo;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    /**
     * Método para registrar un nuevo usuario en la tabla Pendientes
     */
    public void guardar() {
        final String dir = System.getProperty("user.dir").replace("\\", "/"); // Directorio de ejecución actual.
        final String sub = "/src/main/webapp/resources"; // Directorio especificado para guardar imagenes.
        final String ext = ".jpg"; // Se define la extensión del archivo.
        if (archivo != null) { // Sólo se guarda si el archivo fue cargado.
            try { // EL proceso de escritura en archivos puede lanzar excepciones.
                File f = new File(dir + sub, nombre + ext); // Se define el Directorio y Nombre con extensión del file.
                FileOutputStream output = new FileOutputStream(f); // Flujo de escritura para guardar la imagen.
                InputStream input = archivo.getInputstream(); // Flujo de lectura para cargar el archivo subido.
                int read = 0; // Bandera para saber si se sigue leyendo bytes del archivo subido.
                byte[] bytes = new byte[1024]; // Buffer para cargar bloques de 1024 bytes (1 MegaByte).
                while ((read = input.read(bytes)) != -1) output.write(bytes, 0, read); // Se escribe el archivo con lo que se lee.
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Imagen guardada con éxito!!!", "Checa la carpeta .../resources"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error al guardar la imagen", ex.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Error al cargar la imagen", null));
        }
    }
}
