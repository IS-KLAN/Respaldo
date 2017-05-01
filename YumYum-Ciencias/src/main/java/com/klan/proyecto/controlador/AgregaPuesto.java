/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;


import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;

import com.klan.proyecto.jpa.ComidaC;
import com.klan.proyecto.jpa.ComidaPuestoC;
import com.klan.proyecto.jpa.PuestoC;
import com.klan.proyecto.modelo.Comida;
import com.klan.proyecto.modelo.ComidaPuesto;
import com.klan.proyecto.modelo.Puesto;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author miguel
 */
@ManagedBean
@ViewScoped
public class AgregaPuesto implements Serializable {

    private MapModel modelo;
    private Marker marcador;
    private List<String> lista, seleccion;
    private String nombre, descripcion, rutaImagen, lat, lng;
    private UploadedFile archivo;
    private Double latitud;
    private Double longitud;

    @PostConstruct
    public void cargar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias");
        List<Puesto> lugares = new PuestoC(emf).buscaPuestos();
        modelo = new DefaultMapModel();
        for (Puesto lugar : lugares) {
            Double latitud = Double.parseDouble(lugar.getLatitud());
            Double longitud = Double.parseDouble(lugar.getLongitud());
            String nombre = lugar.getNombre();
            System.out.println(latitud + ", " + longitud + ", " + nombre);
            modelo.addOverlay(new Marker(new LatLng(latitud, longitud), nombre));
        } seleccion = new ArrayList<>(); // Se inicializan listas de lista y selección
        lista = new ArrayList<>(); 
        List<Comida> todas = new ComidaC(emf).buscaComidas();
        for (Comida c : todas) lista.add(c.getNombre());
    }

    public List<String> getLista() {
        return lista;
    }

    public List<String> getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(List<String> seleccion) {
        this.seleccion = seleccion;
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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
        this.lat = latitud.toString();
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
        this.lng = longitud.toString();
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public MapModel getModelo() {
        return modelo;
    }

    public Marker getMarcador() {
        return marcador;
    }

    /**
     * Método que se encarga de avisar al usuario que la ubicación ya ha sido registrada.
     * @param event  Es el evento que se registra al tocar el mapa.
     */
    public void alMarcarLugar(OverlaySelectEvent event) {
        try{
            marcador = (Marker) event.getOverlay();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_WARN, "Eliga otra ubicación", "Ubicación ocupada"));            
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Error de interacción con el mapa.", ex.getMessage()));
            System.err.println("\n" + ex.getMessage());            
        }
    }

    /**
     * Método que se encarga de agregar un marcador al mapa.
     */
    public void marcar() {
        try{
            Marker marker = new Marker(new LatLng(latitud, longitud), nombre);
            modelo.addOverlay(marker);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Ubicación definida.", "Lat:" + latitud + ", Lng:" + longitud));
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_INFO, "Error al definir la ubicación.", null));
            System.err.println("\n" + ex.getMessage());
        }
    }

    /**
     * Método para guardar una imagen si es necesario.
     * @return Devuelve TRUE si todo se llevo a cabo sin error. FALSE en otro caso.
     */
    public boolean guardaImagen() {
        final String dir = System.getProperty("user.dir").replace("\\", "/"); // Directorio de ejecución actual.
        final String sub = "/src/main/webapp/resources"; // Directorio especificado para guardar imagenes. 
        if (archivo != null && archivo.getSize() > 0) { // Sólo si se intenta cargar una rutaImagen.
            rutaImagen = nombre + "-" + latitud + ".jpg"; // Se define el nombrePuesto de la rutaImagen.
            try { // EL proceso de escritura en archivos puede lanzar excepciones.
                File f = new File(dir + sub, rutaImagen); // Se define el Directorio y Nombre con extensión del file.
                FileOutputStream output = new FileOutputStream(f); // Flujo de escritura para guardar la rutaImagen.
                InputStream input = archivo.getInputstream(); // Flujo de lectura para cargar el archivo subido.
                int read = 0; // Bandera para saber si se sigue leyendo bytes del archivo subido.
                byte[] bytes = new byte[1024]; // Buffer para cargar bloques de 1024 bytes (1 MegaByte).
                while ((read = input.read(bytes)) != -1) output.write(bytes, 0, read); // Se escribe el archivo con lo que se lee.
                System.out.println("Imagen guardada en: " + sub + "/" + rutaImagen);
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Error al guardar la imagen", null));
                System.err.println("Error al guardar la imagen\n" + ex.getMessage());
                return false;
            }
        } else {
            rutaImagen = "default.jpg";
            System.out.println("No se cargó imagen :(");
        } return true;
    }
    
    /**
     * Método para registrar un nuevo usuario en la tabla Pendientes
     */
    public String agregar() {
        try{ 
            if (latitud == null || longitud == null) throw new NullPointerException("Debe definirse la ubicación del puesto.");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("YumYum-Ciencias"); //Realiza la conexión a la BD 
            PuestoC pc = new PuestoC(emf); // Se utiliza para consultas a la tabla Puesto.
            if(guardaImagen()) { // Si no ocurrieron errores al tratar de guardar la rutaImagen.
                Puesto puesto = new Puesto(nombre, descripcion, lat, lng, rutaImagen); // Se crea el nuevo puesto.
                pc.crear(puesto); // Se agrega el puesto a la BD.
                ComidaPuestoC cpc = new ComidaPuestoC(emf); // Para agregar las relaciones de comida.
                for (String comida : seleccion) { // Se agregan las relaciones y comida nueva del puesto.
                    cpc.crear(new ComidaPuesto(comida, nombre)); // Se crea la relación en la tabla comidaPuesto.
                } // Se avisa al usuario que el puesto ha sido agregado.
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_INFO, "Puesto agregado con éxito!!!.", null));
                // Se asegura que el mensaje se muestre después de la redirección.
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                puesto = new PuestoC(emf).buscaNombre(nombre); //Se actualiza el puesto.
                ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("puesto", puesto);
                return "perfilPuesto?faces-redirect=true";
            } // Si no se guardo bien la rutaImagen, no se agrega el puesto.
        }catch(Exception ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Error al agregar el puesto.", ex.getMessage()));
            System.err.println("Error al agregar el puesto\n" + ex.getMessage());
        } return "agregaPuesto";
    }
}