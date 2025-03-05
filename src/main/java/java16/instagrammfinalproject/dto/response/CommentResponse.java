package java16.instagrammfinalproject.dto.response;

import java.time.LocalDate;

public record CommentResponse(
         Long id,
         String comment,
         int likeCount,
         LocalDate createdAt
) {
}
