package com.example.wanso.board.repository;

import com.example.wanso.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // 유저 ID로 게시물 조회
    List<Board> findByUserId(Long userId);
}
