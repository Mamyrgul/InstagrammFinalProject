package java16.instagrammfinalproject.valid;

import com.auth0.jwt.interfaces.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validation {
    String message() default "Некорректный опыт или возраст для роли";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
