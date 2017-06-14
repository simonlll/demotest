package demotest.security;

import java.io.Serializable;

/**
 * Created by simon on 2017/6/2.
 */
public class JwtAuthenticationRequest implements Serializable {
    private static final long serailVersionUID = -8445943548966154778L;

    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}