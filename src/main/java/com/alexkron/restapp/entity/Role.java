package com.alexkron.restapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INTEGER")
    private Integer id;

    @Column(name = "ROLE_NAME", columnDefinition = "VARCHAR(5)", unique = true, nullable = false)
    private String roleName;
}
