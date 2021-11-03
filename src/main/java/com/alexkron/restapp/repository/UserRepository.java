package com.alexkron.restapp.repository;

import com.alexkron.restapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN Phone p ON u = p.user WHERE " +
            "u.age BETWEEN :dateBefore and :dateAfter " +
            "AND p.number LIKE CASE WHEN :number<>'' THEN :number " +
            "ELSE '%' END " +
            "AND u.email LIKE CASE WHEN :email<>'' THEN :email " +
            "ELSE '%' END " +
            "AND u.name LIKE CONCAT('%', :name, '%')")
    Page<User> findAllWithFilters(@Param("dateBefore") LocalDate dateBefore,
                                  @Param("dateAfter") LocalDate dateAfter,
                                  @Param("name") String name,
                                  @Param("number") String number,
                                  @Param("email") String email,
                                  Pageable pageable);
}
