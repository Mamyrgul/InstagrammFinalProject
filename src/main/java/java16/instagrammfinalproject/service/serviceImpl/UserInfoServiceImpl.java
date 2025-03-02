package java16.instagrammfinalproject.service.serviceImpl;

import jakarta.transaction.Transactional;
import java16.instagrammfinalproject.dto.request.UserInfoRequest;
import java16.instagrammfinalproject.dto.response.SimpleResponse;
import java16.instagrammfinalproject.dto.response.UserInfoResponse;
import java16.instagrammfinalproject.enums.Role;
import java16.instagrammfinalproject.exceptions.BadRequestException;
import java16.instagrammfinalproject.exceptions.NotFoundException;
import java16.instagrammfinalproject.models.User;
import java16.instagrammfinalproject.models.UserInfo;
import java16.instagrammfinalproject.repo.UserInfoRepo;
import java16.instagrammfinalproject.repo.UserRepo;
import java16.instagrammfinalproject.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepo userInfoRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Override
    public SimpleResponse saveUserInfo(UserInfoRequest userInfoRequest) {
        User user = User
                .builder()
                .firstName(userInfoRequest.firstName())
                .lastName(userInfoRequest.lastName())
                .email(userInfoRequest.email())
                .password(passwordEncoder.encode(userInfoRequest.password()))
                .phoneNumber(String.valueOf(userInfoRequest.phoneNumber()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setBiography(userInfoRequest.biography());
        userInfo.setGender(userInfoRequest.gender());
        userInfo.setImageUrl(userInfoRequest.imageUrl());
        userInfo.setUser(user);
        userInfoRepo.save(userInfo);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.ACCEPTED)
                .message("Successfully saved user info")
                .build();
    }
    @Override
    public SimpleResponse updateUserInfo(Long id, UserInfoRequest userInfoRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email %s not found!", email)));
        User updateUser = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with ID %d not found!", id)));
        // Если пользователь пытается обновить чужой аккаунт — ошибка!
        if (!currentUser.getEmail().equals(updateUser.getEmail())) {
            throw new BadRequestException("Вы не можете обновлять чужие данные!");
        }
        // Если email изменился и уже существует у другого пользователя — ошибка!
        if (!updateUser.getEmail().equals(userInfoRequest.email()) && userRepo.existsByEmail(userInfoRequest.email())) {
            throw new BadRequestException("Этот email уже используется!");
        }
        updateUser.setEmail(userInfoRequest.email());
        updateUser.setFirstName(userInfoRequest.firstName());
        updateUser.setLastName(userInfoRequest.lastName());
        updateUser.setPassword(passwordEncoder.encode(userInfoRequest.password()));
        updateUser.setPhoneNumber(userInfoRequest.phoneNumber());
        userRepo.save(updateUser);
        // Найдем userInfo, связанный с пользователем
        UserInfo userInfo = (UserInfo) userInfoRepo.findByUser(updateUser)
                .orElseThrow(() -> new NotFoundException("UserInfo not found for user with ID " + id));
        // Обновляем только непустые поля (чтобы не перезаписывать null-ами)
        if (userInfoRequest.biography() != null) {
            userInfo.setBiography(userInfoRequest.biography());
        }
        if (userInfoRequest.gender() != null) {
            userInfo.setGender(userInfoRequest.gender());
        }
        if (userInfoRequest.imageUrl() != null) {
            userInfo.setImageUrl(userInfoRequest.imageUrl());
        }
        // Сохраняем обновленный UserInfo
        userInfoRepo.save(userInfo);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User info successfully updated!")
                .build();
    }
    @Transactional
    @Override
    public SimpleResponse deleteUserInfo(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepo.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("Current user not found!"));

        UserInfo userInfo = userInfoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User info with ID %d not found!", id)));

        if (!userInfo.getUser().getId().equals(currentUser.getId()) && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new BadRequestException("You cannot delete someone else's information!");
        }

        userInfoRepo.delete(userInfo); // Удаляем сам объект, а не по ID

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User info successfully deleted!")
                .build();
    }


    @Override
    public List<UserInfoResponse> findAllAdminsInfo() {
    return userInfoRepo.findAllAdminsInfo();
    }

    @Override
    public List<UserInfoResponse> searchByFirstNameLastName(String keyWord) {
        return userInfoRepo.searchByFirstNameLastName(keyWord);
    }

}
