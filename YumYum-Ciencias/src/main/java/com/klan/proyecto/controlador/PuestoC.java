/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.IllegalOrphanException;
import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.Comida;
import java.util.ArrayList;
import java.util.List;
import com.klan.proyecto.modelo.Evaluacion;
import com.klan.proyecto.modelo.Puesto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author patlani
 */
public class PuestoC implements Serializable {

    public PuestoC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Puesto puesto) {
        if (puesto.getComidaList() == null) {
            puesto.setComidaList(new ArrayList<Comida>());
        }
        if (puesto.getEvaluacionList() == null) {
            puesto.setEvaluacionList(new ArrayList<Evaluacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comida> attachedComidaList = new ArrayList<Comida>();
            for (Comida comidaListComidaToAttach : puesto.getComidaList()) {
                comidaListComidaToAttach = em.getReference(comidaListComidaToAttach.getClass(), comidaListComidaToAttach.getIdComida());
                attachedComidaList.add(comidaListComidaToAttach);
            }
            puesto.setComidaList(attachedComidaList);
            List<Evaluacion> attachedEvaluacionList = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListEvaluacionToAttach : puesto.getEvaluacionList()) {
                evaluacionListEvaluacionToAttach = em.getReference(evaluacionListEvaluacionToAttach.getClass(), evaluacionListEvaluacionToAttach.getIdEvaluacion());
                attachedEvaluacionList.add(evaluacionListEvaluacionToAttach);
            }
            puesto.setEvaluacionList(attachedEvaluacionList);
            em.persist(puesto);
            for (Comida comidaListComida : puesto.getComidaList()) {
                comidaListComida.getPuestoList().add(puesto);
                comidaListComida = em.merge(comidaListComida);
            }
            for (Evaluacion evaluacionListEvaluacion : puesto.getEvaluacionList()) {
                Puesto oldIdPuestoOfEvaluacionListEvaluacion = evaluacionListEvaluacion.getIdPuesto();
                evaluacionListEvaluacion.setIdPuesto(puesto);
                evaluacionListEvaluacion = em.merge(evaluacionListEvaluacion);
                if (oldIdPuestoOfEvaluacionListEvaluacion != null) {
                    oldIdPuestoOfEvaluacionListEvaluacion.getEvaluacionList().remove(evaluacionListEvaluacion);
                    oldIdPuestoOfEvaluacionListEvaluacion = em.merge(oldIdPuestoOfEvaluacionListEvaluacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Puesto puesto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto persistentPuesto = em.find(Puesto.class, puesto.getIdPuesto());
            List<Comida> comidaListOld = persistentPuesto.getComidaList();
            List<Comida> comidaListNew = puesto.getComidaList();
            List<Evaluacion> evaluacionListOld = persistentPuesto.getEvaluacionList();
            List<Evaluacion> evaluacionListNew = puesto.getEvaluacionList();
            List<String> illegalOrphanMessages = null;
            for (Evaluacion evaluacionListOldEvaluacion : evaluacionListOld) {
                if (!evaluacionListNew.contains(evaluacionListOldEvaluacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evaluacion " + evaluacionListOldEvaluacion + " since its idPuesto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Comida> attachedComidaListNew = new ArrayList<Comida>();
            for (Comida comidaListNewComidaToAttach : comidaListNew) {
                comidaListNewComidaToAttach = em.getReference(comidaListNewComidaToAttach.getClass(), comidaListNewComidaToAttach.getIdComida());
                attachedComidaListNew.add(comidaListNewComidaToAttach);
            }
            comidaListNew = attachedComidaListNew;
            puesto.setComidaList(comidaListNew);
            List<Evaluacion> attachedEvaluacionListNew = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListNewEvaluacionToAttach : evaluacionListNew) {
                evaluacionListNewEvaluacionToAttach = em.getReference(evaluacionListNewEvaluacionToAttach.getClass(), evaluacionListNewEvaluacionToAttach.getIdEvaluacion());
                attachedEvaluacionListNew.add(evaluacionListNewEvaluacionToAttach);
            }
            evaluacionListNew = attachedEvaluacionListNew;
            puesto.setEvaluacionList(evaluacionListNew);
            puesto = em.merge(puesto);
            for (Comida comidaListOldComida : comidaListOld) {
                if (!comidaListNew.contains(comidaListOldComida)) {
                    comidaListOldComida.getPuestoList().remove(puesto);
                    comidaListOldComida = em.merge(comidaListOldComida);
                }
            }
            for (Comida comidaListNewComida : comidaListNew) {
                if (!comidaListOld.contains(comidaListNewComida)) {
                    comidaListNewComida.getPuestoList().add(puesto);
                    comidaListNewComida = em.merge(comidaListNewComida);
                }
            }
            for (Evaluacion evaluacionListNewEvaluacion : evaluacionListNew) {
                if (!evaluacionListOld.contains(evaluacionListNewEvaluacion)) {
                    Puesto oldIdPuestoOfEvaluacionListNewEvaluacion = evaluacionListNewEvaluacion.getIdPuesto();
                    evaluacionListNewEvaluacion.setIdPuesto(puesto);
                    evaluacionListNewEvaluacion = em.merge(evaluacionListNewEvaluacion);
                    if (oldIdPuestoOfEvaluacionListNewEvaluacion != null && !oldIdPuestoOfEvaluacionListNewEvaluacion.equals(puesto)) {
                        oldIdPuestoOfEvaluacionListNewEvaluacion.getEvaluacionList().remove(evaluacionListNewEvaluacion);
                        oldIdPuestoOfEvaluacionListNewEvaluacion = em.merge(oldIdPuestoOfEvaluacionListNewEvaluacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = puesto.getIdPuesto();
                if (findPuesto(id) == null) {
                    throw new NonexistentEntityException("The puesto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto puesto;
            try {
                puesto = em.getReference(Puesto.class, id);
                puesto.getIdPuesto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The puesto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evaluacion> evaluacionListOrphanCheck = puesto.getEvaluacionList();
            for (Evaluacion evaluacionListOrphanCheckEvaluacion : evaluacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Puesto (" + puesto + ") cannot be destroyed since the Evaluacion " + evaluacionListOrphanCheckEvaluacion + " in its evaluacionList field has a non-nullable idPuesto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Comida> comidaList = puesto.getComidaList();
            for (Comida comidaListComida : comidaList) {
                comidaListComida.getPuestoList().remove(puesto);
                comidaListComida = em.merge(comidaListComida);
            }
            em.remove(puesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puesto> findPuestoEntities() {
        return findPuestoEntities(true, -1, -1);
    }

    public List<Puesto> findPuestoEntities(int maxResults, int firstResult) {
        return findPuestoEntities(false, maxResults, firstResult);
    }

    private List<Puesto> findPuestoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Puesto.class));
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

    public Puesto findPuesto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puesto.class, id);
        } finally {
            em.close();
        }
    }

    public int getPuestoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Puesto> rt = cq.from(Puesto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
