/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import com.klan.proyecto.modelo.Evaluacion;
import com.klan.proyecto.modelo.EvaluacionPK;
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

    public void create(Evaluacion evaluacion) throws PreexistingEntityException, Exception {
        if (evaluacion.getEvaluacionPK() == null) {
            evaluacion.setEvaluacionPK(new EvaluacionPK());
        }
        evaluacion.getEvaluacionPK().setNombreUsuario(evaluacion.getUsuario().getNombreUsuario());
        evaluacion.getEvaluacionPK().setNombrePuesto(evaluacion.getPuesto().getNombrePuesto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto puesto = evaluacion.getPuesto();
            if (puesto != null) {
                puesto = em.getReference(puesto.getClass(), puesto.getNombrePuesto());
                evaluacion.setPuesto(puesto);
            }
            Usuario usuario = evaluacion.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getNombreUsuario());
                evaluacion.setUsuario(usuario);
            }
            em.persist(evaluacion);
            if (puesto != null) {
                puesto.getEvaluacionList().add(evaluacion);
                puesto = em.merge(puesto);
            }
            if (usuario != null) {
                usuario.getEvaluacionList().add(evaluacion);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvaluacion(evaluacion.getEvaluacionPK()) != null) {
                throw new PreexistingEntityException("Evaluacion " + evaluacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evaluacion evaluacion) throws NonexistentEntityException, Exception {
        evaluacion.getEvaluacionPK().setNombreUsuario(evaluacion.getUsuario().getNombreUsuario());
        evaluacion.getEvaluacionPK().setNombrePuesto(evaluacion.getPuesto().getNombrePuesto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluacion persistentEvaluacion = em.find(Evaluacion.class, evaluacion.getEvaluacionPK());
            Puesto puestoOld = persistentEvaluacion.getPuesto();
            Puesto puestoNew = evaluacion.getPuesto();
            Usuario usuarioOld = persistentEvaluacion.getUsuario();
            Usuario usuarioNew = evaluacion.getUsuario();
            if (puestoNew != null) {
                puestoNew = em.getReference(puestoNew.getClass(), puestoNew.getNombrePuesto());
                evaluacion.setPuesto(puestoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getNombreUsuario());
                evaluacion.setUsuario(usuarioNew);
            }
            evaluacion = em.merge(evaluacion);
            if (puestoOld != null && !puestoOld.equals(puestoNew)) {
                puestoOld.getEvaluacionList().remove(evaluacion);
                puestoOld = em.merge(puestoOld);
            }
            if (puestoNew != null && !puestoNew.equals(puestoOld)) {
                puestoNew.getEvaluacionList().add(evaluacion);
                puestoNew = em.merge(puestoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getEvaluacionList().remove(evaluacion);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getEvaluacionList().add(evaluacion);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EvaluacionPK id = evaluacion.getEvaluacionPK();
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

    public void destroy(EvaluacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evaluacion evaluacion;
            try {
                evaluacion = em.getReference(Evaluacion.class, id);
                evaluacion.getEvaluacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluacion with id " + id + " no longer exists.", enfe);
            }
            Puesto puesto = evaluacion.getPuesto();
            if (puesto != null) {
                puesto.getEvaluacionList().remove(evaluacion);
                puesto = em.merge(puesto);
            }
            Usuario usuario = evaluacion.getUsuario();
            if (usuario != null) {
                usuario.getEvaluacionList().remove(evaluacion);
                usuario = em.merge(usuario);
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

    public Evaluacion findEvaluacion(EvaluacionPK id) {
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

    public List<Evaluacion> findByNombrePuesto(String nombrePuesto) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Evaluacion.findByNombrePuesto")
                    .setParameter("nombrePuesto", nombrePuesto).getResultList();
        } finally {
            em.close();
        }
    }
}
