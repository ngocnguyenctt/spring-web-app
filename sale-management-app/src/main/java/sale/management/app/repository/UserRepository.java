package sale.management.app.repository;

import org.springframework.stereotype.Repository;
import sale.management.app.model.User;

import java.util.Optional;

/**
 * @author ngoc on 04/05/2018
 * @subject sale-management-app
 */

@Repository
public interface UserRepository extends CommonRepository<User>
{
    Optional<User> findByUsername(String username);
}
