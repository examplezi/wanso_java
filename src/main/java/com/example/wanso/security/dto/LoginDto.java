package com.example.wanso.security.dto;


import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String loginId;
    private String password;

}
