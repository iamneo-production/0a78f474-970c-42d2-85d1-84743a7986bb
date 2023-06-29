package com.example.springapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;



@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long id;
  public String email;
  public String password;
  public String role;

  protected User() {

  }

  public User(String email,String password,String role){
    
    this.email = email;
    this.password = password;
    this.role = role;
    }

    public long getId() {
        return id;
    }

   

    // Getter and Setter methods for the email field
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter methods for the password field
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter methods for the role field
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

