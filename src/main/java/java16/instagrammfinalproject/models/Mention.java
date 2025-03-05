package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mentions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mention {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mention_gen")
    @SequenceGenerator(name = "mention_gen", sequenceName = "mention_seq")
    Long id;
    @ManyToOne
    Post post;
    @ManyToOne
    Comment comment;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
