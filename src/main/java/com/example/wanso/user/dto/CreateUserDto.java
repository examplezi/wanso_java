package com.example.wanso.user.dto;

//@Data
//@NoArgsConstructor
//public class CreateUserDto {
//    public  String nickname;
//    public String password;
//    private String phone;
//    private User.UserInterestEnum interest;
//}


import com.example.wanso.user.entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotEmpty
    private String email;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String password;

    private String phone;

    @NotNull
    private User.UserInterestEnum interest;

    // Lombok을 사용하면, 별도로 getter, setter를 작성할 필요 없음
}
