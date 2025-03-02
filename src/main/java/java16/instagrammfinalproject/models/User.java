package java16.instagrammfinalproject.models;

import jakarta.persistence.*;
import java16.instagrammfinalproject.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq")
    Long id;
    String firstName;
    String lastName;
    @Column(unique = true)
    String email;
    String password;
    String phoneNumber;
    @Enumerated(EnumType.STRING)
    Role role;

    int followersCount; // Количество подписчиков
    int followingCount; // Количество подписок

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String firstName) {
        this.firstName = firstName;
    }

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    List<Post> posts;

    @OneToMany(mappedBy = "follower", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    List<Followers> following; // Кого я подписал

    @OneToMany(mappedBy = "following", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    List<Followers> followers; // Кто подписался на меня

    // Метод для добавления подписчика
    public void addFollower(Followers follower) {
        if (!followers.contains(follower)) {
            followers.add(follower);
            followersCount++; // Увеличиваем количество подписчиков
        }
    }

    // Метод для удаления подписчика
    public void removeFollower(Followers follower) {
        if (followers.contains(follower)) {
            followers.remove(follower);
            followersCount--; // Уменьшаем количество подписчиков
        }
    }

    // Метод для подписки на другого пользователя
    public void followUser(Followers followingUser) {
        if (!following.contains(followingUser)) {
            following.add(followingUser);
            followingCount++; // Увеличиваем количество подписок
        }
    }

    // Метод для отписки
    public void unfollowUser(Followers followingUser) {
        if (following.contains(followingUser)) {
            following.remove(followingUser);
            followingCount--; // Уменьшаем количество подписок
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
