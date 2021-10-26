package com.alexkron.restapp.repository;

import com.alexkron.restapp.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByUserId(Long userId);
}
