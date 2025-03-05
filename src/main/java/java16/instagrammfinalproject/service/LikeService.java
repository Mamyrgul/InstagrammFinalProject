package java16.instagrammfinalproject.service;

public interface LikeService {
    void toggleLikeOnPost(Long postId);
    void toggleLikeOnComment(Long commentId);
}
