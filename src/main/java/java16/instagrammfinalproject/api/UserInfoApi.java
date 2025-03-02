package java16.instagrammfinalproject.api;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import java16.instagrammfinalproject.dto.request.UserInfoRequest;
import java16.instagrammfinalproject.dto.response.SimpleResponse;
import java16.instagrammfinalproject.dto.response.UserInfoResponse;
import java16.instagrammfinalproject.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userInfos")
public class UserInfoApi {
    private final UserInfoService userInfoService;
    @PermitAll
    @PostMapping
    public SimpleResponse saveUserInfo(@Valid @RequestBody UserInfoRequest userInfoRequest) {
        return userInfoService.saveUserInfo(userInfoRequest);
    }
  @PutMapping("/{id}")
    public SimpleResponse updateUserInfo(@Valid @PathVariable Long id,@RequestBody UserInfoRequest userInfoRequest) {
        return userInfoService.updateUserInfo(id,userInfoRequest);
  }
    @PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteUserInfo(@PathVariable Long id) {
        return userInfoService.deleteUserInfo(id);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserInfoResponse> getAllUserInfos() {
        return userInfoService.findAllAdminsInfo();
    }
    @PermitAll
  @GetMapping("/{keyWord}")
    List<UserInfoResponse> getUserInfosByKeyWord(@PathVariable String keyWord) {
        return userInfoService.searchByFirstNameLastName(keyWord);
  }

}
