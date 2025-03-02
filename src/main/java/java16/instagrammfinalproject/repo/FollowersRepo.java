package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.models.Followers;
import java16.instagrammfinalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowersRepo extends JpaRepository<Followers, Long> {

        // Проверяем, есть ли подписка между пользователями
        @Query("""
        SELECT f FROM Followers f 
        WHERE f.follower.id = :followerId 
          AND f.following.id = :followingId
    """)
        Optional<Followers> findSubscription(@Param("followerId") Long followerId,
                                             @Param("followingId") Long followingId);

        // Найти всех подписчиков пользователя
        @Query("""
        SELECT f.follower FROM Followers f WHERE f.following.id = :userId
    """)
        List<User> getAllSubscribersByUserId(@Param("userId") Long userId);

        // Найти всех, на кого подписан пользователь
        @Query("""
        SELECT f.following FROM Followers f WHERE f.follower.id = :userId
    """)
        List<User> getAllSubscriptionsByUserId(@Param("userId") Long userId);
    }



