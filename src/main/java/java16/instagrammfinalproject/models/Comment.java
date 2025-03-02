package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "com_gen")
    @SequenceGenerator(name = "com_gen", sequenceName = "com_seq")
    Long id;
    String comment;
    int likeCount;
    LocalDate createdAt;
    LocalDate updatedAt;
    @OneToMany
    List<Like> likes;
    @ManyToOne
    User user;
    @ManyToOne
    Post post;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    List<Mention> mentions;
    public void addMention(User user) {
        if (mentions == null) {
            mentions = new ArrayList<>();
        }
        Mention mention = Mention.builder()
                .comment(this)
                .user(user)
                .build();
        mentions.add(mention);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
    // Метод для добавления лайка
    public void addLike(Like like) {
        if (!likes.contains(like)) {
            likes.add(like);
            likeCount++;  // Увеличиваем количество лайков
        }
    }

    // Метод для удаления лайка
    public void removeLike(Like like) {
        if (likes.contains(like)) {
            likes.remove(like);
            likeCount--;  // Уменьшаем количество лайков
        }
    }
}
