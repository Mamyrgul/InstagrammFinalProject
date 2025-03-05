package java16.instagrammfinalproject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<Like> likes = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    User user;

    @ManyToOne
    @JsonIgnore
    Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.PERSIST)
    List<Mention> mentions = new ArrayList<>();

    public void addMention(User user) {
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
