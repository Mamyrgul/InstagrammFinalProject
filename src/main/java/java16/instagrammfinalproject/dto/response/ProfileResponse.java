package java16.instagrammfinalproject.dto;

import java16.instagrammfinalproject.enums.Role;
import lombok.Builder;

@Builder
public record ProfileResponse(
        Long id,
        String email,
        Role role
) {

}
