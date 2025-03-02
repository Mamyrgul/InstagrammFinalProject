package java16.instagrammfinalproject.api;

import java16.instagrammfinalproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeApi {

    private final LikeService likeService;

    // Endpoint to toggle like on a post
    @PreAuthorize("@securityService.isCurrentUser(#userId)")
    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<String> toggleLikeOnPost(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.toggleLikeOnPost(postId, userId);
        return ResponseEntity.ok("Like toggled successfully");
    }

    // Endpoint to toggle like on a comment
    @PreAuthorize("@securityService.isCurrentUser(#userId)")
    @PostMapping("/comment/{commentId}/user/{userId}")
    public ResponseEntity<String> toggleLikeOnComment(@PathVariable Long commentId, @PathVariable Long userId) {
        likeService.toggleLikeOnComment(commentId, userId);
        return ResponseEntity.ok("Like toggled successfully");
    }
}

