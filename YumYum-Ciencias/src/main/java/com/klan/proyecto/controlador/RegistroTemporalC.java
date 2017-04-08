/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import com.klan.proyecto.modelo.RegistroTemporal;
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
public class RegistroTemporalC implements Serializable {

    public RegistroTemporalC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroTemporal registroTemporal) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(registroTemporal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistroTemporal(registroTemporal.getCorreo()) != null) {
                throw new PreexistingEntityException("RegistroTemporal " + registroTemporal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroTemporal registroTemporal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            registroTemporal = em.merge(registroTemporal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = registroTemporal.getCorreo();
                if (findRegistroTemporal(id) == null) {
                    throw new NonexistentEntityException("The registroTemporal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroTemporal registroTemporal;
            try {
                registroTemporal = em.getReference(RegistroTemporal.class, id);
                registroTemporal.getCorreo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroTemporal with id " + id + " no longer exists.", enfe);
            }
            em.remove(registroTemporal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroTemporal> findRegistroTemporalEntities() {
        return findRegistroTemporalEntities(true, -1, -1);
    }

    public List<RegistroTemporal> findRegistroTemporalEntities(int maxResults, int firstResult) {
        return findRegistroTemporalEntities(false, maxResults, firstResult);
    }

    private List<RegistroTemporal> findRegistroTemporalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroTemporal.class));
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

    public RegistroTemporal findRegistroTemporal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroTemporal.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroTemporalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroTemporal> rt = cq.from(RegistroTemporal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
