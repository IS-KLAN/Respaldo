/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.modelo.Evaluacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.Puesto;
import com.klan.proyecto.modelo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author patlani
 */
public class EvaluacionC implements Serializable {

    public EvaluacionC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evaluacion evaluacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto idPuesto = evaluacion.getIdPuesto();
            if (idPuesto != null) {
                idPuesto = em.getReference(idPuesto.getClass(), idPuesto.getIdPuesto());
                evaluacion.setIdPuesto(idPuesto);
            }
            Usuario idUsuario = evaluacion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                evaluacion.setIdUsuario(idUsuario);
            }
            em.persist(evaluacion);
            if (idPuesto != null) {
                idPuesto.getEvaluacionList().add(evaluacion);
                idPuesto = em.merge(idPuesto);
            }
            if (idUsuario != null) {
                idUsuario.getEvaluacionList().add(evaluacion);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evaluacion evaluacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluacion persistentEvaluacion = em.find(Evaluacion.class, evaluacion.getIdEvaluacion());
            Puesto idPuestoOld = persistentEvaluacion.getIdPuesto();
            Puesto idPuestoNew = evaluacion.getIdPuesto();
            Usuario idUsuarioOld = persistentEvaluacion.getIdUsuario();
            Usuario idUsuarioNew = evaluacion.getIdUsuario();
            if (idPuestoNew != null) {
                idPuestoNew = em.getReference(idPuestoNew.getClass(), idPuestoNew.getIdPuesto());
                evaluacion.setIdPuesto(idPuestoNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                evaluacion.setIdUsuario(idUsuarioNew);
            }
            evaluacion = em.merge(evaluacion);
            if (idPuestoOld != null && !idPuestoOld.equals(idPuestoNew)) {
                idPuestoOld.getEvaluacionList().remove(evaluacion);
                idPuestoOld = em.merge(idPuestoOld);
            }
            if (idPuestoNew != null && !idPuestoNew.equals(idPuestoOld)) {
                idPuestoNew.getEvaluacionList().add(evaluacion);
                idPuestoNew = em.merge(idPuestoNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getEvaluacionList().remove(evaluacion);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getEvaluacionList().add(evaluacion);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = evaluacion.getIdEvaluacion();
                if (findEvaluacion(id) == null) {
                    throw new NonexistentEntityException("The evaluacion with id " + id + " no longer exists.");
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
            Evaluacion evaluacion;
            try {
                evaluacion = em.getReference(Evaluacion.class, id);
                evaluacion.getIdEvaluacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluacion with id " + id + " no longer exists.", enfe);
            }
            Puesto idPuesto = evaluacion.getIdPuesto();
            if (idPuesto != null) {
                idPuesto.getEvaluacionList().remove(evaluacion);
                idPuesto = em.merge(idPuesto);
            }
            Usuario idUsuario = evaluacion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getEvaluacionList().remove(evaluacion);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(evaluacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evaluacion> findEvaluacionEntities() {
        return findEvaluacionEntities(true, -1, -1);
    }

    public List<Evaluacion> findEvaluacionEntities(int maxResults, int firstResult) {
        return findEvaluacionEntities(false, maxResults, firstResult);
    }

    private List<Evaluacion> findEvaluacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evaluacion.class));
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

    public Evaluacion findEvaluacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evaluacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evaluacion> rt = cq.from(Evaluacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
