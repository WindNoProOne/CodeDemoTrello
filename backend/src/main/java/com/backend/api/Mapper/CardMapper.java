package com.backend.api.Mapper;

import com.backend.api.Dto.Request.CardDto;
import com.backend.api.Entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public static CardDto mapToCardDto(Card card) {
        if (card == null) {
            return null;
        }
        CardDto cardDto = new CardDto();
        cardDto.setId(card.getId());
        cardDto.setBoardId(card.getBoard() != null ? card.getBoard().getId() : null);
        cardDto.setName(card.getName());
        cardDto.setDescription(card.getDescription());
        cardDto.setPosition(card.getPosition());
        cardDto.setDueDate(card.getDueDate());
        cardDto.setCreateDate(card.getCreateDate());
        cardDto.setCreateUserId(card.getCreateUser() != null ? card.getCreateUser().getId() : null);
        return cardDto;
    }

    public static Card mapToCard(CardDto cardDto) {
        if (cardDto == null) {
            return null;
        }
        Card card = new Card();
        card.setId(cardDto.getId());
        card.setName(cardDto.getName());
        card.setDescription(cardDto.getDescription());
        card.setPosition(cardDto.getPosition());
        card.setDueDate(cardDto.getDueDate());
        card.setCreateDate(cardDto.getCreateDate());
        return card;
    }
}
