package java16.instagrammfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SingInRequest(
        @NotBlank(message = "Email не может быть пустым")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|[A-Za-z0-9.-]+\\.org)$",
                message = "Email должен быть @gmail.com, @yahoo.com или заканчиваться на .org")
        String email,

        @NotBlank(message = "Пароль не должен быть пустым")
        @Size(min = 8, max = 20, message = "Пароль должен быть от 8 до 20 символов")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Пароль должен быть не менее 8 символов, содержать хотя бы одну букву и одну цифру")
        String password
) {
}
