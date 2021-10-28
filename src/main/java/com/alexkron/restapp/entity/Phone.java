package com.alexkron.restapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PHONES")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long pholeId;

    @Column(name = "NUMBER", columnDefinition = "VARCHAR(12)", unique = true, nullable = false)
    private String number;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;
}
