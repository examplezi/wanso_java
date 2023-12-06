package com.example.wanso.board.service;

import com.example.wanso.board.dto.CreateBoardDto;
import com.example.wanso.board.entity.Board;
import com.example.wanso.board.repository.BoardRepository;
import com.example.wanso.user.entity.User;
import com.example.wanso.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 전체 게시글조회
    public Page<Board> findBoards(int page, String order) {
        PageRequest pageRequest = PageRequest.of(page - 1, 6, Sort.by(order).descending());
        return boardRepository.findAll(pageRequest);
    }


    // 유저 게시물 조회
    public List<Board> findByUser(Long userId) {
        return boardRepository.findByUserId(userId);
    }

    // 특정 게시물 조회
    public Optional<Board> findOne(Long id) {
        return boardRepository.findById(id);
    }

    // 게시물 생성
    @Transactional
    public Board create(String email, CreateBoardDto createBoardDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Board board = new Board();
        board.setUser(user);
        board.setContent(createBoardDto.getContent());
        // ... 나머지 게시판 데이터 설정 ...

        board = boardRepository.save(board);
        return board;
    }

//    @Transactional
//    public boolean delete(Long id) {
//
//        boardRepository.deleteById(id);
//
//        // 삭제 여부를 확인하기 위해, 삭제 후 해당 ID로 게시판을 조회해봅니다.
//        return boardRepository.findById(id).isEmpty();
//    }

    @Transactional
    public boolean delete(Long id) {
        boolean exists = boardRepository.existsById(id);
        if (exists) {
            boardRepository.deleteById(id);
        }
        return exists;
    }
}
