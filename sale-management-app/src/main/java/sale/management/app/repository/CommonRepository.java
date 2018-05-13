package sale.management.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author ngoc on 20/04/2018
 * @subject sale-management-app
 */

@NoRepositoryBean
public interface CommonRepository<T> extends JpaRepository<T, String>, JpaSpecificationExecutor<T>
{
}
