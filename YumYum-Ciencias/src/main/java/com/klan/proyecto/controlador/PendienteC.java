/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import com.klan.proyecto.modelo.Pendiente;
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
public class PendienteC implements Serializable {

    public PendienteC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pendiente pendiente) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            pendiente.setIdUsuario(getPendienteCount() + 1);
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pendiente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPendiente(pendiente.getNombreUsuario()) != null) {
                throw new PreexistingEntityException("Pendiente " + pendiente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pendiente pendiente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pendiente = em.merge(pendiente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pendiente.getNombreUsuario();
                if (findPendiente(id) == null) {
                    throw new NonexistentEntityException("The pendiente with id " + id + " no longer exists.");
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
            Pendiente pendiente;
            try {
                pendiente = em.getReference(Pendiente.class, id);
                pendiente.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pendiente with id " + id + " no longer exists.", enfe);
            }
            em.remove(pendiente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pendiente> findPendienteEntities() {
        return findPendienteEntities(true, -1, -1);
    }

    public List<Pendiente> findPendienteEntities(int maxResults, int firstResult) {
        return findPendienteEntities(false, maxResults, firstResult);
    }

    private List<Pendiente> findPendienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pendiente.class));
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

    public Pendiente findPendiente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pendiente.class, id);
        } finally {
            em.close();
        }
    }
    
    public Pendiente findByCorreo(String correo) {
        try{
            EntityManager em = getEntityManager();
            return (Pendiente)(em.createNamedQuery("Pendiente.findByCorreo")
                    .setParameter("correo", correo).getSingleResult());
        }catch(Exception ex){
            System.err.println(ex.getMessage() + "\nError al buscar el usuario con correo: " + correo);
        } return null;
    }    
    
    public Pendiente findByIdUsuario(int idUsuario) {
        try{
            EntityManager em = getEntityManager();
            return (Pendiente)(em.createNamedQuery("Pendiente.findByIdUsuario")
                    .setParameter("idUsuario", idUsuario).getSingleResult());
        }catch(Exception ex){
            System.err.println(ex.getMessage() + "\nError al buscar el usuario con id: " + idUsuario);
        } return null;
    }    

    public int getPendienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pendiente> rt = cq.from(Pendiente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
