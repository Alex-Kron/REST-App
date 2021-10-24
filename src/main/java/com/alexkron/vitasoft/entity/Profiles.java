package com.alexkron.vitasoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
@Table(name = "PROFILES")
public class Profiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private BigInteger id;

    @Column(name = "CASH", columnDefinition = "MONEY")
    private Money money;

    @ManyToOne(targetEntity = Users.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private  Users user;
}
