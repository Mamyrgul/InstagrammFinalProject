package java16.instagrammfinalproject.dto.request;

import lombok.Builder;

@Builder
public record CommentRequest (
        String comment
){
}
