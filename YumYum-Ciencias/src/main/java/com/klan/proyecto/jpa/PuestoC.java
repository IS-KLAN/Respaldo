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
import com.klan.proyecto.modelo.ComidaPuesto;
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

    public void crear(Puesto puesto) throws EntidadExistenteException, Exception {
        if (puesto.getComida() == null) {
            puesto.setComida(new ArrayList<ComidaPuesto>());
        }
        if (puesto.getEvaluaciones() == null) {
            puesto.setEvaluaciones(new ArrayList<Evaluacion>());
        }
        EntityManager em = null;
        try {
            puesto.setId(cantidadDePuestos() + 1);
            em = getEntityManager();
            em.getTransaction().begin();
            List<ComidaPuesto> attachedComidaPuestoList = new ArrayList<ComidaPuesto>();
            for (ComidaPuesto comidaPuestoListComidaPuestoToAttach : puesto.getComida()) {
                comidaPuestoListComidaPuestoToAttach = em.getReference(comidaPuestoListComidaPuestoToAttach.getClass(), comidaPuestoListComidaPuestoToAttach.getLlave());
                attachedComidaPuestoList.add(comidaPuestoListComidaPuestoToAttach);
            }
            puesto.setComida(attachedComidaPuestoList);
            List<Evaluacion> attachedEvaluacionList = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListEvaluacionToAttach : puesto.getEvaluaciones()) {
                evaluacionListEvaluacionToAttach = em.getReference(evaluacionListEvaluacionToAttach.getClass(), evaluacionListEvaluacionToAttach.getLlave());
                attachedEvaluacionList.add(evaluacionListEvaluacionToAttach);
            }
            puesto.setEvaluaciones(attachedEvaluacionList);
            em.persist(puesto);
            for (ComidaPuesto comidaPuestoListComidaPuesto : puesto.getComida()) {
                Puesto oldPuestoOfComidaPuestoListComidaPuesto = comidaPuestoListComidaPuesto.getPuesto();
                comidaPuestoListComidaPuesto.setPuesto(puesto);
                comidaPuestoListComidaPuesto = em.merge(comidaPuestoListComidaPuesto);
                if (oldPuestoOfComidaPuestoListComidaPuesto != null) {
                    oldPuestoOfComidaPuestoListComidaPuesto.getComida().remove(comidaPuestoListComidaPuesto);
                    oldPuestoOfComidaPuestoListComidaPuesto = em.merge(oldPuestoOfComidaPuestoListComidaPuesto);
                }
            }
            for (Evaluacion evaluacionListEvaluacion : puesto.getEvaluaciones()) {
                Puesto oldPuestoOfEvaluacionListEvaluacion = evaluacionListEvaluacion.getPuesto();
                evaluacionListEvaluacion.setPuesto(puesto);
                evaluacionListEvaluacion = em.merge(evaluacionListEvaluacion);
                if (oldPuestoOfEvaluacionListEvaluacion != null) {
                    oldPuestoOfEvaluacionListEvaluacion.getEvaluaciones().remove(evaluacionListEvaluacion);
                    oldPuestoOfEvaluacionListEvaluacion = em.merge(oldPuestoOfEvaluacionListEvaluacion);
                }
            }
            em.getTransaction().commit();
            // System.out.println("Puesto agregado: \nNombre" + puesto.getNombrePuesto());
        } catch (Exception ex) {
            if (buscaNombre(puesto.getNombre()) != null) {
                throw new EntidadExistenteException("Puesto " + puesto + " ya existe.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editar(Puesto puesto) throws InconsistenciasException, EntidadInexistenteException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puesto persistentPuesto = em.find(Puesto.class, puesto.getNombre());
            List<ComidaPuesto> comidaPuestoListOld = persistentPuesto.getComida();
            List<ComidaPuesto> comidaPuestoListNew = puesto.getComida();
            List<Evaluacion> evaluacionListOld = persistentPuesto.getEvaluaciones();
            List<Evaluacion> evaluacionListNew = puesto.getEvaluaciones();
            List<String> illegalOrphanMessages = null;
            for (ComidaPuesto comidaPuestoListOldComidaPuesto : comidaPuestoListOld) {
                if (!comidaPuestoListNew.contains(comidaPuestoListOldComidaPuesto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("Se debe mantener ComidaPuesto " + comidaPuestoListOldComidaPuesto + " since its puesto field is not nullable.");
                }
            }
            for (Evaluacion evaluacionListOldEvaluacion : evaluacionListOld) {
                if (!evaluacionListNew.contains(evaluacionListOldEvaluacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("Se debe mantener Evaluacion " + evaluacionListOldEvaluacion + " since its puesto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new InconsistenciasException(illegalOrphanMessages);
            }
            List<ComidaPuesto> attachedComidaPuestoListNew = new ArrayList<ComidaPuesto>();
            for (ComidaPuesto comidaPuestoListNewComidaPuestoToAttach : comidaPuestoListNew) {
                comidaPuestoListNewComidaPuestoToAttach = em.getReference(comidaPuestoListNewComidaPuestoToAttach.getClass(), comidaPuestoListNewComidaPuestoToAttach.getLlave());
                attachedComidaPuestoListNew.add(comidaPuestoListNewComidaPuestoToAttach);
            }
            comidaPuestoListNew = attachedComidaPuestoListNew;
            puesto.setComida(comidaPuestoListNew);
            List<Evaluacion> attachedEvaluacionListNew = new ArrayList<Evaluacion>();
            for (Evaluacion evaluacionListNewEvaluacionToAttach : evaluacionListNew) {
                evaluacionListNewEvaluacionToAttach = em.getReference(evaluacionListNewEvaluacionToAttach.getClass(), evaluacionListNewEvaluacionToAttach.getLlave());
                attachedEvaluacionListNew.add(evaluacionListNewEvaluacionToAttach);
            }
            evaluacionListNew = attachedEvaluacionListNew;
            puesto.setEvaluaciones(evaluacionListNew);
            puesto = em.merge(puesto);
            for (ComidaPuesto comidaPuestoListNewComidaPuesto : comidaPuestoListNew) {
                if (!comidaPuestoListOld.contains(comidaPuestoListNewComidaPuesto)) {
                    Puesto oldPuestoOfComidaPuestoListNewComidaPuesto = comidaPuestoListNewComidaPuesto.getPuesto();
                    comidaPuestoListNewComidaPuesto.setPuesto(puesto);
                    comidaPuestoListNewComidaPuesto = em.merge(comidaPuestoListNewComidaPuesto);
                    if (oldPuestoOfComidaPuestoListNewComidaPuesto != null && !oldPuestoOfComidaPuestoListNewComidaPuesto.equals(puesto)) {
                        oldPuestoOfComidaPuestoListNewComidaPuesto.getComida().remove(comidaPuestoListNewComidaPuesto);
                        oldPuestoOfComidaPuestoListNewComidaPuesto = em.merge(oldPuestoOfComidaPuestoListNewComidaPuesto);
                    }
                }
            }
            for (Evaluacion evaluacionListNewEvaluacion : evaluacionListNew) {
                if (!evaluacionListOld.contains(evaluacionListNewEvaluacion)) {
                    Puesto oldPuestoOfEvaluacionListNewEvaluacion = evaluacionListNewEvaluacion.getPuesto();
                    evaluacionListNewEvaluacion.setPuesto(puesto);
                    evaluacionListNewEvaluacion = em.merge(evaluacionListNewEvaluacion);
                    if (oldPuestoOfEvaluacionListNewEvaluacion != null && !oldPuestoOfEvaluacionListNewEvaluacion.equals(puesto)) {
                        oldPuestoOfEvaluacionListNewEvaluacion.getEvaluaciones().remove(evaluacionListNewEvaluacion);
                        oldPuestoOfEvaluacionListNewEvaluacion = em.merge(oldPuestoOfEvaluacionListNewEvaluacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = puesto.getNombre();
                if (buscaNombre(id) == null) {
                    throw new EntidadInexistenteException("No existe puesto con id " + id);
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
            Puesto puesto;
            try {
                puesto = em.getReference(Puesto.class, id);
                puesto.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new EntidadInexistenteException("No existe puesto with id " + id, enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ComidaPuesto> comidaPuestoListOrphanCheck = puesto.getComida();
            for (ComidaPuesto comidaPuestoListOrphanCheckComidaPuesto : comidaPuestoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Puesto (" + puesto + ") cannot be destroyed since the ComidaPuesto " + comidaPuestoListOrphanCheckComidaPuesto + " in its comidaPuestoList field has a non-nullable puesto field.");
            }
            List<Evaluacion> evaluacionListOrphanCheck = puesto.getEvaluaciones();
            for (Evaluacion evaluacionListOrphanCheckEvaluacion : evaluacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Puesto (" + puesto + ") cannot be destroyed since the Evaluacion " + evaluacionListOrphanCheckEvaluacion + " in its evaluacionList field has a non-nullable puesto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new InconsistenciasException(illegalOrphanMessages);
            }
            em.remove(puesto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Puesto> buscaPuestos() {
        return buscaPuestos(true, -1, -1);
    }

    public List<Puesto> buscaPuestos(int maxResults, int firstResult) {
        return buscaPuestos(false, maxResults, firstResult);
    }

    private List<Puesto> buscaPuestos(boolean all, int maxResults, int firstResult) {
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

    public Puesto buscaNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Puesto.class, nombre);
        } finally {
            em.close();
        }
    }

    public Puesto buscaLugar(String latitud, String longitud) {
        try{
            EntityManager em = getEntityManager();
            return (Puesto)(em.createNamedQuery("Puesto.buscaLugar")
                    .setParameter("latitud", latitud).setParameter("longitud", longitud).getSingleResult());
        }catch(Exception ex){
            System.err.println(ex.getMessage() + "\nError al buscar el usuario con correo: " + latitud);
        } return null;
    }    

    public int cantidadDePuestos() {
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
