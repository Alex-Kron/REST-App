package com.alexkron.vitasoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
public class Phones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private BigInteger id;

    @Column(name = "NUMBER", columnDefinition = "CHAR(12)", unique = true)
    private String number;

    @ManyToOne(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private  Users user;
}
