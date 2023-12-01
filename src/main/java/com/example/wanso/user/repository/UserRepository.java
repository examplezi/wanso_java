package com.example.wanso.user.repository;

import com.example.wanso.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//@Repository
//public class UserRepository {
//기본 CRUD(Create, Read, Update, Delete) 작업을 위한 메소드를 포함
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByUsername(String Username);
}
}
    //User save(User user);

//    save(S entity): 엔티티 저장 및 업데이트
//    findById(ID id): ID를 통한 엔티티 조회
//    findAll(): 모든 엔티티 조회
//    deleteById(ID id): ID를 통한 엔티티 삭제
    //Optional<User> findByUserId(Long userId);
   // Optional<User> findByUsername(String Username);

    //Page<User> findAll(Pageable pageable);

    //Boolean existsByLoginId(String loginId);

    //Boolean existsByNickname(String nickname);

    //Long countAllByUserRole(User.UserInterestEnum interest);
//}
   // Map<Integer, User> memoryDB = new ConcurrentHashMap<>();
    //    /**
//     * @author Ryan
//     * @description 유저 등록
//     *
//     * @param id 유저 아이디
//     * @param name 유저 이름
//     *
////     * @return id 유저 아이디
////     */
//    public int save(int id, String name){
//        User user = new User();
//        user.setId(id);
//        user.setName(name);
//        this.memoryDB.put(id, user); // 저장, 갱신
//        return id;
//    }
////    /**
////     * @author Ryan
////     * @description 단일 유저 조회
////     *
////     * @return User 유저
////     */
//    public User findByUser(int id){
//        return this.memoryDB.get(id); // 조회
//    }
//    //    /**
////     * @author Ryan
////     * @description 유저 정보 전체 조회
////     *
////     * @return User[] 유저 리스트
////     */
//    public Collection<User> findAllByUser(){
//        return this.memoryDB.values(); // 모든 값 조회
//    }
//    //    /**
////     * @author Ryan
////     * @description 유저 수정
////     *
////     * @return User 유저
////     */
//    public User update(int id, String name){
//        User user = this.memoryDB.get(id);
//        user.setName(name);
//
//        return user;
//
//    }
//
//    //    /**
////     * @author Ryan
////     * @description 유저 삭제
////     *
////     * @param id 유저 고유 아이디
////     *
////     * @return Boolean 성공 여부
////     */
//    public Boolean delete(int id){
//        User user = this.memoryDB.remove(id); // 제거
//        // 삭제하려는 유저가 존재하지 않다면
//        if(user == null){
//            return false;
//        }
//        return true;
//    }

//}


///--------

//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository userRepository;
//
//    public SuccessResponse onCreateUser(CreateUserDto createUserDto){
//        User newUser = new User();
//        newUser.setId(createUserDto.getId());
////        newUser.setName(createUserDto.getName());
//
//        User savedUser = userRepository.save(newUser);
//        return new SuccessResponse(true, savedUser.getId());
//    }
//
//    public SuccessResponse getUserList(){
//        List<User> userList = userRepository.findAll();
//        return new SuccessResponse(true, userList);
//    }
//
//    public SuccessResponse getUser(Long id){
//        Optional<User> user = userRepository.findById(id);
//        return user.map(u -> new SuccessResponse(true, u))
//                .orElse(new SuccessResponse(false, "User not found"));
//    }
//
//    public SuccessResponse updateUser(UpdateUserDto updateUserDto){
//        Optional<User> existingUser = userRepository.findById(updateUserDto.getId());
//        if (existingUser.isPresent()) {
//            User user = existingUser.get();
//            user.setName(updateUserDto.getName());
//            userRepository.save(user);
//            return new SuccessResponse(true, user);
//        } else {
//            return new SuccessResponse(false, "User not found");
//        }
//    }
//
//    public SuccessResponse deleteUser(Long id){
//        if (userRepository.existsById(id)) {
//            userRepository.deleteById(id);
//            return new SuccessResponse(true, "User deleted");
//        } else {
//            return new SuccessResponse(false, "User not found");
//        }
//    }
//}
