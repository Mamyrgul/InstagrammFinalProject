package java16.instagrammfinalproject.service;

public interface LikeService {
    void toggleLikeOnPost(Long postId, Long userId);
    void toggleLikeOnComment(Long commentId, Long userId);
}
