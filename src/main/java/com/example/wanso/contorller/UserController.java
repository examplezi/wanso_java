package com.example.wanso.contorller;
//import com.ryan.spring_boot_rest_api.dto.CreatUserDto;
//import com.ryan.spring_boot_rest_api.dto.SuccessResponse;
//import com.ryan.spring_boot_rest_api.dto.UpdateUserDto;
//import com.ryan.spring_boot_rest_api.service.UserService;

import com.example.wanso.dto.SuccessResponse;
import com.example.wanso.dto.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * @author Ryan
     * @description 유저 생성 컨트롤러
     *
     * @path /user/create
     *
     * @return User Id
     */
    @PostMapping(value = "/create") // 유저생성
    public ResponseEntity<SuccessResponse> onCreateUser(@RequestBody @Valid CreatUserDto creatUserDto){

        SuccessResponse response = this.userService.onCreateUser(creatUserDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }


    /**
     * @author Ryan
     * @description 전체 유저 조회
     *
     * @path /user/list
     *
     * @return User[]
     */
    @GetMapping(value = "/list") // 전체 유저 조회
    public ResponseEntity<SuccessResponse> getUserList(){

        SuccessResponse response = this.userService.getUserList();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * @author Ryan
     * @description 단일 유저 조회 (Query Params 방식)
     *
     * @path /user/user?id=
     *
     * @return User
     */
    @GetMapping(value = "/user")
    public ResponseEntity<SuccessResponse> getUser(@RequestParam("id") int id){

        SuccessResponse response = this.userService.getUser(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * @author Ryan
     * @description 유저 정보 수정
     *
     * @path /user/update
     *
     * @return User Id
     */
    @PutMapping(value = "/update")
    public ResponseEntity<SuccessResponse> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto){

        SuccessResponse response = this.userService.updateUser(updateUserDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * @author Ryan
     * @description 특정 유저 삭제 (Path Variables 방식)
     *
     * @path /user/delete/{id}
     *
     * @return Boolean
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable("id") int id){

        SuccessResponse response = this.userService.deleteUser(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
