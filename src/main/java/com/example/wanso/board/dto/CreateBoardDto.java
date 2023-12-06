package com.example.wanso.board.dto;

import lombok.Data;

@Data
public class CreateBoardDto {
    private String content; // nullable이므로 @NotNull 어노테이션은 사용하지 않음
}
