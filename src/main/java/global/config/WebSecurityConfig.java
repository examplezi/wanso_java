package global.config;

//@Configuration
//@EnableWebSecurity(debug = true) // todo debug 운영시 지우기
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SpringSecurityConfig { // 폼 기반 인증
//
//    private final ObjectMapper objectMapper;
//    private final UserRepository userRepository;
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//                .requestMatchers("/favicon.ico")
//                .requestMatchers("/error")
//                .requestMatchers(toH2Console());
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests()
//                .anyRequest().permitAll()
//                .and()
//                .addFilterBefore(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(e -> {
//                    e.accessDeniedHandler(new Http403Handler(objectMapper));
//                    e.authenticationEntryPoint(new Http401Handler(objectMapper));
//                })
//                .csrf(AbstractHttpConfigurer::disable)
//                .build();
//    }
//
//    @Bean
//    public EmailPasswordAuthFilter usernamePasswordAuthenticationFilter() {
//        EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter("/auth/login", objectMapper);
//        filter.setAuthenticationManager(authenticationManager());
//        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(objectMapper));
//        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
//        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
//
//        return filter;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService(userRepository));
//        provider.setPasswordEncoder(passwordEncoder());
//
//        return new ProviderManager(provider);
//    }
//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository) {
//        return username -> {
//            User user = userRepository.findByEmail(username)
//                    .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
//
//            return new UserPrincipal(user);
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new SCryptPasswordEncoder(
//                16,
//                8,
//                1,
//                32,
//                64
//        );
//    }
//}

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // JWT 제공 클래스
    private final JwtProvider jwtProvider;
    // 인증 실패 또는 인증헤더가 전달받지 못했을때 핸들러
    private final AuthenticationEntryPoint authenticationEntryPoint;
    // 인증 성공 핸들러
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    // 인증 실패 핸들러
    private final AuthenticationFailureHandler authenticationFailureHandler;
    // 인가 실패 핸들러
    private final AccessDeniedHandler accessDeniedHandler;

    /**
     * Security 적용 무시
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .mvcMatchers("/docs/**");
    }

    /**
     * 보안 기능 초기화 및 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final String[] GET_WHITELIST = new String[]{
                "/login" // 로그인
        };

        final String[] POST_WHITELIST = new String[]{
                "/user" // 회원가입
        };

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint) // 인증 실패
                .accessDeniedHandler(accessDeniedHandler) // 인가 실패
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, GET_WHITELIST).permitAll() // 해당 GET URL은 모두 허용
                .antMatchers(HttpMethod.POST, POST_WHITELIST).permitAll() // 해당 POST URL은 모두 허용
                .antMatchers("**").hasAnyRole("USER") // 권한 적용
                .anyRequest().authenticated() // 나머지 요청에 대해서는 인증을 요구
                .and() // 로그인하는 경우에 대해 설정함
                .formLogin().disable() // 로그인 페이지 사용 안함
//                .loginPage("/user/loginView") // 로그인 성공 URL을 설정함
//                .successForwardUrl("/index") // 로그인 실패 URL을 설정함
//                .failureForwardUrl("/index").permitAll()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 비밀번호 암호화 및 확인 클래스
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 사용자 요청 정보로 UserPasswordAuthenticationToken 발급하는 필터
     */
    @Bean
    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        // 필터 URL 설정
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        // 인증 성공 핸들러
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        // 인증 실패 핸들러
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        // BeanFactory에 의해 모든 property가 설정되고 난 뒤 실행
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    /**
     * JWT의 인증 및 권한을 확인하는 필터
     */
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider);
    }

}