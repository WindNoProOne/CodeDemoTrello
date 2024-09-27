package com.backend.api.Dto.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveCardDto {
    private Integer newPosition;
    private Integer newBoardId;
}
