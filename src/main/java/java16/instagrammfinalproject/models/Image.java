package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "images")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "img_gen")
    @SequenceGenerator(name = "img_gen", sequenceName = "img_seq")
    Long id;
    String imageUrl;
    @ManyToOne
    Post post;
}
