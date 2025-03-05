package java16.instagrammfinalproject.service.serviceImpl;

import java16.instagrammfinalproject.dto.response.CommentResponse;
import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Like;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.CommentRepo;
import java16.instagrammfinalproject.repo.LikeRepo;
import java16.instagrammfinalproject.repo.PostRepo;
import java16.instagrammfinalproject.repo.UserRepo;
import java16.instagrammfinalproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
        private final CommentRepo commentRepository;
        private final LikeRepo likeRepository;
        private final PostRepo postRepository;
        private final UserRepo userRepository;

        public Comment saveComment(Long postId, Long userId, String text) {
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Comment comment = Comment.builder()
                    .comment(text)
                    .likeCount(0)
                    .user(user)
                    .post(post)
                    .createdAt(LocalDate.now())
                    .build();

            commentRepository.save(comment);

            Like like = Like.builder()
                    .comment(comment)
                    .user(user)
                    .post(post)
                    .isLiked(false)
                    .build();

            likeRepository.save(like);

            return comment;
        }

        public List<CommentResponse> findAllByPostId(Long postId) {
            return commentRepository.findAllByPostId(postId);
        }
    public void deleteById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        likeRepository.deleteAll(comment.getLikes());

        commentRepository.deleteById(commentId);
    }

    public Long findPostIdByCommentId(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        return comment.getPost().getId();
    }

}

