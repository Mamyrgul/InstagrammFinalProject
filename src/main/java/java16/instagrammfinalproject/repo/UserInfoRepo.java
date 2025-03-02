package java16.instagrammfinalproject.repo;

import java16.instagrammfinalproject.dto.response.UserInfoResponse;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    Optional<Object> findByUser(User updateUser);
    @Query("""
        select new java16.instagrammfinalproject.dto.response.UserInfoResponse(u.id, u.user.firstName, u.user.lastName, u.user.email)
        from UserInfo u
    """)
    List<UserInfoResponse> findAllAdminsInfo();
    @Query("""
    select new java16.instagrammfinalproject.dto.response.UserInfoResponse(
        u.id, u.user.firstName, u.user.lastName, u.user.email
    ) 
    from UserInfo u
    where lower(u.user.firstName) like lower(concat('%', :keyWord, '%')) 
       or lower(u.user.lastName) like lower(concat('%', :keyWord, '%'))
""")
    List<UserInfoResponse> searchByFirstNameLastName(@Param("keyWord") String keyWord);
}
