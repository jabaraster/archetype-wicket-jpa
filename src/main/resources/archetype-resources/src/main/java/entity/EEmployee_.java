package jabara.aaa.entity;

import jabara.jpa.entity.EntityBase_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(EEmployee_.class)
public class EEmployee_ extends EntityBase_ {
    public static volatile SingularAttribute<EEmployee, String> code;
    public static volatile SingularAttribute<EEmployee, String> name;
}
