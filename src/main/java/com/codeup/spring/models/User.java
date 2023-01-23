package com.codeup.spring.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true, nullable = false, length = 100)
    private String username;

    @Column(unique=true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Post> posts;


//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public User(long id, String username, String email, String password) {
//        this.id = id;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }
//
//    public User(String username, String email, String password) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//    }
//
//    public User() {
//
//    }

    public User(User copy) {
        id = copy.id;
        username = copy.username;
        email = copy.email;
        password = copy.password;
    }

}
