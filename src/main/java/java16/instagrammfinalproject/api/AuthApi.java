package java16.instagrammfinalproject.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import java16.instagrammfinalproject.dto.response.AuthResponse;
import java16.instagrammfinalproject.dto.request.SingInRequest;
import java16.instagrammfinalproject.dto.request.SingUpRequest;
import java16.instagrammfinalproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {
    private final AuthService authService;
    @PostMapping("/singUp")
    public AuthResponse singUp(@Valid @RequestBody SingUpRequest singUpRequest){
       return  authService.singUp(singUpRequest);
    }

    @PostMapping("/singIn")
    public AuthResponse singIn(@Valid @RequestBody SingInRequest singInRequest){
        return authService.singIn(singInRequest);
    }


}
