package com.backend.api.Service;

import com.backend.api.Dto.Request.BoardDto;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    BoardDto createBoard(BoardDto boardDto);
    List<BoardDto> getAllBoard();
    Optional<BoardDto> getDetails(Integer boardId);
    Optional<BoardDto> updateBoard(Integer boardId, BoardDto boardDto);
    void deleteBoard(Integer boardId);
}
