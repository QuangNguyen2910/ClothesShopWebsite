package com.example.project_economic.controller;

import com.example.project_economic.entity.Comment;
import com.example.project_economic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/rep/")
    public ResponseEntity<?>createComment(
            @RequestParam("parentId")Long parentId,
            @RequestParam("productId")Long productId,
            @RequestParam("userId")Long userId,
            @RequestBody Comment comment
            ){
        return ResponseEntity.ok(this.commentService.addReplyComment(comment,userId,productId,parentId));
    }
    @PostMapping("/add/")
    public ResponseEntity<Comment>addComment(
            @RequestParam("productId")Long productId,
            @RequestParam("userId")Long userId,
            @RequestBody Comment commentEntity
    ){
        return ResponseEntity.ok(this.commentService.addComment(commentEntity,userId,productId));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?>getAllComment(
            @PathVariable Long productId
    ){
        return ResponseEntity.ok(this.commentService.findByPostId(productId));
    }
}
