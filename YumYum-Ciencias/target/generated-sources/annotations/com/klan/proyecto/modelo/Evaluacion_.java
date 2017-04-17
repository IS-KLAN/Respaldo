package com.klan.proyecto.modelo;

import com.klan.proyecto.modelo.EvaluacionPK;
import com.klan.proyecto.modelo.Puesto;
import com.klan.proyecto.modelo.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-16T19:31:21")
@StaticMetamodel(Evaluacion.class)
public class Evaluacion_ { 

    public static volatile SingularAttribute<Evaluacion, Long> idEvaluacion;
    public static volatile SingularAttribute<Evaluacion, Puesto> puesto;
    public static volatile SingularAttribute<Evaluacion, Integer> calificacion;
    public static volatile SingularAttribute<Evaluacion, Usuario> usuario;
    public static volatile SingularAttribute<Evaluacion, EvaluacionPK> evaluacionPK;
    public static volatile SingularAttribute<Evaluacion, String> comentario;

}