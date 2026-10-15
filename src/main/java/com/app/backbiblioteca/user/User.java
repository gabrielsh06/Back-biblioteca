package com.app.backbiblioteca.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Roles role;
    private String email;
    private String password;

}
