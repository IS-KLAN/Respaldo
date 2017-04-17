package com.klan.proyecto.modelo;

import com.klan.proyecto.modelo.ComidaPuesto;
import com.klan.proyecto.modelo.Evaluacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-16T19:31:21")
@StaticMetamodel(Puesto.class)
public class Puesto_ { 

    public static volatile SingularAttribute<Puesto, String> descripcion;
    public static volatile SingularAttribute<Puesto, String> nombrePuesto;
    public static volatile SingularAttribute<Puesto, String> latitud;
    public static volatile SingularAttribute<Puesto, String> longitud;
    public static volatile ListAttribute<Puesto, ComidaPuesto> comidaPuestoList;
    public static volatile ListAttribute<Puesto, Evaluacion> evaluacionList;
    public static volatile SingularAttribute<Puesto, Long> idPuesto;
    public static volatile SingularAttribute<Puesto, String> rutaImagen;

}