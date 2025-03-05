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

    @PreAuthorize("@securityService.isCurrentUser(#request.userId)")
    @PostMapping("/post/{postId}")
    public ResponseEntity<String> toggleLikeOnPost(@PathVariable Long postId) {
        likeService.toggleLikeOnPost(postId);
        return ResponseEntity.ok("Like toggled successfully");
    }

    @PreAuthorize("@securityService.isCurrentUser(#request.userId)")
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<String> toggleLikeOnComment(@PathVariable Long commentId) {
        likeService.toggleLikeOnComment(commentId);
        return ResponseEntity.ok("Like toggled successfully");
    }
}

