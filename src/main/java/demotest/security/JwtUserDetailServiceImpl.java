package demotest.security;

import demotest.dao.AdminUserRepository;
import demotest.entity.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by simon on 2017/6/2.
 */
@Service
public class JwtUserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByUsername(username);

        if (adminUser == null) {
            throw new UsernameNotFoundException(String.format("not user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(adminUser);
        }
    }
}
