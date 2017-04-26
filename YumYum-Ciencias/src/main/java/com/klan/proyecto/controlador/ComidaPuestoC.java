/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.Comida;
import com.klan.proyecto.modelo.ComidaPuesto;
import com.klan.proyecto.modelo.ComidaPuestoPK;
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

    public void create(ComidaPuesto comidaPuesto) throws PreexistingEntityException, Exception {
        if (comidaPuesto.getComidaPuestoPK() == null) {
            throw new NullPointerException("No se encuentra una llave de comidaPuesto definida.");
        } ComidaPuestoPK pk = comidaPuesto.getComidaPuestoPK();
        EntityManager em = null;
        try {
            comidaPuesto.setIdComidaPuesto(getComidaPuestoCount() + 1);
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
            em.persist(comidaPuesto);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComidaPuesto(comidaPuesto.getComidaPuestoPK()) != null) {
                throw new PreexistingEntityException("ComidaPuesto " + comidaPuesto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComidaPuesto comidaPuesto) throws NonexistentEntityException, Exception {
        comidaPuesto.getComidaPuestoPK().setNombrePuesto(comidaPuesto.getPuesto().getNombrePuesto());
        comidaPuesto.getComidaPuestoPK().setNombreComida(comidaPuesto.getComida().getNombreComida());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComidaPuesto persistentComidaPuesto = em.find(ComidaPuesto.class, comidaPuesto.getComidaPuestoPK());
            Comida comidaOld = persistentComidaPuesto.getComida();
            Comida comidaNew = comidaPuesto.getComida();
            Puesto puestoOld = persistentComidaPuesto.getPuesto();
            Puesto puestoNew = comidaPuesto.getPuesto();
            if (comidaNew != null) {
                comidaNew = em.getReference(comidaNew.getClass(), comidaNew.getNombreComida());
                comidaPuesto.setComida(comidaNew);
            }
            if (puestoNew != null) {
                puestoNew = em.getReference(puestoNew.getClass(), puestoNew.getNombrePuesto());
                comidaPuesto.setPuesto(puestoNew);
            }
            comidaPuesto = em.merge(comidaPuesto);
            if (comidaOld != null && !comidaOld.equals(comidaNew)) {
                comidaOld.getComidaPuestoList().remove(comidaPuesto);
                comidaOld = em.merge(comidaOld);
            }
            if (comidaNew != null && !comidaNew.equals(comidaOld)) {
                comidaNew.getComidaPuestoList().add(comidaPuesto);
                comidaNew = em.merge(comidaNew);
            }
            if (puestoOld != null && !puestoOld.equals(puestoNew)) {
                puestoOld.getComidaPuestoList().remove(comidaPuesto);
                puestoOld = em.merge(puestoOld);
            }
            if (puestoNew != null && !puestoNew.equals(puestoOld)) {
                puestoNew.getComidaPuestoList().add(comidaPuesto);
                puestoNew = em.merge(puestoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ComidaPuestoPK id = comidaPuesto.getComidaPuestoPK();
                if (findComidaPuesto(id) == null) {
                    throw new NonexistentEntityException("The comidaPuesto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ComidaPuestoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComidaPuesto comidaPuesto;
            try {
                comidaPuesto = em.getReference(ComidaPuesto.class, id);
                comidaPuesto.getComidaPuestoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comidaPuesto with id " + id + " no longer exists.", enfe);
            }
            Comida comida = comidaPuesto.getComida();
            if (comida != null) {
                comida.getComidaPuestoList().remove(comidaPuesto);
                comida = em.merge(comida);
            }
            Puesto puesto = comidaPuesto.getPuesto();
            if (puesto != null) {
                puesto.getComidaPuestoList().remove(comidaPuesto);
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

    public List<ComidaPuesto> findComidaPuestoEntities() {
        return findComidaPuestoEntities(true, -1, -1);
    }

    public List<ComidaPuesto> findComidaPuestoEntities(int maxResults, int firstResult) {
        return findComidaPuestoEntities(false, maxResults, firstResult);
    }

    private List<ComidaPuesto> findComidaPuestoEntities(boolean all, int maxResults, int firstResult) {
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
    public ComidaPuesto findComidaPuesto(ComidaPuestoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComidaPuesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getComidaPuestoCount() {
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
