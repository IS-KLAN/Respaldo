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

    /**
     * Método que inserta una evaluación en la BD.
     * Se verifica que la PK este bien definida.
     * 
     * @param evaluacion Es la evaluacipon a insertar.
     * @throws PreexistingEntityException Indica que ya exisistía una evaluación con la misma PK.
     * @throws Exception  Cualquier otro error durante la transacción.
     */
    public void create(Evaluacion evaluacion) throws PreexistingEntityException, Exception {
        if (evaluacion.getEvaluacionPK() == null) {
            System.err.println("Se debe definir una llave para insertar."); return;
        } EvaluacionPK id = evaluacion.getEvaluacionPK();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto puesto = em.getReference(Puesto.class, id.getNombrePuesto());
            evaluacion.setPuesto(puesto);
            Usuario usuario = em.getReference(Usuario.class, id.getNombreUsuario());
            evaluacion.setUsuario(usuario);
            if (evaluacion.getPuesto() == null || evaluacion.getUsuario() == null) {
                System.err.println("La llave no contiene una referencia válida.");
                em.close(); return;
            } evaluacion.setIdEvaluacion(getEvaluacionCount() + 1);
            puesto.getEvaluacionList().add(evaluacion);
            em.merge(puesto);
            usuario.getEvaluacionList().add(evaluacion);
            em.merge(usuario);
            em.persist(evaluacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEvaluacion(id) != null) {
                throw new PreexistingEntityException("Evaluacion " + evaluacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) em.close();
        }
    }

    public void edit(Evaluacion evaluacion) throws NonexistentEntityException, Exception {
        if (evaluacion.getEvaluacionPK() == null) {
            System.err.println("Se debe definir una llave para insertar."); return;
        } EvaluacionPK id = evaluacion.getEvaluacionPK();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto puesto = em.getReference(Puesto.class, id.getNombrePuesto());
            Usuario usuario = em.getReference(Usuario.class, id.getNombreUsuario());
            evaluacion.setPuesto(puesto); evaluacion.setUsuario(usuario);
            if (puesto == null || usuario == null) {
                System.err.println("La llave no contiene una referencia válida.");
                em.close(); return;
            } Evaluacion original = findEvaluacion(id);
            if (original == null) {
                System.err.println("La evaluación con id " + id + "no existe.");
                em.close(); return;
            }
            puesto.getEvaluacionList().remove(original);
            puesto.getEvaluacionList().add(evaluacion);
            em.merge(puesto);
            usuario.getEvaluacionList().remove(original);
            usuario.getEvaluacionList().add(evaluacion);
            em.merge(usuario);
            em.merge(evaluacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
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
                em.merge(puesto);
            }
            Usuario usuario = evaluacion.getUsuario();
            if (usuario != null) {
                usuario.getEvaluacionList().remove(evaluacion);
                em.merge(usuario);
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
}
