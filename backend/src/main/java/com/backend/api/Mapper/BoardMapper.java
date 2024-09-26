package com.backend.api.Mapper;


import com.backend.api.Dto.Request.BoardDto;
import com.backend.api.Entity.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {

    public static BoardDto mapToBoardToDto(Board board) {
        if (board == null) {
            return null;
        }
        BoardDto boardToDto = new BoardDto();
        boardToDto.setId(board.getId());
        boardToDto.setName(board.getName());
        boardToDto.setCreateDate(board.getCreateDate());
        boardToDto.setCreateUserId(board.getCreateUser() != null ? board.getCreateUser().getId() : null);
        return boardToDto;
    }

    public static  Board mapToBoard  (BoardDto boardDto) {
        if(boardDto == null) {
            return  null;
        }

        Board board = new Board();
        board.setId(boardDto.getId());
        board.setName(boardDto.getName());
        board.setCreateDate(boardDto.getCreateDate());
        return board;
    }
}
