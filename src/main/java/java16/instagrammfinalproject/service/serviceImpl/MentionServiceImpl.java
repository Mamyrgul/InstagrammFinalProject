package java16.instagrammfinalproject.service.serviceImpl;

import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Mention;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.MentionRepo;
import java16.instagrammfinalproject.service.MentionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {

    private final MentionRepo mentionRepository;

    @Override
    public void addMentionToPost(Post post, Optional<User> user) {
        User mentionUser = user.orElseThrow(() -> new RuntimeException("User not found"));
        post.addMention(Optional.of(mentionUser));
        mentionRepository.save(Mention.builder().post(post).user(mentionUser).build());
    }

    @Override
    public void addMentionToComment(Comment comment, User user) {
        comment.addMention(user);
        mentionRepository.save(Mention.builder().comment(comment).user(user).build());
    }
}


