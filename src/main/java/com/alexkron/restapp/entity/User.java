package com.alexkron.restapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "NAME", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    private String name;

    @Column(name = "AGE", columnDefinition = "DATE", nullable = false)
    private LocalDate age;

    @Column(name = "EMAIL", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;

    @ManyToOne(targetEntity = Role.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role role;

    @Override
    public String toString() {
        return "USER:" +
                "ID = " + id +
                "NAME = " + name +
                "AGE = " + age.toString() +
                "EMAIL = " + email;
    }
}
