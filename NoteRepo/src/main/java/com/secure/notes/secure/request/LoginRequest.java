package com.secure.notes.secure.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;

}
