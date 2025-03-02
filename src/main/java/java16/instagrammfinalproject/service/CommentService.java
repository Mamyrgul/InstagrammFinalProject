package java16.instagrammfinalproject.service;

import java16.instagrammfinalproject.models.Comment;

import java.util.List;

public interface CommentService {
    Comment saveComment(Long postId, Long userId, String text);
    List<Comment> findAllByPostId(Long postId);
    void deleteById(Long commentId);
    Long findPostIdByCommentId(Long commentId);
}
