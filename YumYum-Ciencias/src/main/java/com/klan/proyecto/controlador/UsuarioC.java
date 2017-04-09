/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.IllegalOrphanException;
import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.Evaluacion;
import com.klan.proyecto.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author patlani
 */
public class UsuarioC implements Serializable {

    public UsuarioC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getEvaluacionList() == null) {
            usuario.setEvaluacionList(new ArrayList<Evaluacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Evaluacion> attachedEvaluacionList = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListEvaluacionToAttach : usuario.getEvaluacionList()) {
                evaluacionListEvaluacionToAttach = em.getReference(evaluacionListEvaluacionToAttach.getClass(), evaluacionListEvaluacionToAttach.getEvaluacionPK());
                attachedEvaluacionList.add(evaluacionListEvaluacionToAttach);
            }
            usuario.setEvaluacionList(attachedEvaluacionList);
            em.persist(usuario);
            for (Evaluacion evaluacionListEvaluacion : usuario.getEvaluacionList()) {
                Usuario oldUsuarioOfEvaluacionListEvaluacion = evaluacionListEvaluacion.getUsuario();
                evaluacionListEvaluacion.setUsuario(usuario);
                evaluacionListEvaluacion = em.merge(evaluacionListEvaluacion);
                if (oldUsuarioOfEvaluacionListEvaluacion != null) {
                    oldUsuarioOfEvaluacionListEvaluacion.getEvaluacionList().remove(evaluacionListEvaluacion);
                    oldUsuarioOfEvaluacionListEvaluacion = em.merge(oldUsuarioOfEvaluacionListEvaluacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getNombreUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getNombreUsuario());
            List<Evaluacion> evaluacionListOld = persistentUsuario.getEvaluacionList();
            List<Evaluacion> evaluacionListNew = usuario.getEvaluacionList();
            List<String> illegalOrphanMessages = null;
            for (Evaluacion evaluacionListOldEvaluacion : evaluacionListOld) {
                if (!evaluacionListNew.contains(evaluacionListOldEvaluacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evaluacion " + evaluacionListOldEvaluacion + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Evaluacion> attachedEvaluacionListNew = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListNewEvaluacionToAttach : evaluacionListNew) {
                evaluacionListNewEvaluacionToAttach = em.getReference(evaluacionListNewEvaluacionToAttach.getClass(), evaluacionListNewEvaluacionToAttach.getEvaluacionPK());
                attachedEvaluacionListNew.add(evaluacionListNewEvaluacionToAttach);
            }
            evaluacionListNew = attachedEvaluacionListNew;
            usuario.setEvaluacionList(evaluacionListNew);
            usuario = em.merge(usuario);
            for (Evaluacion evaluacionListNewEvaluacion : evaluacionListNew) {
                if (!evaluacionListOld.contains(evaluacionListNewEvaluacion)) {
                    Usuario oldUsuarioOfEvaluacionListNewEvaluacion = evaluacionListNewEvaluacion.getUsuario();
                    evaluacionListNewEvaluacion.setUsuario(usuario);
                    evaluacionListNewEvaluacion = em.merge(evaluacionListNewEvaluacion);
                    if (oldUsuarioOfEvaluacionListNewEvaluacion != null && !oldUsuarioOfEvaluacionListNewEvaluacion.equals(usuario)) {
                        oldUsuarioOfEvaluacionListNewEvaluacion.getEvaluacionList().remove(evaluacionListNewEvaluacion);
                        oldUsuarioOfEvaluacionListNewEvaluacion = em.merge(oldUsuarioOfEvaluacionListNewEvaluacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getNombreUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evaluacion> evaluacionListOrphanCheck = usuario.getEvaluacionList();
            for (Evaluacion evaluacionListOrphanCheckEvaluacion : evaluacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Evaluacion " + evaluacionListOrphanCheckEvaluacion + " in its evaluacionList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
