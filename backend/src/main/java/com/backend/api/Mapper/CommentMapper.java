package com.backend.api.Mapper;

import com.backend.api.Dto.Request.CommentDto;
import com.backend.api.Entity.Comment;

public class CommentMapper {
    public static CommentDto mapToCommentDto(Comment comment) {
        if(comment == null) {
            return null;
        }
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setUserId(comment.getUser() != null ? comment.getUser().getId() : null);
        commentDto.setCardId(comment.getCard() != null ? comment.getCard().getId() : null);
        commentDto.setComment(comment.getComment());
        commentDto.setCreateDate(comment.getCreateDate());
        return commentDto;
    }

    public static Comment mapToComment(CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setComment(commentDto.getComment());
        return comment;
    }
}
