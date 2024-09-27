package com.backend.api.Dto.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private Integer id;
    private Integer boardId;
    private String name;
    private String description;
    private Integer position;
    private Date dueDate;
    private Date createDate;
    private Integer createUserId;
}
