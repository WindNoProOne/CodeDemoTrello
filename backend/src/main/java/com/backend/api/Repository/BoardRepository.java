package com.backend.api.Repository;

import com.backend.api.Entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    @Query("SELECT COALESCE(MAX(b.position), 0) FROM Board b")
    Integer findMaxPosition();


}
