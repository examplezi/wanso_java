package com.example.wanso.service;

import com.example.wanso.domain.User;
import com.example.wanso.dto.CreateUserDto;
import com.example.wanso.dto.SuccessResponse;
import com.example.wanso.dto.UpdateUserDto;
import com.example.wanso.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //    /**
//     * @author Ryan
//     * @description 유저 생성 컨트롤러
//     *
//     * @path /user/create
//     *
//     * @return User Id
//     */
    public SuccessResponse onCreateUser(CreateUserDto createUserDto){
        int id = createUserDto.getId();
        String name = createUserDto.getName();

        int userId = this.userRepository.save(id, name);
        return new SuccessResponse(true, userId); // 성공
    }

    //    /**
//     * @author Ryan
//     * @description 전체 유저 조회
//     *
//     * @path /user/list
//     *
//     * @return User[]
//     */
    public SuccessResponse getUserList(){
        List<User> userList = new ArrayList<>(this.userRepository.findAllByUser());
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

    public SuccessResponse getUser(int id){
        User byUser = this.userRepository.findByUser(id);

        return new SuccessResponse(true, byUser);
    }

    //    /**
//     * @author Ryan
//     * @description 유저 정보 수정
//     *
//     * @path /update
//     *
//     * @return User Id
//     */
    public SuccessResponse updateUser(UpdateUserDto updateUserDto){
        int id = updateUserDto.getId();
        String name = updateUserDto.getName();

        User user = this.userRepository.update(id, name);

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

    public SuccessResponse deleteUser(int id){
        Boolean success = this.userRepository.delete(id);
        return new SuccessResponse(success, null);
    }
}
