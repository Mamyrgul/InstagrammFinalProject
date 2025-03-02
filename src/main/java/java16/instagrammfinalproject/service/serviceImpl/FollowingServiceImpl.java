package java16.instagrammfinalproject.service.serviceImpl;

import jakarta.transaction.Transactional;
import java16.instagrammfinalproject.exceptions.NotFoundException;
import java16.instagrammfinalproject.models.Followers;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.FollowersRepo;
import java16.instagrammfinalproject.repo.UserRepo;
import java16.instagrammfinalproject.service.FollowersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowingServiceImpl implements FollowersService {

        private final FollowersRepo followersRepository;
        private final UserRepo userRepository;

        // Метод для подписки/отписки
        @Transactional
        public User subscribe(Long followerId, Long followingId) {
            User follower = userRepository.findById(followerId)
                    .orElseThrow(() -> new NotFoundException("User not found"));
            User following = userRepository.findById(followingId)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            Optional<Followers> existingSubscription = followersRepository.findSubscription(followerId, followingId);

            if (existingSubscription.isPresent()) {
                // Если подписка существует, удаляем её (отписка)
                followersRepository.delete(existingSubscription.get());
                follower.setFollowingCount(follower.getFollowingCount() - 1);
                following.setFollowersCount(following.getFollowersCount() - 1);
            } else {
                // Если подписки нет, создаём её (подписка)
                Followers newFollower = Followers.builder()
                        .follower(follower)
                        .following(following)
                        .build();
                followersRepository.save(newFollower);
                follower.setFollowingCount(follower.getFollowingCount() + 1);
                following.setFollowersCount(following.getFollowersCount() + 1);
            }

            return following;
        }

        // Получить всех подписчиков
        public List<User> getAllSubscribersByUserId(Long userId) {
            return followersRepository.getAllSubscribersByUserId(userId);
        }

        // Получить всех, на кого подписан пользователь
        public List<User> getAllSubscriptionsByUserId(Long userId) {
            return followersRepository.getAllSubscriptionsByUserId(userId);
        }
    }

