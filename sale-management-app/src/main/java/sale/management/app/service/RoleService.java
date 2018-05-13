package sale.management.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sale.management.app.model.Role;

/**
 * @author ngoc on 04/05/2018
 * @subject sale-management-app
 */

@Service
public class RoleService extends CommonService<Role>
{
    @Override
    public Page<Role> search(Role role, Pageable pageable)
    {
        return null;
    }
}
