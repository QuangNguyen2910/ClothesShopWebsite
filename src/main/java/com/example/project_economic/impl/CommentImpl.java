package com.example.project_economic.impl;

import com.example.project_economic.dto.CommentDTO;
import com.example.project_economic.entity.Comment;
import com.example.project_economic.repository.CommentRepository;
import com.example.project_economic.repository.UserRepository;
import com.example.project_economic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    private final UserRepository userRepository;

    public CommentImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentDTO> findByPostId(Long postId) {
        List<Comment> comments = this.commentRepository.findCommentFirstInProduct(postId);
        return DFS(comments);
    }

    @Override
    public Comment addComment(Comment comment, Long userId, Long productId) {
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setProductId(productId);
        comment.setUserId(userId);
        comment.setStep(1L);
        return commentRepository.save(comment);
    }

    @Override
    public Comment addReplyComment(Comment replyComment, Long userId, Long productId, Long parentId) {
        Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new NotFoundException("Parent comment not found"));
        replyComment.setParentComment(parentComment);
        replyComment.setCreatedAt(LocalDateTime.now());
        replyComment.setUpdatedAt(LocalDateTime.now());
        replyComment.setUserId(userId);
        replyComment.setProductId(productId);
        replyComment.setStep(parentComment.getStep() + 1);
        Comment commentSaved = commentRepository.save(replyComment);
        List<Comment> comments = parentComment.getChildComments();
        parentComment.setChildComments(comments);
        commentRepository.save(parentComment);
        return commentSaved;
    }

    public void Try(Comment comment, List<Comment> commentList) {
        commentList.add(comment);
        if (comment.getChildComments().size() == 0) {
            return;
        } else {
            for (int i = comment.getChildComments().size() - 1; i >= 0; i--) {
                Try(comment.getChildComments().get(i), commentList);
            }
        }
    }

    public List<CommentDTO> DFS(List<Comment> comments) {
        List<Comment> commentList = new ArrayList<>();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            Try(comments.get(i), commentList);
        }
        for (Comment comment : commentList) {
            DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy");
            CommentDTO commentDTO = CommentDTO.builder()
                    .id(comment.getId())
                    .productId(comment.getProductId())
                    .content(comment.getContent())
                    .userId(comment.getUserId())
                    .userName(this.userRepository.findById(comment.getUserId()).get().getUsername())
                    .step(comment.getStep())
                    .star(comment.getStar())
                    .dateCreate(dateTimeFormatter.format(comment.getCreatedAt()))
                    .build();
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }
}
