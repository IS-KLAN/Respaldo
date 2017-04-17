package com.klan.proyecto.modelo;

import com.klan.proyecto.modelo.Comida;
import com.klan.proyecto.modelo.ComidaPuestoPK;
import com.klan.proyecto.modelo.Puesto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-16T19:31:21")
@StaticMetamodel(ComidaPuesto.class)
public class ComidaPuesto_ { 

    public static volatile SingularAttribute<ComidaPuesto, ComidaPuestoPK> comidaPuestoPK;
    public static volatile SingularAttribute<ComidaPuesto, Puesto> puesto;
    public static volatile SingularAttribute<ComidaPuesto, Long> idComidaPuesto;
    public static volatile SingularAttribute<ComidaPuesto, Comida> comida;

}