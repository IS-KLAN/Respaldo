/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.modelo.Comida;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.Puesto;
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

    public void create(Comida comida) {
        if (comida.getPuestoList() == null) {
            comida.setPuestoList(new ArrayList<Puesto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Puesto> attachedPuestoList = new ArrayList<Puesto>();
            for (Puesto puestoListPuestoToAttach : comida.getPuestoList()) {
                puestoListPuestoToAttach = em.getReference(puestoListPuestoToAttach.getClass(), puestoListPuestoToAttach.getIdPuesto());
                attachedPuestoList.add(puestoListPuestoToAttach);
            }
            comida.setPuestoList(attachedPuestoList);
            em.persist(comida);
            for (Puesto puestoListPuesto : comida.getPuestoList()) {
                puestoListPuesto.getComidaList().add(comida);
                puestoListPuesto = em.merge(puestoListPuesto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comida comida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comida persistentComida = em.find(Comida.class, comida.getIdComida());
            List<Puesto> puestoListOld = persistentComida.getPuestoList();
            List<Puesto> puestoListNew = comida.getPuestoList();
            List<Puesto> attachedPuestoListNew = new ArrayList<Puesto>();
            for (Puesto puestoListNewPuestoToAttach : puestoListNew) {
                puestoListNewPuestoToAttach = em.getReference(puestoListNewPuestoToAttach.getClass(), puestoListNewPuestoToAttach.getIdPuesto());
                attachedPuestoListNew.add(puestoListNewPuestoToAttach);
            }
            puestoListNew = attachedPuestoListNew;
            comida.setPuestoList(puestoListNew);
            comida = em.merge(comida);
            for (Puesto puestoListOldPuesto : puestoListOld) {
                if (!puestoListNew.contains(puestoListOldPuesto)) {
                    puestoListOldPuesto.getComidaList().remove(comida);
                    puestoListOldPuesto = em.merge(puestoListOldPuesto);
                }
            }
            for (Puesto puestoListNewPuesto : puestoListNew) {
                if (!puestoListOld.contains(puestoListNewPuesto)) {
                    puestoListNewPuesto.getComidaList().add(comida);
                    puestoListNewPuesto = em.merge(puestoListNewPuesto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = comida.getIdComida();
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

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comida comida;
            try {
                comida = em.getReference(Comida.class, id);
                comida.getIdComida();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comida with id " + id + " no longer exists.", enfe);
            }
            List<Puesto> puestoList = comida.getPuestoList();
            for (Puesto puestoListPuesto : puestoList) {
                puestoListPuesto.getComidaList().remove(comida);
                puestoListPuesto = em.merge(puestoListPuesto);
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

    public Comida findComida(Long id) {
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
