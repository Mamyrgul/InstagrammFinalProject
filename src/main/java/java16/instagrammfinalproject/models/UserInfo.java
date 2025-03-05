package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import java16.instagrammfinalproject.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "userInfos")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "useIn_gen")
    @SequenceGenerator(name = "useIn_gen", sequenceName = "useIn_seq")
    Long id;
    String biography;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String imageUrl;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    User user;
}
