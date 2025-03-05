package java16.instagrammfinalproject.service;

import java16.instagrammfinalproject.dto.response.AuthResponse;
import java16.instagrammfinalproject.dto.response.ProfileResponse;
import java16.instagrammfinalproject.dto.request.SingInRequest;
import java16.instagrammfinalproject.dto.request.SingUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
   AuthResponse singUp (SingUpRequest singUpRequest);
   AuthResponse singIn(SingInRequest singInRequest);
   ProfileResponse getProfile();
}
