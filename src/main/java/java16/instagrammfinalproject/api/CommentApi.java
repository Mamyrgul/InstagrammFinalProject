package java16.instagrammfinalproject.api;

import jakarta.annotation.security.PermitAll;
import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    // ✅ Сохранение комментария
    @PreAuthorize("@securityService.isCurrentUser(#userId)")
    @PostMapping
    public ResponseEntity<Comment> saveComment(
            @RequestParam Long postId,
            @RequestParam Long userId,
            @RequestParam String text) {
        return ResponseEntity.ok(commentService.saveComment(postId, userId, text));
    }

    // ✅ Получить все комментарии по postId
    @PermitAll
    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findAllByPostId(postId));
    }

    // ✅ Удалить комментарий (и все его лайки)
    @PreAuthorize("@securityService.isCommentOwner(#commentId) or @securityService.isPostOwner(@commentService.findPostIdByCommentId(#commentId))")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}

