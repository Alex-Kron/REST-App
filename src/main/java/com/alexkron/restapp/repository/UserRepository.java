package com.alexkron.restapp.repository;

import com.alexkron.restapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
