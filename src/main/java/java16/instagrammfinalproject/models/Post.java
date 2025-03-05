package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "posts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
    @SequenceGenerator(name = "post_gen", sequenceName = "post_seq")
    Long id;
    String title;
    String description;
    int likeCount;
    LocalDate createdAt;
    @ManyToOne
    User user;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE})
    List<Comment> comments;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE})
    List<Like> likes;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.PERSIST})
    List<Image> images;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Mention> mentions;

    public void addMention(Optional<User> user) {
        if (mentions == null) {
            mentions = new ArrayList<>();
        }
        User mentionUser = user.orElseThrow(() -> new RuntimeException("User not found"));

        Mention mention = Mention.builder()
                .post(this)
                .user(mentionUser)
                .build();

        mentions.add(mention);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }

    public void addLike(Like like) {
        if (!likes.contains(like)) {
            likes.add(like);
            likeCount++;
        }
    }
    public void removeLike(Like like) {
        if (likes.contains(like)) {
            likes.remove(like);
            likeCount--;
        }
    }
}
