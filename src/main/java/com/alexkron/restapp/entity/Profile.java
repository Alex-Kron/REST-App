package com.alexkron.restapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROFILES")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long profileId;

    @Column(name = "CASH", columnDefinition = "NUMERIC(10,2)", nullable = false)
    private BigDecimal cash;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;
}
