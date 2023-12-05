package com.example.wanso.user.service;

import com.example.wanso.user.dto.CreateUserDto;
import com.example.wanso.user.dto.SuccessResponse;
import com.example.wanso.user.dto.UpdateUserDto;
import com.example.wanso.user.entity.User;
import com.example.wanso.user.exception.UserException;
import com.example.wanso.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService { // 스프링 시큐리티
    private final UserRepository userRepository;
    //private final CacheManager cacheManager; // 캐시 매니저 주입
    //private final PasswordEncoder passwordEncoder; // 스프링 시큐리티의 PasswordEncoder


    //    /**
//     * @author Ryan
//     * @description 유저 생성 컨트롤러
//     *
//     * @path /user/create
//     *
//     * @return User Id
//     */


    @Transactional
    public SuccessResponse onCreateUser(CreateUserDto createUserDto) {
        String email = createUserDto.getEmail();
        String password = createUserDto.getPassword(); // 가정: CreateUserDto에 hashedPassword 필드가 password로 명명됨
        String nickname = createUserDto.getNickname();
        String phone = createUserDto.getPhone();
        User.UserInterestEnum interest = createUserDto.getInterest();

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            // 이미 존재하는 이메일인 경우, 예외 발생
            throw new UserException.EmailAlreadyExistsException("User with email " + email + " already exists.");
        }
        User newUser = new User();

        newUser.setEmail(email);
        newUser.setPassword(password); // 비밀번호 암호화 로직 적용 가능
        newUser.setNickname(nickname);
        newUser.setPhone(phone);
        newUser.setInterest(interest);

        User savedUser = userRepository.save(newUser);
        return new SuccessResponse(true, savedUser.getUserId());
    }

    //    /**
//     * @author Ryan
//     * @description 전체 유저 조회
//     *
//     * @path /user/list
//     *
//     * @return User[]
//     */
    public SuccessResponse getUserList() { // 모든 유저 조회
        List<User> userList = userRepository.findAll();
        return new SuccessResponse(true, userList);
    }
//    /**
//     * @author Ryan
//     * @description 단일 유저 조회
//     *
//     * @path /user/:id
//     *
//     * @return User
//     */

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException.UserNotFoundException("User with email " + email + " not found."));
    }

    //    /**
//     * @author Ryan
//     * @description 유저 정보 수정
//     *
//     * @path /update
//     *
//     * @return User Id
//     */
//    public SuccessResponse updateUser(UpdateUserDto updateUserDto){
//        //int id = updateUserDto.getId();
//        String name = updateUserDto.getName();
//
//        User user = this.userRepository.update(id, name);
//
//        return new SuccessResponse(true, user);
//    }

    @Transactional
    public SuccessResponse updateUser(int id, UpdateUserDto updateUserDto) {
        // 기존 유저 정보 조회
        User user = userRepository.findByUserId((long) id).orElseThrow(() -> new RuntimeException("User not found"));

        // UpdateUserDto에서 정보를 가져와서 유저 정보 업데이트
        if (updateUserDto.getEmail() != null) {
            user.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getNickname() != null) {
            user.setNickname(updateUserDto.getNickname());
        }
        if (updateUserDto.getPhone() != null) {
            user.setPhone(updateUserDto.getPhone());
        }
        if (updateUserDto.getInterest() != null) {
            user.setInterest(updateUserDto.getInterest());
        }

        userRepository.save(user);

        return new SuccessResponse(true, user);
    }


    //    /**
//     * @author Ryan
//     * @description 특정 유저 삭제
//     *
//     * @path /delete
//     *
//     * @return Boolean
//     */

    public SuccessResponse deleteUser(Long id){
        Optional<User> user = userRepository.findByUserId(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return new SuccessResponse(true, null);
        } else {
            return new SuccessResponse(false, "User not found");
        }
    }

}
