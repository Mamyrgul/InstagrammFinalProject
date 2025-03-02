package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "likes")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_gen")
    @SequenceGenerator(name = "like_gen", sequenceName = "like_seq")
    Long id;
    boolean isLiked;
    @ManyToOne
    User user;
    @ManyToOne
    Post post;
    @ManyToOne
    Comment comment;
}
