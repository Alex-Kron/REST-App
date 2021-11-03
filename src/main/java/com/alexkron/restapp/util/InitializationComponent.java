package com.alexkron.restapp.util;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.service.AdminService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Slf4j
public class InitializationComponent {
    @Autowired
    private AdminService adminService;

    @SneakyThrows
    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        User admin1 = new User();
        admin1.setLogin("admin1");
        admin1.setPassword("adminpas1");
        admin1.setName("Админ Номер Один");
        admin1.setAge(LocalDate.of(1990, 12, 31));
        admin1.setEmail("admin1@email.com");
        adminService.setUser(admin1);
        adminService.setUserRole(admin1.getLogin(), adminService.getRoleByName("ROLE_ADMIN"));

        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("userpas1");
        user1.setName("Юзер Номер Один");
        user1.setAge(LocalDate.of(2000, 12, 31));
        user1.setEmail("user1@email.com");
        adminService.setUser(user1);


        Profile userProfile = new Profile();
        userProfile.setCash(BigDecimal.valueOf(100));
        userProfile.setUser(adminService.getUserByLogin(user1.getLogin()));
        adminService.setProfile(userProfile);

        Profile adminProfile = new Profile();
        adminProfile.setCash(BigDecimal.valueOf(100000));
        adminProfile.setUser(adminService.getUserByLogin(admin1.getLogin()));
        adminService.setProfile(adminProfile);

        Phone phone1 = new Phone();
        phone1.setNumber("+79000000001");
        phone1.setUser(adminService.getUserByLogin(admin1.getLogin()));
        adminService.setPhone(phone1);

        Phone phone2 = new Phone();
        phone2.setNumber("+79000000002");
        phone2.setUser(adminService.getUserByLogin(user1.getLogin()));
        adminService.setPhone(phone2);

        Phone phone3 = new Phone();
        phone3.setNumber("+79000000003");
        phone3.setUser(adminService.getUserByLogin(user1.getLogin()));
        adminService.setPhone(phone3);
    }
}
