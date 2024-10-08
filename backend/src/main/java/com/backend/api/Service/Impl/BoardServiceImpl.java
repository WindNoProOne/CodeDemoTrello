package com.backend.api.Service.Impl;

import com.backend.api.Dto.Request.BoardDto;
import com.backend.api.Dto.Request.MoveBoardDto;
import com.backend.api.Entity.Board;
import com.backend.api.Entity.User;
import com.backend.api.Exception.InvalidCardStateException;
import com.backend.api.Mapper.BoardMapper;
import com.backend.api.Repository.BoardRepository;
import com.backend.api.Repository.UserRepository;
import com.backend.api.Service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    private   final static String USER_NOT_LOGGED_IN  ="User not logged in";
    private final  static  String BOARD_NOT_FOUND = "Board Not Found";
    @Override
    public BoardDto createBoard(BoardDto boardDto) {
        Board board = BoardMapper.mapToBoard(boardDto);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserEmail = userDetails.getUsername();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_LOGGED_IN));
        board.setCreateUser(currentUser);
        Integer maxPosition = boardRepository.findMaxPosition();
        if (maxPosition == null) {
            maxPosition = -1;
        }
        board.setPosition(maxPosition + 1);
        Board savedBoard = boardRepository.save(board);
        return BoardMapper.mapToBoardToDto(savedBoard);
    }


    @Override
    public List<BoardDto> getAllBoard() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(BoardMapper::mapToBoardToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<BoardDto> getDetails(Integer boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isPresent()) {
            BoardDto boardDto = BoardMapper.mapToBoardToDto(boardOptional.get());
            return Optional.of(boardDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<BoardDto> updateBoard(Integer boardId, BoardDto boardDto) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if(boardOptional.isPresent()) {
            Board boardToUpdate = boardOptional.get();
            boardToUpdate.setName(boardDto.getName());
            boardToUpdate.setCreateDate(boardDto.getCreateDate());
            boardRepository.save(boardToUpdate);
            return Optional.of(BoardMapper.mapToBoardToDto(boardToUpdate));
        } else  {
            throw new EntityNotFoundException(BOARD_NOT_FOUND);
        }
    }

    @Override
    public void deleteBoard(Integer boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new EntityNotFoundException(BOARD_NOT_FOUND);
        }
        boardRepository.deleteById(boardId);
    }

    //Code Update Position (MoveBoard)
    @Override
    public boolean moveBoard(MoveBoardDto moveBoardDto, Integer boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new InvalidCardStateException("Board not found with id " + boardId));
        Integer newPosition = moveBoardDto.getNewPosition();
        updateBoardPosition(board, newPosition);
        return true;
    }
    private void updateBoardPosition(Board board, Integer newPosition) {
        Integer currentPosition = board.getPosition();
        if (!currentPosition.equals(newPosition)) {
            updatePositions(currentPosition, newPosition);
            board.setPosition(newPosition);
            boardRepository.save(board);
        }
    }

    private void updatePositions(Integer currentPosition, Integer newPosition) {
        List<Board> boards = boardRepository.findAll();
        for (Board b : boards) {
            if (currentPosition < newPosition) {
                if (b.getPosition() > currentPosition && b.getPosition() <= newPosition) {
                    b.setPosition(b.getPosition() - 1);
                    boardRepository.save(b);
                }
            }
            else {
                if (b.getPosition() >= newPosition && b.getPosition() < currentPosition) {
                    b.setPosition(b.getPosition() + 1);
                    boardRepository.save(b);
                }
            }
        }
    }


}
