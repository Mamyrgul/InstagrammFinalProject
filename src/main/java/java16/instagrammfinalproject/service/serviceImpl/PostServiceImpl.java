package java16.instagrammfinalproject.service.serviceImpl;

import java16.instagrammfinalproject.dto.request.PostRequest;
import java16.instagrammfinalproject.dto.request.PostUpdateRequest;
import java16.instagrammfinalproject.dto.response.PostResponse;
import java16.instagrammfinalproject.models.Image;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.repo.PostRepo;
import java16.instagrammfinalproject.repo.UserRepo;
import java16.instagrammfinalproject.service.MentionService;
import java16.instagrammfinalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepository;
    private final UserRepo userRepository;
    private final MentionService mentionService; // Убираем null, используем Autowired


    public void createPost(PostRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getImages().isEmpty()) {
            throw new RuntimeException("Пост должен содержать хотя бы одно изображение!");
        }

        Post post = Post.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .likeCount(0)
                .createdAt(LocalDate.now())
                .user(user)
                .images(request.getImages().stream()
                        .map(imgUrl -> new Image(null, imgUrl, null)) // Создаем изображения
                        .collect(Collectors.toList()))
                .build();

        post.getImages().forEach(image -> image.setPost(post)); // Устанавливаем связь изображений с постом

        postRepository.save(post);
    }

    public void updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Пост не найден"));

        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());

        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<PostResponse> getUserFeed(Long userId) {
        List<Post> posts = postRepository.findAllUserFeed(userId);
        return posts.stream()
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getDescription(),
                        post.getLikeCount(),
                        post.getCreatedAt(),
                        post.getUser().getFirstName() + " " + post.getUser().getLastName()
                ))
                .collect(Collectors.toList());
    }



    public String addMentionToPost(Long postId, String firstName) {
        try {
            // Находим пост по его id
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post with id " + postId + " not found"));

            // Находим пользователя по firstName
            User userToMention = userRepository.findByFirstName(firstName)
                    .orElseThrow(() -> new RuntimeException("User with firstName " + firstName + " not found"));

            // Добавляем упоминание в пост
            mentionService.addMentionToPost(post, Optional.ofNullable(userToMention));

            return "User mentioned in post successfully!";
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}


