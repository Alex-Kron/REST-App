package com.alexkron.vitasoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Table(name="USERS")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private BigInteger id;

    @Column(name = "NAME", unique = true, columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "AGE", columnDefinition = "DATE")
    private LocalDate age;

    @Column(name = "EMAIL", unique = true, columnDefinition = "VARCHAR(50)")
    private String email;
}
