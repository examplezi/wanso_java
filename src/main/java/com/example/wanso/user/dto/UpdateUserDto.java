package com.example.wanso.user.dto;

import com.example.wanso.user.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class UpdateUserDto {
    //public int id;
    public  String email;
    public  String nickname;
    public  String phone;
    //public  String interest;
    public User.UserInterestEnum interest; // Enum 타입으로 변경
}

