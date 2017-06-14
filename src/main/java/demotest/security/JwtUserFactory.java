package demotest.security;

import demotest.entity.AdminUser;
import demotest.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by simon on 2017/6/2.
 */
public final class JwtUserFactory {

    private JwtUserFactory(){

    }

    public static JwtUser create(AdminUser user) {
        return new JwtUser(user.getId().toString(), user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getRoles()), user.getLastPasswordResetDate());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
