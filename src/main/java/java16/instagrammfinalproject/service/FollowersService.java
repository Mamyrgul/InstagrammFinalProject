package java16.instagrammfinalproject.service;

import java16.instagrammfinalproject.models.User;

import java.util.List;

public interface FollowersService {
    User subscribe(Long followerId, Long followingId);
    List<User> getAllSubscribersByUserId(Long userId);
    List<User> getAllSubscriptionsByUserId(Long userId);
}
