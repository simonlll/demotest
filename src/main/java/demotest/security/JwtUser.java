package demotest.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by simon on 2017/6/2.
 */
public class JwtUser implements UserDetails {
    private final String id;
    private final String username;
    private final String password;
    //存储用户拥有的角色
    private final Collection<? extends GrantedAuthority> authorities;


    public JwtUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    //账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    //密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}