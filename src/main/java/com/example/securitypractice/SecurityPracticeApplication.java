package com.example.securitypractice;

import com.example.securitypractice.enums.Role;
import com.example.securitypractice.model.User;
import com.example.securitypractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@RequiredArgsConstructor
public class SecurityPracticeApplication implements CommandLineRunner {
    @Autowired
private  UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(SecurityPracticeApplication.class, args);
    }

    @Override
    public void run(String... args){
        User adminAccount = userRepository.findByRole(Role.ADMIN);
        if (null == adminAccount){

            User user = new User();

            user.setFirstName("%");
            user.setLastName("%");
            user.setEmail("%");
            user.setPassword(new BCryptPasswordEncoder().encode("%"));
            userRepository.save(user);

        }

    }
}
