package sale.management.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sale.management.app.model.User;
import sale.management.app.repository.UserRepository;

import java.util.Optional;

/**
 * @author ngoc on 04/05/2018
 * @subject sale-management-app
 */

@Service
public class UserService extends CommonService<User>
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    public Optional<User> findUserbyUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> search(User user, Pageable pageable)
    {
        return null;
    }
}
