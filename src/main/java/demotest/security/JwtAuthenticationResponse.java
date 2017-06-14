package demotest.security;

import java.io.Serializable;

/**
 * Created by simon on 2017/6/2.
 */
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1230166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
