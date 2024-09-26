package com.backend.api.Dto.Request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    private Integer id;
    private String name;
    private LocalDateTime createDate;
    private Integer createUserId;
}
