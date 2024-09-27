package com.backend.api.Service;

import com.backend.api.Dto.Request.CardDto;

import java.util.List;
import java.util.Optional;

public interface CardService {
    CardDto createCard(CardDto cardDto);
    List<CardDto> getAllCard();
    Optional<CardDto> getDetailsCard(Integer cardId);
    Optional<CardDto> updateCard (CardDto cardDto,  Integer cardId);
    void  deleteCard(Integer cardId);
}
