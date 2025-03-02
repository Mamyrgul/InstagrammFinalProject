package java16.instagrammfinalproject.service;

import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.models.User;

import java.util.Optional;

public interface MentionService {
    void addMentionToPost(Post post, Optional<User> user);
    void addMentionToComment(Comment comment, User user);
}
