package com.backend.api.Service.Impl;

import com.backend.api.Dto.Request.CommentDto;
import com.backend.api.Entity.Card;
import com.backend.api.Entity.Comment;
import com.backend.api.Entity.User;
import com.backend.api.Exception.InvalidCommentStateException;
import com.backend.api.Mapper.CardMapper;
import com.backend.api.Mapper.CommentMapper;
import com.backend.api.Repository.CardRepository;
import com.backend.api.Repository.CommentRepository;
import com.backend.api.Repository.UserRepository;
import com.backend.api.Service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CommentRepository commentRepository;

    private final static String USER_NOT_LOGGED_IN = "User not logged in";
    private final static String CREATE_NEW_COMMENT_SUCCESSFULLY = "Create new user successfully";
    private final static String CARD_NOT_FOUND = "CARD not found";
    private final static String CREATE_NEW_COMMENT_FALSE = "Create new user false";
    @Override
    public CommentDto createComment(CommentDto commentDto) {
        try {
            Comment comment = CommentMapper.mapToComment(commentDto);
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currentUserEmail = userDetails.getUsername();
            User currentUser = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new EntityNotFoundException(USER_NOT_LOGGED_IN));

            Card card = cardRepository.findById(commentDto.getCardId())
                    .orElseThrow(() -> new EntityNotFoundException(CARD_NOT_FOUND));

            comment.setUser(currentUser);
            comment.setCard(card);
            comment.setCreateDate(new Date());

            Comment savedComment = commentRepository.save(comment);

            return CommentMapper.mapToCommentDto(savedComment);

        } catch (Exception e) {
            throw new InvalidCommentStateException(CREATE_NEW_COMMENT_FALSE);
        }
    }


    @Override
    public List<CommentDto> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(CommentMapper::mapToCommentDto).collect(Collectors.toList());
    }

    @Override
    public Optional<CommentDto> getDetailsComments(Integer commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if(commentOptional.isPresent()) {
            CommentDto commentDto = CommentMapper.mapToCommentDto(commentOptional.get());
            return  Optional.of(commentDto);
        } else  {
            return Optional.empty();
        }
    }

    @Override
    public void deleteComment(Integer commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException();
        }
        commentRepository.deleteById(commentId);
    }
}
