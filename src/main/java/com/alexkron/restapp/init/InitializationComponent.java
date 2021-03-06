package com.alexkron.restapp.init;

import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.service.AdminService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Slf4j
@Component
public class InitializationComponent {
    @Autowired
    private AdminService adminService;

    @SneakyThrows
    @PostConstruct
    public void contextRefreshedEvent() {
        User admin1 = new User();
        admin1.setLogin("admin1");
        admin1.setPassword("adminpas1");
        admin1.setName("Админ Номер Один");
        admin1.setAge(LocalDate.of(1990, 12, 31));
        admin1.setEmail("admin1@email.com");
        adminService.setUser(admin1);
        adminService.setUserRole(admin1.getLogin(), adminService.getRoleByName("ROLE_ADMIN"));
        log.info("Administrator user initialized: login=admin1, password=adminpas1");
    }
}
