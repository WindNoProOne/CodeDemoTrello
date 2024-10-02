package com.backend.api.Repository;

import com.backend.api.Entity.Board;
import com.backend.api.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Integer> {
    @Query("SELECT COALESCE(MAX(c.position), 0) FROM Card c WHERE c.board = :board")
    int findMaxPositionByBoard(@Param("board") Board board);
    List<Card> findByBoardId(Integer boardId);
}
