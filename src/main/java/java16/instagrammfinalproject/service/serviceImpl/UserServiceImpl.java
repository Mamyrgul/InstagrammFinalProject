package java16.instagrammfinalproject.service.serviceImpl;

import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.UserRepo;
import java16.instagrammfinalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
        private final UserRepo userRepository;

        // Метод для поиска пользователя по firstName
        public User findByFirstName(String firstName) {
            return userRepository.findByFirstName(firstName)
                    .orElseThrow(() -> new RuntimeException("User with first name " + firstName + " not found"));
        }
    }

