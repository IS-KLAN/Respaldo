/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.jpa;

import com.klan.proyecto.jpa.excepciones.EntidadInexistenteException;
import com.klan.proyecto.jpa.excepciones.EntidadExistenteException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.Comida;
import com.klan.proyecto.modelo.ComidaPuesto;
import com.klan.proyecto.modelo.ComidaPuestoP;
import com.klan.proyecto.modelo.Puesto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author patlani
 */
public class ComidaPuestoC implements Serializable {

    public ComidaPuestoC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void crear(ComidaPuesto comidaPuesto) throws EntidadExistenteException, Exception {
        if (comidaPuesto.getLlave() == null) {
            throw new NullPointerException("No se encuentra una llave de comidaPuesto definida.");
        } ComidaPuestoP pk = comidaPuesto.getLlave();
        EntityManager em = null;
        try {
            comidaPuesto.setId(cantidadDeEntidades() + 1);
            em = getEntityManager();
            em.getTransaction().begin();            
            Comida comida = em.getReference(Comida.class, pk.getNombreComida());
            if (comida == null) {
                throw new NullPointerException("Llave de referencia inválida de comida.");
            } else comidaPuesto.setComida(comida);
            Puesto  puesto = em.getReference(Puesto.class, pk.getNombrePuesto());
            if (comida == null) {
                throw new NullPointerException("Llave de referencia inválida de puesto.");
            } else comidaPuesto.setPuesto(puesto);
            comida.getListaRelacionada().add(comidaPuesto);
            puesto.getComida().add(comidaPuesto);
            em.merge(comida);
            em.merge(puesto);
            em.persist(comidaPuesto);
            em.getTransaction().commit();
            // System.out.println("Relación agregada: " + comida.getNombreComida() + " a " + puesto.getNombrePuesto());
        } catch (Exception ex) {
            if (buscaId(comidaPuesto.getLlave()) != null) {
                throw new EntidadExistenteException("ComidaPuesto " + comidaPuesto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editar(ComidaPuesto comidaPuesto) throws EntidadInexistenteException, Exception {
        comidaPuesto.getLlave().setNombrePuesto(comidaPuesto.getPuesto().getNombre());
        comidaPuesto.getLlave().setNombreComida(comidaPuesto.getComida().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComidaPuesto persistentComidaPuesto = em.find(ComidaPuesto.class, comidaPuesto.getLlave());
            Comida comidaOld = persistentComidaPuesto.getComida();
            Comida comidaNew = comidaPuesto.getComida();
            Puesto puestoOld = persistentComidaPuesto.getPuesto();
            Puesto puestoNew = comidaPuesto.getPuesto();
            if (comidaNew != null) {
                comidaNew = em.getReference(comidaNew.getClass(), comidaNew.getNombre());
                comidaPuesto.setComida(comidaNew);
            }
            if (puestoNew != null) {
                puestoNew = em.getReference(puestoNew.getClass(), puestoNew.getNombre());
                comidaPuesto.setPuesto(puestoNew);
            }
            comidaPuesto = em.merge(comidaPuesto);
            if (comidaOld != null && !comidaOld.equals(comidaNew)) {
                comidaOld.getListaRelacionada().remove(comidaPuesto);
                comidaOld = em.merge(comidaOld);
            }
            if (comidaNew != null && !comidaNew.equals(comidaOld)) {
                comidaNew.getListaRelacionada().add(comidaPuesto);
                comidaNew = em.merge(comidaNew);
            }
            if (puestoOld != null && !puestoOld.equals(puestoNew)) {
                puestoOld.getComida().remove(comidaPuesto);
                puestoOld = em.merge(puestoOld);
            }
            if (puestoNew != null && !puestoNew.equals(puestoOld)) {
                puestoNew.getComida().add(comidaPuesto);
                puestoNew = em.merge(puestoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ComidaPuestoP id = comidaPuesto.getLlave();
                if (buscaId(id) == null) {
                    throw new EntidadInexistenteException("No existe comidaPuesto con id " + id);
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void borrar(ComidaPuestoP id) throws EntidadInexistenteException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComidaPuesto comidaPuesto;
            try {
                comidaPuesto = em.getReference(ComidaPuesto.class, id);
                comidaPuesto.getLlave();
            } catch (EntityNotFoundException enfe) {
                throw new EntidadInexistenteException("No existe comidaPuesto with id " + id, enfe);
            }
            Comida comida = comidaPuesto.getComida();
            if (comida != null) {
                comida.getListaRelacionada().remove(comidaPuesto);
                comida = em.merge(comida);
            }
            Puesto puesto = comidaPuesto.getPuesto();
            if (puesto != null) {
                puesto.getComida().remove(comidaPuesto);
                puesto = em.merge(puesto);
            }
            em.remove(comidaPuesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComidaPuesto> buscaEntidades() {
        return buscaEntidades(true, -1, -1);
    }

    public List<ComidaPuesto> buscaEntidades(int maxResults, int firstResult) {
        return buscaEntidades(false, maxResults, firstResult);
    }

    private List<ComidaPuesto> buscaEntidades(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComidaPuesto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Método que busca una relación entra una comida y un puesto, a partir de su llave primaria.
     * @param id Es la llave primaria de la relación buscada.
     * @return Devuelve la entidad encontrada en la BD, o NULL en caso de encontrarla.
     */
    public ComidaPuesto buscaId(ComidaPuestoP id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComidaPuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int cantidadDeEntidades() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComidaPuesto> rt = cq.from(ComidaPuesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
