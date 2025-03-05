package java16.instagrammfinalproject.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import java16.instagrammfinalproject.config.jwtConfig.JwtService;
import java16.instagrammfinalproject.dto.response.AuthResponse;
import java16.instagrammfinalproject.dto.response.ProfileResponse;
import java16.instagrammfinalproject.dto.request.SingInRequest;
import java16.instagrammfinalproject.dto.request.SingUpRequest;
import java16.instagrammfinalproject.enums.Role;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.UserRepo;
import java16.instagrammfinalproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse singUp(SingUpRequest singUpRequest) {
        if (userRepo.existsByEmail(singUpRequest.email())){
            throw new  RuntimeException(String.format(" User with email %s  already exist " , singUpRequest.email()));
        }
        User user = User
                .builder()
                .firstName(singUpRequest.firstName())
                .lastName(singUpRequest.lastName())
                .phoneNumber(singUpRequest.phoneNumber())
                .email(singUpRequest.email())
                .password(passwordEncoder.encode(singUpRequest.password()))
                .role(Role.USER)
                .build();
        userRepo.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse
                .builder()
                .token(token)
                .role(user.getRole())
                .build();
    }
    @Override
    public AuthResponse singIn(SingInRequest singInRequest) {
        User user = userRepo.findUserByEmail(singInRequest.email()).orElseThrow(()
                -> new EntityNotFoundException(String.format("User with email %s not found!", singInRequest.email())));
        if (!passwordEncoder.matches(singInRequest.password(),user.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse
                .builder()
                .token(token)
                .role(user.getRole())
                .build();
    }

    @Override
    public ProfileResponse getProfile() {
        return null;
    }
}
