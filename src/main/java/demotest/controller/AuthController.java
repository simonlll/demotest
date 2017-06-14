package demotest.controller;

import demotest.auth.AuthService;
import demotest.entity.AdminUser;
import demotest.security.JwtAuthenticationRequest;
import demotest.security.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

/**
 * Created by simon on 2017/6/2.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public AdminUser register(@RequestBody AdminUser user) throws AuthenticationException {
        return authService.register(user);
    }
}
