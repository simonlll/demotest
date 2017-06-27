package demotest.auth;

import demotest.entity.AdminUser;

/**
 * Created by simon on 2017/6/2.
 */
public interface AuthService {
    AdminUser register(AdminUser user);

    String login(String username, String password);

    String refresh(String oldToken);

    String logout(String token);

}
