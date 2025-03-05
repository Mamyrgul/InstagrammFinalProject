package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "followers")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fol_gen")
    @SequenceGenerator(name = "fol_gen", sequenceName = "fol_seq")
    Long id;
    @ManyToOne
    User follower;
    @ManyToOne
    User following;
}
