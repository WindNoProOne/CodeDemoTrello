package com.backend.api.Service;

import com.backend.api.Dto.Request.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto createComment (CommentDto commentDto);
    List<CommentDto> getAllComment();
    Optional<CommentDto> getDetailsComments(Integer commentId);
    void deleteComment(Integer commentId);
}
