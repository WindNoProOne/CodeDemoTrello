package com.backend.api.Controller;

import com.backend.api.Dto.Request.BoardDto;
import com.backend.api.Dto.Request.CardDto;
import com.backend.api.Service.CardService;
import io.micrometer.observation.transport.ResponseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class CardController {
    @Autowired
    private CardService cardService;
    @PostMapping("/card")
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        CardDto createdCard = cardService.createCard(cardDto);
        return ResponseEntity.ok(createdCard);
    }

    @GetMapping("/card")
    public ResponseEntity<List<CardDto>> findAllCard () {
        List<CardDto> cardDto = cardService.getAllCard();
        return  ResponseEntity.ok(cardDto);
    }

    @GetMapping("/card/{cardId}")
    public  ResponseEntity<CardDto> getCardDetails  (@PathVariable Integer cardId) {
        Optional<CardDto> cardDetails = cardService.getDetailsCard(cardId);
        return  cardDetails.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/card/{cardId}")
    public ResponseEntity<CardDto> updateCard(@PathVariable Integer cardId, @RequestBody CardDto cardDto) {
        Optional<CardDto> updateCard = cardService.updateCard(cardDto, cardId);
        return updateCard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Integer cardId) {
        cardService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }
 }
