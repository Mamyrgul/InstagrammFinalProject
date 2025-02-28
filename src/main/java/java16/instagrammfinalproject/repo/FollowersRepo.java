package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.models.Followers;
import java16.instagrammfinalproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowersRepo extends JpaRepository<Followers, Long> {
    List<Followers> findAllByFollowing(User following); // Получить подписчиков
    List<Followers> findAllByFollower(User follower);  // Получить подписки
}

