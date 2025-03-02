package java16.instagrammfinalproject.service.serviceImpl;

import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Like;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.CommentRepo;
import java16.instagrammfinalproject.repo.LikeRepo;
import java16.instagrammfinalproject.repo.PostRepo;
import java16.instagrammfinalproject.repo.UserRepo;
import java16.instagrammfinalproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

        private final LikeRepo likeRepository;
        private final PostRepo postRepository;
        private final CommentRepo commentRepository;
        private final UserRepo userRepository;

        // Add or remove like on a post
        public void toggleLikeOnPost(Long postId, Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

            // Check if the user has already liked the post
            Like like = likeRepository.findByUserAndPost(user, post);

            if (like != null) {
                // If the user has already liked the post, remove the like
                post.removeLike(like);
                likeRepository.delete(like);
            } else {
                // If the user hasn't liked the post yet, add the like
                like = Like.builder().user(user).post(post).isLiked(true).build();
                post.addLike(like);
                likeRepository.save(like);
            }

            postRepository.save(post);
        }

        // Add or remove like on a comment
        public void toggleLikeOnComment(Long commentId, Long userId) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

            // Check if the user has already liked the comment
            Like like = likeRepository.findByUserAndComment(user, comment);

            if (like != null) {
                // If the user has already liked the comment, remove the like
                comment.removeLike(like);
                likeRepository.delete(like);
            } else {
                // If the user hasn't liked the comment yet, add the like
                like = Like.builder().user(user).comment(comment).isLiked(true).build();
                comment.addLike(like);
                likeRepository.save(like);
            }

            commentRepository.save(comment);
        }
    }

