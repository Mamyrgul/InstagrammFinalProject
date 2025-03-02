package java16.instagrammfinalproject.dto.response;

import java16.instagrammfinalproject.enums.Role;
import lombok.Builder;

@Builder
public record AuthResponse(
        String token,
        Role role
) {

}
