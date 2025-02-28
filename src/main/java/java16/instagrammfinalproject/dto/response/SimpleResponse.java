package java16.instagrammfinalproject.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

// Getter Setter
// AllArgConstructor NoArgConstructor
@Builder
public record SimpleResponse(
        HttpStatus httpStatus,
        String message
) {
}
