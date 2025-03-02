package java16.instagrammfinalproject.exceptions.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
        HttpStatus status,
        String exceptionsClassName,
        String message
) {
}
