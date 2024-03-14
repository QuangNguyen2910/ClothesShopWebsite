package com.example.project_economic.controller;

import com.example.project_economic.dto.CommentDTO;
import com.example.project_economic.entity.Comment;
import com.example.project_economic.service.CommentService;
import com.example.project_economic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @MessageMapping("/message")
    @SendTo("/topic/return-to")
    public CommentDTO comment(@RequestBody CommentDTO commentDTO){
        if(commentDTO.getCommentParentId()==null){
            Comment commentEntity=new Comment();
            commentEntity.setContent(commentDTO.getContent());
            commentEntity.setStar(commentDTO.getStar());
            Comment comment=this.commentService.addComment(commentEntity, commentDTO.getUserId(), commentDTO.getProductId());
            CommentDTO commentDTO1=CommentDTO.builder()
                    .id(comment.getId())
                    .productId(comment.getProductId())
                    .content(comment.getContent())
                    .userId(comment.getUserId())
                    .userName(userService.findUserById(comment.getUserId()).getUsername())
                    .step(comment.getStep())
                    .star(comment.getStar())
                    .build();
            System.out.println(comment.getStar());
            return commentDTO1;
        }
        else{
            Comment comment=new Comment();
            comment.setContent(commentDTO.getContent());
            Comment comment1=this.commentService.addReplyComment(comment, commentDTO.getUserId(), commentDTO.getProductId(), commentDTO.getCommentParentId());
            CommentDTO commentDTO1=CommentDTO.builder()
                    .id(comment1.getId())
                    .productId(comment1.getProductId())
                    .content(comment1.getContent())
                    .userId(comment1.getUserId())
                    .step(comment1.getStep())
                    .star(comment1.getStar())
                    .userName(userService.findUserById(comment.getUserId()).getUsername())
                    .commentParentId(commentDTO.getCommentParentId())
                    .build();
            return commentDTO1;
        }
    }
}
