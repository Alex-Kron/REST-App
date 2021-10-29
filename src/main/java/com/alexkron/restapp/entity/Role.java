package com.alexkron.restapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "INTEGER")
    private Integer roleId;

    @Column(name = "ROLE_NAME", columnDefinition = "VARCHAR(20)", unique = true, nullable = false)
    @Pattern(regexp = "ROLE_\\D*")
    private String roleName;
}
