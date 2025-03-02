package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId); // Найти все комментарии по postId
    Optional<Comment> findById(Long id);  // This method is already provided by JpaRepository


}
