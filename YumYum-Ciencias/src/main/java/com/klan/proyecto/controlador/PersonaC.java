/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.controlador;

import com.klan.proyecto.controlador.exceptions.IllegalOrphanException;
import com.klan.proyecto.controlador.exceptions.NonexistentEntityException;
import com.klan.proyecto.modelo.Persona;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.klan.proyecto.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author patlani
 */
public class PersonaC implements Serializable {

    public PersonaC(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = persona.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdUsuario());
                persona.setUsuario(usuario);
            }
            em.persist(persona);
            if (usuario != null) {
                Persona oldIdPersonaOfUsuario = usuario.getIdPersona();
                if (oldIdPersonaOfUsuario != null) {
                    oldIdPersonaOfUsuario.setUsuario(null);
                    oldIdPersonaOfUsuario = em.merge(oldIdPersonaOfUsuario);
                }
                usuario.setIdPersona(persona);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getIdPersona());
            Usuario usuarioOld = persistentPersona.getUsuario();
            Usuario usuarioNew = persona.getUsuario();
            List<String> illegalOrphanMessages = null;
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Usuario " + usuarioOld + " since its idPersona field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdUsuario());
                persona.setUsuario(usuarioNew);
            }
            persona = em.merge(persona);
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                Persona oldIdPersonaOfUsuario = usuarioNew.getIdPersona();
                if (oldIdPersonaOfUsuario != null) {
                    oldIdPersonaOfUsuario.setUsuario(null);
                    oldIdPersonaOfUsuario = em.merge(oldIdPersonaOfUsuario);
                }
                usuarioNew.setIdPersona(persona);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getIdPersona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Usuario usuarioOrphanCheck = persona.getUsuario();
            if (usuarioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Usuario " + usuarioOrphanCheck + " in its usuario field has a non-nullable idPersona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
