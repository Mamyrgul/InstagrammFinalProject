package java16.instagrammfinalproject.api;

import jakarta.annotation.security.PermitAll;
import java16.instagrammfinalproject.dto.request.PostRequest;
import java16.instagrammfinalproject.dto.request.PostUpdateRequest;
import java16.instagrammfinalproject.dto.response.PostResponse;
import java16.instagrammfinalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApi {
    private final PostService postService;

    @PreAuthorize("@securityService.isCurrentUser(#request.userId)")
    @PostMapping()
    public ResponseEntity<String> createPost(@RequestBody PostRequest request) {
        postService.createPost(request);
        return ResponseEntity.ok("Пост успешно создан!");
    }

    @PreAuthorize("@securityService.isOwnerr(#postId)")
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok("Пост обновлен!");
    }
    @PreAuthorize("@securityService.isOwnerr(#postId)")
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Пост удален!");
    }

    @GetMapping("/feed/{userId}")
    public List<PostResponse> getUserFeed(@PathVariable Long userId) {
        return postService.getUserFeed(userId);
    }
    @PermitAll
    @PostMapping("/mention/{postId}")
    public ResponseEntity<String> mentionUserInPost(@PathVariable Long postId, @RequestParam String firstName) {
        try {
            postService.addMentionToPost(postId, firstName);
            return ResponseEntity.ok("User mentioned in post successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

