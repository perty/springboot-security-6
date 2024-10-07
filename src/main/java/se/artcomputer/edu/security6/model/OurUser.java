package se.artcomputer.edu.security6.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ourusers")
public class OurUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String email;
    private String password;
    private String roles;

    public OurUser() {
    }

    public OurUser(String email, String roles, String encode) {
        this.email = email;
        this.roles = roles;
        this.password = encode;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public int getId() {
        return id;
    }

    public String getRoles() {
        return roles;
    }
}
