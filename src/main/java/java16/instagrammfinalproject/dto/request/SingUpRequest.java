package java16.instagrammfinalproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SingUpRequest(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(min = 3, max = 50, message = "Имя должно быть от 3 до 50 символов")
        String firstName,

        @NotBlank(message = "Фамилия не может быть пустой")
        @Size(min = 3, max = 50, message = "Фамилия должна быть от 3 до 50 символов")
        String lastName,

        @NotBlank(message = "Номер телефона не может быть пустым")
        @Pattern(regexp = "^\\+996\\d{9}$", message = "Номер телефона должен начинаться с +996 и содержать 13 цифр")
        String phoneNumber,

        @NotBlank(message = "Email не может быть пустым")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|[A-Za-z0-9-]+\\.org)$",
                message = "Email должен быть @gmail.com, @yahoo.com или заканчиваться на .org"
        )
        String email,

        @NotBlank(message = "Пароль не должен быть пустым")
        @Size(min = 8, max = 20, message = "Пароль должен быть от 8 до 20 символов")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Пароль должен быть не менее 8 символов, содержать хотя бы одну букву и одну цифру"
        )
        String password
) {
}
