package com.example.wanso.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken authRequest;

        final LoginDTO loginDTO;
        try {
            // 사용자 요청 정보로 UserPasswordAuthenticationToken 발급
            loginDTO = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);
            System.out.println("loginDTO : " + loginDTO);
            authRequest = new UsernamePasswordAuthenticationToken(loginDTO.getLoginid(), loginDTO.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Token 발급 실패");
        }
        setDetails(request, authRequest);

        // AuthenticationManager에게 전달 -> AuthenticationProvider의 인증 메서드 실행
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
