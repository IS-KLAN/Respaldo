/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.klan.proyecto.jpa;

import com.klan.proyecto.jpa.excepciones.InconsistenciasException;
import com.klan.proyecto.jpa.excepciones.EntidadInexistenteException;
import com.klan.proyecto.jpa.excepciones.EntidadExistenteException;
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
import javax.persistence.NamedQueries;

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

    public void crear(Usuario usuario) throws EntidadExistenteException, Exception {
        if (usuario.getEvaluacionList() == null) {
            usuario.setEvaluacionList(new ArrayList<Evaluacion>());
        }
        EntityManager em = null;
        try {
            usuario.setIdUsuario(cantidadDeUsuarios() + 1);
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
            if (buscaId(usuario.getNombreUsuario()) != null) {
                throw new EntidadExistenteException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editar(Usuario usuario) throws InconsistenciasException, EntidadInexistenteException, Exception {
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
                    illegalOrphanMessages.add("Se debe mantener Evaluacion " + evaluacionListOldEvaluacion + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new InconsistenciasException(illegalOrphanMessages);
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
                if (buscaId(id) == null) {
                    throw new EntidadInexistenteException("No existe usuario con id " + id);
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void borrar(String id) throws InconsistenciasException, EntidadInexistenteException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new EntidadInexistenteException("No existe usuario with id " + id, enfe);
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
                throw new InconsistenciasException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> buscaUsuarios() {
        return buscaUsuarios(true, -1, -1);
    }

    public List<Usuario> buscaUsuarios(int maxResults, int firstResult) {
        return buscaUsuarios(false, maxResults, firstResult);
    }

    private List<Usuario> buscaUsuarios(boolean all, int maxResults, int firstResult) {
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

    public Usuario buscaId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public Usuario buscaCorreo(String correo) {
        try{
            EntityManager em = getEntityManager();
            return (Usuario)(em.createNamedQuery("Usuario.findByCorreo")
                    .setParameter("correo", correo).getSingleResult());
        }catch(Exception ex){
            System.err.println(ex.getMessage() + "\nError al buscar el usuario con correo: " + correo);
        } return null;
    }    

    public int cantidadDeUsuarios() {
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
