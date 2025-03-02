package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Like;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {
        Like findByUserAndPost(User user, Post post);
        Like findByUserAndComment(User user, Comment comment);
    }

