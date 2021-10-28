package com.alexkron.restapp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long userId;

    @Column(name = "LOGIN", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    private String login;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(60)", nullable = false)
    private String password;

    @Column(name = "NAME", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(name = "AGE", columnDefinition = "DATE", nullable = false)
    private LocalDate age;

    @Email(message = "Email should be valid")
    @Column(name = "EMAIL", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;

    @ManyToOne(targetEntity = Role.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role role;
}
