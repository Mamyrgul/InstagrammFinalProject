package java16.instagrammfinalproject.dto.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
