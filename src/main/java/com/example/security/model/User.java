package com.example.security.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.PrivilegedAction;
import java.sql.Timestamp;


@Entity
@Data
public class User {

    @Id //primarykey
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private int id;
    private String username;
    private String password;
    private String email;
    private String role; //ROLE_USER, ROLE_ADMIN

    //private Timestamp loginDate; (휴면 계정을 이용할 시)
    @CreationTimestamp
    private Timestamp createDate;

}
