package java16.instagrammfinalproject.api;

import jakarta.annotation.security.PermitAll;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.service.FollowersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/followers")
@RequiredArgsConstructor
@PermitAll
public class FollowingApi {
        private final FollowersService followersService;

        @PostMapping("/subscribe/{followerId}/{followingId}")
        public ResponseEntity<String> subscribe(@PathVariable Long followerId, @PathVariable Long followingId) {
            User followingUser = followersService.subscribe(followerId, followingId);
            boolean isSubscribed = followersService.getAllSubscribersByUserId(followingId).stream()
                    .anyMatch(user -> user.getId().equals(followerId));
            String message = isSubscribed ? "Подписаться" : "Отменить подписку";
            return ResponseEntity.ok(message);
        }

        // Получить всех подписчиков
        @GetMapping("/subscribers/{userId}")
        public ResponseEntity<List<User>> getSubscribers(@PathVariable Long userId) {
            return ResponseEntity.ok(followersService.getAllSubscribersByUserId(userId));
        }

        // Получить всех, на кого подписан
        @GetMapping("/subscriptions/{userId}")
        public ResponseEntity<List<User>> getSubscriptions(@PathVariable Long userId) {
            return ResponseEntity.ok(followersService.getAllSubscriptionsByUserId(userId));
        }
    }
