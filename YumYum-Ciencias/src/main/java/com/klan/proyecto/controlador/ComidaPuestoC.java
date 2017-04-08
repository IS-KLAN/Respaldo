/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import com.klan.proyecto.modelo.ComidaPuesto;
import com.klan.proyecto.modelo.ComidaPuestoPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
            comidaPuesto.setComidaPuestoPK(new ComidaPuestoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comidaPuesto = em.merge(comidaPuesto);
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
