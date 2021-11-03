package com.alexkron.restapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "PROFILES")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long profileId;

    @Column(name = "CASH", columnDefinition = "NUMERIC(10,2)", nullable = false)
    @DecimalMin(value = "0.00")
    @Digits(integer = 6, fraction = 2)
    private BigDecimal cash;

    @OneToOne(optional = false, cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private User user;
}
