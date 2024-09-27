package com.backend.api.Controller;


import com.backend.api.Entity.Comment;
import com.backend.api.Dto.Request.CommentDto;
import com.backend.api.Service.CommentService;
import com.backend.api.Service.Impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentDto> createComment (@RequestBody CommentDto commentDto) {
        CommentDto createComment = commentService.createComment(commentDto);
        return ResponseEntity.ok(createComment);
    }

    @GetMapping("/comment")
    public ResponseEntity<List<CommentDto>> findAllComment () {
        List<CommentDto> cardDto = commentService.getAllComment();
        return  ResponseEntity.ok(cardDto);
    }

    @GetMapping("/comment/{commentId}")
    public  ResponseEntity<CommentDto> getCommentDetails  (@PathVariable Integer commentId) {
        Optional<CommentDto> cardDetails = commentService.getDetailsComments(commentId);
        return  cardDetails.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment (@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return  ResponseEntity.noContent().build();
    }
}

