package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Mention;
import java16.instagrammfinalproject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MentionRepo extends JpaRepository<Mention, Long> {
    List<Mention> findByPost(Post post);
    List<Mention> findByComment(Comment comment);
}


