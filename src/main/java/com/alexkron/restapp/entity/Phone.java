package com.alexkron.restapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

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
    @Pattern(regexp = "\\+7\\d{10}")
    private String number;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Valid
    private User user;
}
