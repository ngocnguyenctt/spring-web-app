package sale.management.app.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ngoc on 04/05/2018
 * @subject sale-management-app
 */

public class IdGenerator implements IdentifierGenerator, Configurable
{
    private String prefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException
    {
        String query = String.format("select %s from %s",
                session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
                obj.getClass().getSimpleName());
        List<String> ids = new ArrayList<>();
        for (Object object : session.createQuery(query).getResultList()) {
            ids.add((String) object);
        }
        Long max = ids.stream().map(id -> id.replace(prefix, ""))
                .mapToLong(Long::parseLong).max().orElse(0L);
        return prefix + (max + 1);
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException
    {
        prefix = properties.getProperty("prefix");
    }
}
