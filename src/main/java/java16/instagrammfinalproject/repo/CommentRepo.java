package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.dto.response.CommentResponse;
import java16.instagrammfinalproject.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    @Query("""
    select new java16.instagrammfinalproject.dto.response.CommentResponse(c.id,c.comment, c.likeCount, c.createdAt )
    from Comment c
    where c.post.id = :postId
""")
    List<CommentResponse> findAllByPostId(@Param("postId") Long postId);

    Optional<Comment> findById(Long id);


}
