package java16.instagrammfinalproject.service.serviceImpl;

import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Like;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.repo.CommentRepo;
import java16.instagrammfinalproject.repo.LikeRepo;
import java16.instagrammfinalproject.repo.PostRepo;
import java16.instagrammfinalproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

        private final LikeRepo likeRepository;
        private final PostRepo postRepository;
        private final CommentRepo commentRepository;

        public void toggleLikeOnPost(Long postId) {
            Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

            Like like = likeRepository.findByPost( post);

            if (like != null) {
                post.removeLike(like);
                likeRepository.delete(like);
            } else {
                like = Like.builder().post(post).isLiked(true).build();
                post.addLike(like);
                likeRepository.save(like);
            }

            postRepository.save(post);
        }

        public void toggleLikeOnComment(Long commentId) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));

            Like like = likeRepository.findByComment( comment);

            if (like != null) {
                comment.removeLike(like);
                likeRepository.delete(like);
            } else {
                like = Like.builder().comment(comment).isLiked(true).build();
                comment.addLike(like);
                likeRepository.save(like);
            }

            commentRepository.save(comment);
        }
    }

