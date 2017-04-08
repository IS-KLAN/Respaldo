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
import com.klan.proyecto.modelo.Persona;
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

    public void create(Usuario usuario) throws IllegalOrphanException {
        if (usuario.getEvaluacionList() == null) {
            usuario.setEvaluacionList(new ArrayList<Evaluacion>());
        }
        List<String> illegalOrphanMessages = null;
        Persona idPersonaOrphanCheck = usuario.getIdPersona();
        if (idPersonaOrphanCheck != null) {
            Usuario oldUsuarioOfIdPersona = idPersonaOrphanCheck.getUsuario();
            if (oldUsuarioOfIdPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + idPersonaOrphanCheck + " already has an item of type Usuario whose idPersona column cannot be null. Please make another selection for the idPersona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona idPersona = usuario.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                usuario.setIdPersona(idPersona);
            }
            List<Evaluacion> attachedEvaluacionList = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListEvaluacionToAttach : usuario.getEvaluacionList()) {
                evaluacionListEvaluacionToAttach = em.getReference(evaluacionListEvaluacionToAttach.getClass(), evaluacionListEvaluacionToAttach.getIdEvaluacion());
                attachedEvaluacionList.add(evaluacionListEvaluacionToAttach);
            }
            usuario.setEvaluacionList(attachedEvaluacionList);
            em.persist(usuario);
            if (idPersona != null) {
                idPersona.setUsuario(usuario);
                idPersona = em.merge(idPersona);
            }
            for (Evaluacion evaluacionListEvaluacion : usuario.getEvaluacionList()) {
                Usuario oldIdUsuarioOfEvaluacionListEvaluacion = evaluacionListEvaluacion.getIdUsuario();
                evaluacionListEvaluacion.setIdUsuario(usuario);
                evaluacionListEvaluacion = em.merge(evaluacionListEvaluacion);
                if (oldIdUsuarioOfEvaluacionListEvaluacion != null) {
                    oldIdUsuarioOfEvaluacionListEvaluacion.getEvaluacionList().remove(evaluacionListEvaluacion);
                    oldIdUsuarioOfEvaluacionListEvaluacion = em.merge(oldIdUsuarioOfEvaluacionListEvaluacion);
                }
            }
            em.getTransaction().commit();
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
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Persona idPersonaOld = persistentUsuario.getIdPersona();
            Persona idPersonaNew = usuario.getIdPersona();
            List<Evaluacion> evaluacionListOld = persistentUsuario.getEvaluacionList();
            List<Evaluacion> evaluacionListNew = usuario.getEvaluacionList();
            List<String> illegalOrphanMessages = null;
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                Usuario oldUsuarioOfIdPersona = idPersonaNew.getUsuario();
                if (oldUsuarioOfIdPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + idPersonaNew + " already has an item of type Usuario whose idPersona column cannot be null. Please make another selection for the idPersona field.");
                }
            }
            for (Evaluacion evaluacionListOldEvaluacion : evaluacionListOld) {
                if (!evaluacionListNew.contains(evaluacionListOldEvaluacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evaluacion " + evaluacionListOldEvaluacion + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                usuario.setIdPersona(idPersonaNew);
            }
            List<Evaluacion> attachedEvaluacionListNew = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListNewEvaluacionToAttach : evaluacionListNew) {
                evaluacionListNewEvaluacionToAttach = em.getReference(evaluacionListNewEvaluacionToAttach.getClass(), evaluacionListNewEvaluacionToAttach.getIdEvaluacion());
                attachedEvaluacionListNew.add(evaluacionListNewEvaluacionToAttach);
            }
            evaluacionListNew = attachedEvaluacionListNew;
            usuario.setEvaluacionList(evaluacionListNew);
            usuario = em.merge(usuario);
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.setUsuario(null);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.setUsuario(usuario);
                idPersonaNew = em.merge(idPersonaNew);
            }
            for (Evaluacion evaluacionListNewEvaluacion : evaluacionListNew) {
                if (!evaluacionListOld.contains(evaluacionListNewEvaluacion)) {
                    Usuario oldIdUsuarioOfEvaluacionListNewEvaluacion = evaluacionListNewEvaluacion.getIdUsuario();
                    evaluacionListNewEvaluacion.setIdUsuario(usuario);
                    evaluacionListNewEvaluacion = em.merge(evaluacionListNewEvaluacion);
                    if (oldIdUsuarioOfEvaluacionListNewEvaluacion != null && !oldIdUsuarioOfEvaluacionListNewEvaluacion.equals(usuario)) {
                        oldIdUsuarioOfEvaluacionListNewEvaluacion.getEvaluacionList().remove(evaluacionListNewEvaluacion);
                        oldIdUsuarioOfEvaluacionListNewEvaluacion = em.merge(oldIdUsuarioOfEvaluacionListNewEvaluacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario.getIdUsuario();
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

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evaluacion> evaluacionListOrphanCheck = usuario.getEvaluacionList();
            for (Evaluacion evaluacionListOrphanCheckEvaluacion : evaluacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Evaluacion " + evaluacionListOrphanCheckEvaluacion + " in its evaluacionList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona idPersona = usuario.getIdPersona();
            if (idPersona != null) {
                idPersona.setUsuario(null);
                idPersona = em.merge(idPersona);
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

    public Usuario findUsuario(Long id) {
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
