package demotest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by simon on 2017/6/2.
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    //jwtTokenUtil是JWT用来生成和解码JWT的TOKEN的
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    //tokenHead: "Bearer "
    @Value("${jwt.header}")
    private String tokenHeader;


    //header: Authorization
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);

        if (authHeader != null && authHeader.startsWith(tokenHead)) {

            final String authToken = authHeader.substring(tokenHead.length()); //the part after "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            logger.info("checking authentication " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //调用userDetailService从数据库的admin_user表中获取user的信息，用户名，密码和角色
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                System.out.println("userDetail info:" + userDetails.getUsername()+ " " + userDetails.getPassword()+ " " + userDetails.getAuthorities().toString());
                //将客户端传来的authToken和数据库取来的信息userDetails进行比对
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
