/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.IllegalOrphanException;
import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import com.klan.proyecto.modelo.Comida;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.ComidaPuesto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author patlani
 */
public class ComidaC implements Serializable {

    public ComidaC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comida comida) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            if (comida.getNombreComida() == null) throw new NullPointerException("La comida debe tener un nombre.");
            comida.setIdComida(getComidaCount() + 1);
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comida);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComida(comida.getNombreComida()) != null) {
                throw new PreexistingEntityException("Comida " + comida + " ya existe.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comida comida) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comida persistentComida = em.find(Comida.class, comida.getNombreComida());
            List<ComidaPuesto> comidaPuestoListOld = persistentComida.getComidaPuestoList();
            List<ComidaPuesto> comidaPuestoListNew = comida.getComidaPuestoList();
            List<String> illegalOrphanMessages = null;
            for (ComidaPuesto comidaPuestoListOldComidaPuesto : comidaPuestoListOld) {
                if (!comidaPuestoListNew.contains(comidaPuestoListOldComidaPuesto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComidaPuesto " + comidaPuestoListOldComidaPuesto + " since its comida field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ComidaPuesto> attachedComidaPuestoListNew = new ArrayList<ComidaPuesto>();
            for (ComidaPuesto comidaPuestoListNewComidaPuestoToAttach : comidaPuestoListNew) {
                comidaPuestoListNewComidaPuestoToAttach = em.getReference(comidaPuestoListNewComidaPuestoToAttach.getClass(), comidaPuestoListNewComidaPuestoToAttach.getComidaPuestoPK());
                attachedComidaPuestoListNew.add(comidaPuestoListNewComidaPuestoToAttach);
            }
            comidaPuestoListNew = attachedComidaPuestoListNew;
            comida.setComidaPuestoList(comidaPuestoListNew);
            comida = em.merge(comida);
            for (ComidaPuesto comidaPuestoListNewComidaPuesto : comidaPuestoListNew) {
                if (!comidaPuestoListOld.contains(comidaPuestoListNewComidaPuesto)) {
                    Comida oldComidaOfComidaPuestoListNewComidaPuesto = comidaPuestoListNewComidaPuesto.getComida();
                    comidaPuestoListNewComidaPuesto.setComida(comida);
                    comidaPuestoListNewComidaPuesto = em.merge(comidaPuestoListNewComidaPuesto);
                    if (oldComidaOfComidaPuestoListNewComidaPuesto != null && !oldComidaOfComidaPuestoListNewComidaPuesto.equals(comida)) {
                        oldComidaOfComidaPuestoListNewComidaPuesto.getComidaPuestoList().remove(comidaPuestoListNewComidaPuesto);
                        oldComidaOfComidaPuestoListNewComidaPuesto = em.merge(oldComidaOfComidaPuestoListNewComidaPuesto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = comida.getNombreComida();
                if (findComida(id) == null) {
                    throw new NonexistentEntityException("The comida with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comida comida;
            try {
                comida = em.getReference(Comida.class, id);
                comida.getNombreComida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comida with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ComidaPuesto> comidaPuestoListOrphanCheck = comida.getComidaPuestoList();
            for (ComidaPuesto comidaPuestoListOrphanCheckComidaPuesto : comidaPuestoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comida (" + comida + ") cannot be destroyed since the ComidaPuesto " + comidaPuestoListOrphanCheckComidaPuesto + " in its comidaPuestoList field has a non-nullable comida field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(comida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comida> findComidaEntities() {
        return findComidaEntities(true, -1, -1);
    }

    public List<Comida> findComidaEntities(int maxResults, int firstResult) {
        return findComidaEntities(false, maxResults, firstResult);
    }

    private List<Comida> findComidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comida.class));
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

    public Comida findComida(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comida.class, id);
        } finally {
            em.close();
        }
    }

    public int getComidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comida> rt = cq.from(Comida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
