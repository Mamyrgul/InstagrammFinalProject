package java16.instagrammfinalproject.service;

import java16.instagrammfinalproject.dto.request.UserInfoRequest;
import java16.instagrammfinalproject.dto.response.SimpleResponse;
import java16.instagrammfinalproject.dto.response.UserInfoResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInfoService  {
    SimpleResponse saveUserInfo(UserInfoRequest userInfoRequest);
    SimpleResponse updateUserInfo(Long id,UserInfoRequest userInfoRequest);
    SimpleResponse deleteUserInfo(Long id);
    List<UserInfoResponse> findAllAdminsInfo();
    List<UserInfoResponse> searchByFirstNameLastName(@Param("keyWord") String keyWord);
}
