package com.alexkron.restapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT")
    private Long userId;

    @Column(name = "LOGIN", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    @NotBlank
    @Pattern(regexp = "\\w{4,20}")
    private String login;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(60)", nullable = false)
    @Pattern(regexp = ".{1,60}")
    private String password;

    @Column(name = "NAME", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+")
    private String name;

    @Column(name = "AGE", columnDefinition = "DATE", nullable = false)
    @Past
    private LocalDate age;

    @Email(message = "Email should be valid")
    @Column(name = "EMAIL", unique = true, columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;

 //   @OneToMany(cascade = CascadeType.ALL,
 //           mappedBy = "user", orphanRemoval = true)
 //   private List<Phone> phones = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    @JsonIgnore
    private Profile profile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    @Valid
    private Role role;
}
