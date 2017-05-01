/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.jpa;

import com.klan.proyecto.jpa.excepciones.EntidadInexistenteException;
import com.klan.proyecto.jpa.excepciones.EntidadExistenteException;
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

    public void crear(Pendiente pendiente) throws EntidadExistenteException, Exception {
        EntityManager em = null;
        try {
            pendiente.setId(cantidadDePendientes() + 1);
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pendiente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (buscaId(pendiente.getNombre()) != null) {
                throw new EntidadExistenteException("Pendiente " + pendiente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editar(Pendiente pendiente) throws EntidadInexistenteException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pendiente = em.merge(pendiente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pendiente.getNombre();
                if (buscaId(id) == null) {
                    throw new EntidadInexistenteException("No existe pendiente con id " + id);
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void borrar(String id) throws EntidadInexistenteException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pendiente pendiente;
            try {
                pendiente = em.getReference(Pendiente.class, id);
                pendiente.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new EntidadInexistenteException("No existe pendiente with id " + id, enfe);
            }
            em.remove(pendiente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pendiente> buscaPendientes() {
        return buscaPendientes(true, -1, -1);
    }

    public List<Pendiente> buscaPendientes(int maxResults, int firstResult) {
        return buscaPendientes(false, maxResults, firstResult);
    }

    private List<Pendiente> buscaPendientes(boolean all, int maxResults, int firstResult) {
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

    public Pendiente buscaId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pendiente.class, id);
        } finally {
            em.close();
        }
    }
    
    public Pendiente buscaCorreo(String correo) {
        try{
            EntityManager em = getEntityManager();
            return (Pendiente)(em.createNamedQuery("Pendiente.buscaCorreo")
                    .setParameter("correo", correo).getSingleResult());
        }catch(Exception ex){
            System.err.println(ex.getMessage() + "\nError al buscar el usuario con correo: " + correo);
        } return null;
    }    

    public int cantidadDePendientes() {
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
