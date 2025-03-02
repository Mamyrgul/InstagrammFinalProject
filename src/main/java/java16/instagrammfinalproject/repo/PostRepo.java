package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    @Query("""
        SELECT p FROM Post p
        WHERE p.user.id = :userId 
        OR p.user.id IN (SELECT f.following.id FROM Followers f WHERE f.follower.id = :userId)
        ORDER BY p.createdAt DESC
    """)
    List<Post> findAllUserFeed(@Param("userId") Long userId);
    }

