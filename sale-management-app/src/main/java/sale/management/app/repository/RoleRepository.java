package sale.management.app.repository;

import org.springframework.stereotype.Repository;
import sale.management.app.model.Role;

import java.util.Optional;

/**
 * @author ngoc on 04/05/2018
 * @subject sale-management-app
 */

@Repository
public interface RoleRepository extends CommonRepository<Role>
{
    Optional<Role> findByRoleName(String roleName);
}
