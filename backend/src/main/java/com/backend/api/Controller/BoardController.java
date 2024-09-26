package com.backend.api.Controller;


import com.backend.api.Dto.Request.BoardDto;
import com.backend.api.Mapper.BoardMapper;
import com.backend.api.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        try {
            BoardDto createdBoard = boardService.createBoard(boardDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBoard);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/board")
    public ResponseEntity<List<BoardDto>> findAllBoard () {
        List<BoardDto> boardDto = boardService.getAllBoard();
        return ResponseEntity.ok(boardDto);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardDto> getBoardDetails(@PathVariable Integer boardId) {
        Optional<BoardDto> boardDetails = boardService.getDetails(boardId);
        return boardDetails.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/board/{boardId}")
    public ResponseEntity<BoardDto> updateBoard(@PathVariable Integer boardId, @RequestBody BoardDto boardDto) {
        Optional<BoardDto> updatedBoard = boardService.updateBoard(boardId, boardDto);
        return updatedBoard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Integer boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}
