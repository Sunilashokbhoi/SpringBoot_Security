package com.secure.notes.secure.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class LoginResponse {
    private String jwtToken;
    private String usetname;
    private List<String> roles;

    public LoginResponse(String usetname,List<String> roles ,String jwtToken)
    {
        this.usetname = usetname;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }
}
