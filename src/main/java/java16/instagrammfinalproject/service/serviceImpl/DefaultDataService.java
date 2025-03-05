package java16.instagrammfinalproject.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import java16.instagrammfinalproject.enums.Role;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class DefaultDataService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        String email = "admin@gmail.com";
        boolean exists = userRepo.existsByEmail(email);
        if (!exists) {
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("InstagrammFinalProject")
                    .email(email)
                    .password(passwordEncoder.encode("adminstration11"))
                    .role(Role.ADMIN)
                    .build();
            userRepo.save(admin);
        }
    }
}

