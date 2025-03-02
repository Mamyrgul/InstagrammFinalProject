package java16.instagrammfinalproject.service;

import java16.instagrammfinalproject.models.User;
import org.springframework.data.repository.query.Param;

public interface UserService {
    User findByFirstName(@Param("firstName") String firstName);
}
