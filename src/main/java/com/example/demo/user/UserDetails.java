package com.example.demo.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDetails {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    // FIXME Passwords should never be stored in plain text!
    @Size(min = 4, max = 64, message = "Password must be 4-64 char long")
    private String password;

    public String getUsername() {return username;}

    public void setUsername(String firstName) {this.username = firstName;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

}