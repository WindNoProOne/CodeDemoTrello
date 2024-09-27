package com.backend.api.Service.Impl;

import com.backend.api.Dto.Request.CardDto;
import com.backend.api.Dto.Request.MoveCardDto;
import com.backend.api.Entity.Board;
import com.backend.api.Entity.Card;
import com.backend.api.Entity.User;
import com.backend.api.Exception.InvalidCardStateException;
import com.backend.api.Mapper.CardMapper;
import com.backend.api.Repository.BoardRepository;
import com.backend.api.Repository.CardRepository;
import com.backend.api.Repository.UserRepository;
import com.backend.api.Service.CardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.net.StandardSocketOptions;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CardRepository cardRepository;
    private final static String USER_NOT_LOGGED_IN = "User not logged in";
    private final static String BOARD_NOT_FOUND = "Board not found";
    private final static String CARD_NOT_FOUND = "CARD not found";
    private final static String CREATE_CARD_FALSE = "Create Card False";
    private final static String UPDATE_CARD_FALSE = "Update Card false";

    //Create Card
    @Override
    public CardDto createCard(CardDto cardDto) {
        try {
            Card card = CardMapper.mapToCard(cardDto);
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currentUserEmail = userDetails.getUsername();
            User currentUser = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new EntityNotFoundException(USER_NOT_LOGGED_IN));
            Board board = boardRepository.findById(cardDto.getBoardId())
                    .orElseThrow(() -> new EntityNotFoundException(BOARD_NOT_FOUND));
            card.setBoard(board);
            card.setCreateUser(currentUser);
            card.setCreateDate(new Date());
            card.setPosition(getNextPosition(board));
            Card savedCard = cardRepository.save(card);
            return CardMapper.mapToCardDto(savedCard);
        } catch (Exception e) {
            throw new InvalidCardStateException(CREATE_CARD_FALSE);
        }
    }

    private int getNextPosition(Board board) {
        return cardRepository.findMaxPositionByBoard(board) + 1;
    }

    @Override
    public List<CardDto> getAllCard() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream().map(CardMapper::mapToCardDto).collect(Collectors.toList());
    }

    @Override
    public Optional<CardDto> getDetailsCard(Integer cardId) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            CardDto cardDto = CardMapper.mapToCardDto(cardOptional.get());
            return Optional.of(cardDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<CardDto> updateCard(CardDto cardDto, Integer cardId) {
        try {
            Card existingCard = cardRepository.findById(cardId)
                    .orElseThrow(() -> new EntityNotFoundException(CARD_NOT_FOUND));
            if (cardDto.getName() != null) {
                existingCard.setName(cardDto.getName());
            }
            if (cardDto.getDescription() != null) {
                existingCard.setDescription(cardDto.getDescription());
            }
            if (cardDto.getDueDate() != null) {
                existingCard.setDueDate(cardDto.getDueDate());
            }
            existingCard.setCreateDate(existingCard.getCreateDate());
            Card updatedCard = cardRepository.save(existingCard);
            return Optional.of(CardMapper.mapToCardDto(updatedCard));
        } catch (Exception e) {
            throw new EntityNotFoundException(UPDATE_CARD_FALSE);
        }
    }

    @Override
    public void deleteCard(Integer cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new EntityNotFoundException();
        }
        cardRepository.deleteById(cardId);
    }

    @Override
    public boolean moveCard(MoveCardDto moveCardDto, Integer cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new InvalidCardStateException("Card not found with id: " + cardId));
        Integer newPosition = moveCardDto.getNewPosition();
        Integer newBoardId = moveCardDto.getNewBoardId();
        updateCardPositionAndBoard(card, newPosition, newBoardId);
        return true;
    }

    private void updateCardPositionAndBoard(Card card, Integer newPosition, Integer newBoardId) {
        Board currentBoard = card.getBoard();

        if(card.getBoard().equals(newBoardId)) {
            updateCardPosition(card.getPosition(), newPosition, currentBoard.getId());
            card.setPosition(newPosition);
        } else  {
            updateCardPosition(card.getPosition(), null, currentBoard.getId());
            updateCardPosition(null, newPosition, newBoardId);

            //Update ID New Board
            Board newBoard = new Board();
            newBoard.setId(newBoardId);


            card.setPosition(newPosition);
            card.setBoard(newBoard);
        }
        cardRepository.save(card);
    }

    private void updateCardPosition(Integer currentPosition, Integer newPosition, Integer boardId) {
        List<Card> cardsInBoard = cardRepository.findByBoardId(boardId);

        if (newPosition != null) {
            if (currentPosition == null) {
                for (Card c : cardsInBoard) {
                    if (c.getPosition() >= newPosition) {
                        c.setPosition(c.getPosition() + 1);
                        cardRepository.save(c);
                    }
                }
            } else if (currentPosition < newPosition) {
                for (Card c : cardsInBoard) {
                    if (c.getPosition() > currentPosition && c.getPosition() <= newPosition) {
                        c.setPosition(c.getPosition() - 1);
                        cardRepository.save(c);
                    }
                }
            } else if (currentPosition > newPosition) {
                for (Card c : cardsInBoard) {
                    if (c.getPosition() >= newPosition && c.getPosition() < currentPosition) {
                        c.setPosition(c.getPosition() + 1);
                        cardRepository.save(c);
                    }
                }
            }
        } else {
            for (Card c : cardsInBoard) {
                if (c.getPosition() > currentPosition) {
                    c.setPosition(c.getPosition() - 1);
                    cardRepository.save(c);
                }
            }
        }
    }

}