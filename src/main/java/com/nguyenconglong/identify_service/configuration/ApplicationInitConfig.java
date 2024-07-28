package com.nguyenconglong.identify_service.configuration;

import com.nguyenconglong.identify_service.entity.Role;
import com.nguyenconglong.identify_service.entity.User;
import com.nguyenconglong.identify_service.repository.RoleRepository;
import com.nguyenconglong.identify_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(prefix = "spring",
    value = "datasource.driverClassName",
    havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            if(roleRepository.findById("ADMIN").isEmpty()){
                Role role = Role.builder()
                        .name("ADMIN")
                        .description("Admin role")
                        .permissions(new HashSet<>())
                        .build();
                roleRepository.save(role);
                log.warn("Admin role has been created");
            }
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findById("ADMIN").orElseThrow();
                List<Role> roles = new ArrayList<>();
                roles.add(adminRole);
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(new HashSet<>(roles))
                        .build();
                userRepository.save(user);
                log.warn("Admin has been created");
            }
        };
    }
}
