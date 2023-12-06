package com.example.wanso.board.controller;

import com.example.wanso.board.dto.CreateBoardDto;
import com.example.wanso.board.entity.Board;
import com.example.wanso.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    //게시물 생성
    @PostMapping("/board")
   // @PreAuthorize("isAuthenticated()")
    public Board createBoard(Authentication authentication, @RequestBody CreateBoardDto createBoardDto) {
        String email = authentication.getName();
        return boardService.create(email, createBoardDto);
    }

    //전체 게시물 조회
    @GetMapping("/boards")
    public List<Board> fetchBoards(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "DESC") String order
    ) {
        return boardService.find(page, order);
    }

    // 유저 개인 게시물 조회
    @GetMapping("/user/boards")
    //@PreAuthorize("isAuthenticated()") 스프링 시큐리티
    public List<Board> fetchBoardsByUser(Authentication authentication) {
        String email = authentication.getName();
        return boardService.userFind(email);
    }

    @GetMapping("/board/{id}")
    public Board fetchBoard(@PathVariable Long id) {
        return boardService.findOne(id).orElseThrow(() -> new RuntimeException("Board not found"));
    }

    // 게시물 삭제
    @DeleteMapping("/board/{id}")
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable Long id) {
        boolean isDeleted = boardService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }
}
