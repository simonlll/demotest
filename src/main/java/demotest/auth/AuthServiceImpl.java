package demotest.auth;

import demotest.dao.AdminUserRepository;
import demotest.entity.AdminUser;
import demotest.entity.Role;
import demotest.security.JwtTokenUtil;
import demotest.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.fill;

/**
 * Created by simon on 2017/6/2.
 */
@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private AdminUserRepository adminUserRepository;

    //tokenHead: Bearer
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            AdminUserRepository adminUserRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.adminUserRepository = adminUserRepository;
    }

    @Override
    public AdminUser register(AdminUser user) {
        final String username = user.getUsername();
        if (adminUserRepository.findByUsername(username) != null) {
            return null;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        List<Role> roles =new ArrayList<>();
        Role role2 = new Role();
        role2.setName("ROLE_USER");
        roles.add(role);
        roles.add(role2);
        user.setRole(roles);
        return adminUserRepository.save(user);
    }

    /**
     * 根据用户名和密码，返回token
     * @param username
     * @param password
     * @return
     */
    @Override
    public String login(String username, String password) {
        System.out.println("username: " + username + " password: " + password);
        //根据用户名和密码生成一个Spring Security内部使用的token
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        System.out.println("upToken: " + upToken);
        //打印结果
        //upToken: org.springframework.security.authentication.UsernamePasswordAuthenticationToken@1f: Principal: wu;
        //Credentials: [PROTECTED]; Authenticated: false; Details: null; Not granted any authorities

        //Spring Security用这个upToken进行验证
        //日志文件显示， 去数据库里面查询并比较用户名和密码
        //2017-06-13 16:56:02.357 DEBUG 47553 --- [nio-8099-exec-1] o.s.s.authentication.ProviderManager     : Authentication attempt using org.springframework.security.authentication.dao.DaoAuthenticationProvider
        //2017-06-13 16:56:02.380  INFO 47553 --- [nio-8099-exec-1] o.h.h.i.QueryTranslatorFactoryInitiator  : HHH000397: Using ASTQueryTranslatorFactory
        //Hibernate: select adminuser0_.id as id1_0_, adminuser0_.email as email2_0_, adminuser0_.password as password3_0_, adminuser0_.username as username4_0_ from admin_user adminuser0_ where adminuser0_.username=?
        //Hibernate: select role0_.adminuser_id as adminuse3_3_0_, role0_.id as id1_3_0_, role0_.id as id1_3_1_, role0_.name as name2_3_1_ from role role0_ where role0_.adminuser_id=?
        final Authentication authentication = authenticationManager.authenticate(upToken);

        System.out.println("result: " + authentication);
        //打印结果 显示验证成功， 如果验证不成功，会自动返回403错误
        //result: org.springframework.security.authentication.UsernamePasswordAuthenticationToken@55074e2b:
        //Principal: demotest.security.JwtUser@15a487c9; Credentials: [PROTECTED];
        //Authenticated: true; Details: null; Granted Authorities: ROLE_ADMIN


        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("token: " + token);
        return token;
    }

    /**
     * 刷新Token, 逻辑是： 如果token未过期，并且token创建之后，没有重置密码，就刷新token
     * @param oldToken
     * @return
     */
    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public String logout(String token) {
        return null;
    }
}
