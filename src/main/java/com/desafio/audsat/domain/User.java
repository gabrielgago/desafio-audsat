package com.desafio.audsat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User implements Serializable {
    @Id
    private String username;
    private String password;
}
