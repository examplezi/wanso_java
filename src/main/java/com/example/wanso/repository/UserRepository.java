package com.example.wanso.repository;

import com.example.wanso.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class UserRepository {
    Map<Integer, User> memoryDB = new ConcurrentHashMap<>();
    //    /**
//     * @author Ryan
//     * @description 유저 등록
//     *
//     * @param id 유저 아이디
//     * @param name 유저 이름
//     *
//     * @return id 유저 아이디
//     */
    public int save(int id, String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        this.memoryDB.put(id, user); // 저장, 갱신
        return id;
    }
//    /**
//     * @author Ryan
//     * @description 단일 유저 조회
//     *
//     * @return User 유저
//     */
    public User findByUser(int id){
        return this.memoryDB.get(id); // 조회
    }
    //    /**
//     * @author Ryan
//     * @description 유저 정보 전체 조회
//     *
//     * @return User[] 유저 리스트
//     */
    public Collection<User> findAllByUser(){
        return this.memoryDB.values(); // 모든 값 조회
    }
    //    /**
//     * @author Ryan
//     * @description 유저 수정
//     *
//     * @return User 유저
//     */
    public User update(int id, String name){
        User user = this.memoryDB.get(id);
        user.setName(name);

        return user;

    }

    //    /**
//     * @author Ryan
//     * @description 유저 삭제
//     *
//     * @param id 유저 고유 아이디
//     *
//     * @return Boolean 성공 여부
//     */
    public Boolean delete(int id){
        User user = this.memoryDB.remove(id); // 제거
        // 삭제하려는 유저가 존재하지 않다면
        if(user == null){
            return false;
        }
        return true;
    }

}
