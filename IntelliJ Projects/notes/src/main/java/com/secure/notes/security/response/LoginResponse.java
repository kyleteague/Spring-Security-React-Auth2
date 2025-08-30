package com.secure.notes.security.response;

import java.util.List;

@lombok.Setter
@lombok.Getter
public class LoginResponse {
    private String jwtToken;

    private String username;
     private List<String> roles;

    public LoginResponse(String username, List<String> roles, String jwtToken) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }
}
