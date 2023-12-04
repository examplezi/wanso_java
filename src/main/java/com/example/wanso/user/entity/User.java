package com.example.wanso.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
//export enum USER_INTEREST_ENUM {
//    romance = 'romance',
//    drama = 'drama',
//    fantasy = 'fantasy',
//    action = 'action',
//    school = 'school',
//    horror = 'horror',
//}

@Getter
//@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User {

//    public User orElseThrow(Object o) {
//    }

    public enum UserInterestEnum {
        ROMANCE,
        DRAMA,
        FANTASY,
        ACTION,
        SCHOOL,
        HORROR;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(nullable = false, length = 10)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 10)
    private String password;

    @Column(nullable = false, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ROMANCE', 'DRAMA', 'FANTASY', 'ACTION', 'SCHOOL', 'HORROR')")
    private UserInterestEnum interest;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime deletedAt;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private List<Board> board = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private List<Review> review = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private List<ReviewLike> reviewLike = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
//    private List<Wishlist> wishlist = new ArrayList<>();

    // 생성자에 @Builder 적용
    // 주로 유저(또는 클라이언트)가 입력할 수 있는 부분
    @Builder
    public User(String nickname, String email, String password, String phone
    , UserInterestEnum interest ) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.interest = interest;
    }


}
