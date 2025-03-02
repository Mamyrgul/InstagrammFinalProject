package java16.instagrammfinalproject.service;

import java16.instagrammfinalproject.dto.request.PostRequest;
import java16.instagrammfinalproject.dto.request.PostUpdateRequest;
import java16.instagrammfinalproject.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    void createPost(PostRequest request);
    void updatePost(Long postId, PostUpdateRequest request);
    List<PostResponse> getUserFeed(Long userId);
    void deletePost(Long postId);
    String addMentionToPost(Long postId, String firstName);
}
