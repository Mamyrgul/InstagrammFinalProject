package java16.instagrammfinalproject.dto;

import lombok.Builder;

@Builder
public record SingInRequest(
        String email,
        String password
) {
}
