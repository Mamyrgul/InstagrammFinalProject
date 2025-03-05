package java16.instagrammfinalproject.config;

import jakarta.persistence.EntityNotFoundException;
import java16.instagrammfinalproject.models.Comment;
import java16.instagrammfinalproject.models.Post;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.models.UserInfo;
import java16.instagrammfinalproject.repo.CommentRepo;
import java16.instagrammfinalproject.repo.PostRepo;
import java16.instagrammfinalproject.repo.UserInfoRepo;
import java16.instagrammfinalproject.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class  SecurityService {
    private final UserRepo userRepo;
    private final UserInfoRepo userInfoRepo;
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;
    public boolean isOwner(Long userInfoId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return false;
        }

        String email = authentication.getName();
        return userRepo.findUserByEmail(email)
                .flatMap(user -> userInfoRepo.findById(userInfoId)
                        .map(userInfo -> userInfo.getUser().getId().equals(user.getId())))
                .orElse(false);
    }
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Пользователь не аутентифицирован");
            return false;
        }
        Object principal = authentication.getPrincipal();
        System.out.println("Principal: " + principal);
        System.out.println("Principal class: " + principal.getClass().getName());
        String email;
        if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        } else if (principal instanceof String) {
            email = (String) principal;
        } else {
            System.out.println("Неизвестный тип principal");
            return false;
        }
        System.out.println(" Получен email: " + email);
        User user = userRepo.findUserByEmail(email).orElse(null);
        if (user == null) {
            System.out.println("Пользователь с email " + email + " не найден");
            return false;
        }
        System.out.println("Сравнение: " + user.getId() + " == " + userId);
        return user.getId().equals(userId);
    }

    public boolean isOwnerr(Long postId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Пост не найден"));

        return post.getUser().getId().equals(currentUser.getId());
    }

    public boolean isPostOwner(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }

        String email = authentication.getName();
        User currentUser = userRepo.findUserByEmail(email).orElse(null);
        if (currentUser == null) {
            return false;
        }

        Post post = postRepo.findById(postId).orElse(null);
        return post != null && post.getUser().getId().equals(currentUser.getId());
    }

    public boolean isCommentOwner(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }

        String email = authentication.getName();
        User currentUser = userRepo.findUserByEmail(email).orElse(null);
        if (currentUser == null) {
            return false;
        }

        Comment comment = commentRepo.findById(commentId).orElse(null);
        return comment != null && comment.getUser().getId().equals(currentUser.getId());
    }

}
